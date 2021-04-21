package com.adria.adriasign.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Getter
public class EnvUtil {

    final Environment environment;
    @Value("${app.save.path}")
    private String uploadPath;
    @Value("${app.files.pdf.endpoint}")
    private String filesEndpoint;
    private String port;
    private String hostname;

    public EnvUtil(Environment environment) {
        this.environment = environment;
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
