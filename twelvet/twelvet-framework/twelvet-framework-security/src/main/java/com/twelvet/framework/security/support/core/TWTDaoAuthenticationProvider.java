package com.twelvet.framework.security.support.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.twelvet.framework.core.locale.I18nUtils;
import com.twelvet.framework.security.constants.Oauth2GrantEnums;
import com.twelvet.framework.security.support.service.TwUserDetailsService;
import com.twelvet.framework.utils.http.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 自定义处理校验(默认密码模式)
 */
public class TWTDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final static Logger log = LoggerFactory.getLogger(TWTDaoAuthenticationProvider.class);

	/**
	 * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
	 * String)} on when the user is not found to avoid SEC-2056.
	 */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	private final static BasicAuthenticationConverter basicConvert = new BasicAuthenticationConverter();

	private PasswordEncoder passwordEncoder;

	/**
	 * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
	 * on when the user is not found to avoid SEC-2056. This is necessary, because some
	 * {@link PasswordEncoder} implementations will short circuit if the password is not
	 * in a valid format.
	 */
	private volatile String userNotFoundEncodedPassword;

	private UserDetailsService userDetailsService;

	private UserDetailsPasswordService userDetailsPasswordService;

	public TWTDaoAuthenticationProvider() {
		setMessageSource(SpringUtil.getBean(MessageSource.class));
		setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
		// 开启隐藏找不到用户提示
		setHideUserNotFoundExceptions(true);
	}

	/**
	 * 密码校验，短信验证码校验
	 * @param userDetails as retrieved from the
	 * {@link #retrieveUser(String, UsernamePasswordAuthenticationToken)} or
	 * <code>UserCache</code>
	 * @param authentication the current request that needs to be authenticated
	 * @throws AuthenticationException AuthenticationException
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		String grantType = ServletUtils.getRequest().get().getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (!Oauth2GrantEnums.PASSWORD.getGrant().equals(grantType)) {
			return;
		}
		if (authentication.getCredentials() == null) {
			log.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(
					I18nUtils.getLocale("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		// 检验密码是否正确
		if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			log.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(this.messages
				.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		prepareTimingAttackProtection();
		HttpServletRequest request = null;
		try {
			request = ServletUtils.getRequest()
				.orElseThrow(
						(Supplier<Throwable>) () -> new InternalAuthenticationServiceException("web request is empty"));
		}
		catch (Throwable e) {
			throw new InternalAuthenticationServiceException("web request is empty");
		}

		Map<String, String> paramMap = ServletUtils.getMapParam();
		String grantType = paramMap.get(OAuth2ParameterNames.GRANT_TYPE);
		String clientId = paramMap.get(OAuth2ParameterNames.CLIENT_ID);

		if (StrUtil.isBlank(clientId)) {
			clientId = basicConvert.convert(request).getName();
		}

		Map<String, TwUserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(TwUserDetailsService.class);

		String finalClientId = clientId;
		// 获取需要使用的登录器
		Optional<TwUserDetailsService> optional = userDetailsServiceMap.values()
			.stream()
			.filter(service -> service.support(finalClientId, grantType))
			.max(Comparator.comparingInt(Ordered::getOrder));

		if (optional.isEmpty()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}

		try {
			UserDetails loadedUser = optional.get().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (AuthenticationException ex) {
			throw ex;
		}
		catch (Exception ex) {
			log.debug("Unknown login error thrown");
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
				&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords. If
	 * not set, the password will be compared using
	 * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
	 * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder}
	 * types.
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
		this.userDetailsPasswordService = userDetailsPasswordService;
	}

}
