package com.adria.adriaseal.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Getter
public class EnvUtil {
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-ClientEntity-IP",
            "WL-Proxy-ClientEntity-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
    final Environment environment;
    @Value("${app.save.ROOT_FOLDER}")
    private String uploadPath;
    @Value("${app.save.DOCUMENT_ORIGIN}")
    private String filesEndpoint;
    private String port;
    private String hostname;

    public EnvUtil(Environment environment) {
        this.environment = environment;
    }

    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getClientIpAddressIfItExists() {
        try {
            HttpServletRequest request = getCurrentRequest();
            for (String header : IP_HEADER_CANDIDATES) {
                String ipList = request.getHeader(header);
                if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                    return ipList.split(",")[0];
                }
            }
            return request.getRemoteAddr();
        } catch (NullPointerException ignored) {
            return "0.0.0.0";
        }

    }


    public static String getUserAgentIfItExists() {
        return getCurrentRequest().getHeader("user-agent");
    }

    public String getPort() {
        if (port == null) port = environment.getProperty("local.server.port");
        return port;
    }

    private String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    public String getServerUrl() throws UnknownHostException {
        return "http://" + getHostname() + ":" + getPort();
    }
}
