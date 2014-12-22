package com.xinhuanet.commons.neo4j;

import com.alibaba.fastjson.JSON;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * 对jersey client的简单封装，http响应内容为JSON格式。
 */
public class RestfulClient {
    private static Logger logger = LoggerFactory.getLogger(RestfulClient.class);

    private Client client;
    private String url;

    public RestfulClient(String url) {
        client = ClientBuilder.newClient();
        this.url = url;
    }

    public RestfulClient(String url, String httpAuthUserName, String httpAuthPassword) {
        httpBasicAuth(httpAuthUserName, httpAuthPassword);
        this.url = url;
    }

    private void httpBasicAuth(String userName, String password) {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
        if (client == null) {
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.register(feature);
            client = ClientBuilder.newClient(clientConfig);
        }
    }

    public Response post(List<String> resources, Object para) {
        WebTarget target = createWebTarget(resources);
        String json = JSON.toJSONString(para);
        logger.debug("parameter : " + json);
        return target.request(MediaType.APPLICATION_JSON_TYPE).post(
                Entity.entity(json, MediaType.APPLICATION_JSON));
    }

    public Response get(List<String> resources) {
        WebTarget target = createWebTarget(resources);
        return target.request(MediaType.APPLICATION_JSON_TYPE).get();
    }

    public Response delete(List<String> resources) {
        WebTarget target = createWebTarget(resources);
        return target.request(MediaType.APPLICATION_JSON_TYPE).delete();
    }

    public Response put(List<String> resources, Object para) {
        WebTarget target = createWebTarget(resources);
        String json = JSON.toJSONString(para);
        logger.debug("parameter : " + json);
        return target.request(MediaType.APPLICATION_JSON_TYPE).put(
                Entity.entity(json, MediaType.APPLICATION_JSON));
    }

    private WebTarget createWebTarget(List<String> resources) {
        WebTarget target = client().target(url);
        for (String resource : resources) {
            target = target.path(resource);
        }
        logger.debug("uri : " + target.getUri().getPath());
        return target;
    }

    public String getUrl() {
        return url;
    }

    public Client client() {
        return client;
    }

}
