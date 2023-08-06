package com.twelvet.framework.security.domain;

import com.twelvet.api.system.domain.SysRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite twelvet.cn
 * @Description: 登录用户身份
 */
public class LoginUser extends User implements OAuth2AuthenticatedPrincipal {

	@Serial
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色对象
	 */
	private List<SysRole> roles;

	/**
	 * 部门ID
	 */
	private Long deptId;

	public LoginUser(Long userId, Long deptId, List<SysRole> roles, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.deptId = deptId;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("deptId", getDeptId())
			.append("roles", getRoles())
			.append("userId", getUserId())
			.toString();
	}

	/**
	 * Get the OAuth 2.0 token attributes
	 * @return the OAuth 2.0 token attributes
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<>();
	}

	@Override
	public String getName() {
		return this.getUsername();
	}

}
