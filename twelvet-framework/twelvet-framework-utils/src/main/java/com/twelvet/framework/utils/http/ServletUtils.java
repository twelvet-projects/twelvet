package com.twelvet.framework.utils.http;

import com.twelvet.framework.utils.CharsetKit;
import com.twelvet.framework.utils.Convert;
import com.twelvet.framework.utils.TWTUtils;
import com.twelvet.framework.utils.exception.TWTUtilsException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: Servlet工具
 */
public class ServletUtils {

    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";

    /**
     * 渲染json数据
     *
     * @param code 客户端状态码
     * @param json json字符串
     */
    public static void render(int code, String json) {
        HttpServletResponse response = getResponse();
        try {
            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 获取当前地址完整URI(注意是服务器的IP,便于查找出错机器)
     *
     * @return 当前完整访问地址
     */
    public static String getHostRequestURI() {
        HttpServletRequest request = getRequest();
        return IpUtils.getHostIp() + ":" + request.getServerPort() + request.getRequestURI();
    }

    /**
     * 获取getResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取RequestAttributes
     *
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes;
    }

    /**
     * 获取session
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取json格式参数
     *
     * @return Map
     */
    public static Map<String, String> getMapParam() {
        return getMapParam(getRequest());
    }

    /**
     * 获取json格式参数
     *
     * @param httpServletRequest HttpServletRequest
     * @return Map
     */
    public static Map<String, String> getMapParam(HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<>(6);
        // 获取所有参数名称
        Enumeration enu = httpServletRequest.getParameterNames();
        // 遍历hash
        while (enu.hasMoreElements()) {
            String paramName = (String) enu.nextElement();
            // 获取参数值
            String[] paramValues = httpServletRequest.getParameterValues(paramName);
            // 是否存在参数
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    /**
     * 获取body数据
     *
     * @return String
     */
    public static String getStrFromStream(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try (
                ServletInputStream inputStream = req.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取Integer参数
     *
     * @param name 参数名称
     * @return 返回参数数据
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 导出文件给予前端
     *
     * @param httpServletResponse HttpServletResponse
     * @param file                文件
     * @param filename            文件名称
     */
    public static void download(HttpServletResponse httpServletResponse, byte[] file, String filename) {

        try {

            httpServletResponse.setCharacterEncoding(CharsetKit.UTF_8);
            filename = URLEncoder.encode(filename, CharsetKit.UTF_8);
            // Url编码，前台需自行还原
            filename = "attachment; filename=" + filename;
            // 设置Excel导出的名称
            httpServletResponse.setHeader("Content-Disposition", filename);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(file);

        } catch (IOException e) {
            throw new TWTUtilsException("文件导出出错");
        }

    }

    /**
     * 导出文件给予前端
     *
     * @param file     文件
     * @param filename 文件名称
     */
    public static void download(byte[] file, String filename) {
        download(getResponse(), file, filename);
    }

}
