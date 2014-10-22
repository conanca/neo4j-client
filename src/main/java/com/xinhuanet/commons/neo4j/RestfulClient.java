package com.xinhuanet.commons.neo4j;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 * 一个简单的Restful客户端类，http响应内容为JSON格式。
 * 
 * @author conanca
 * 
 */
public class RestfulClient {

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

	public void httpBasicAuth(String userName, String password) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, password);
		if (client == null) {
			ClientConfig clientConfig = new ClientConfig();
			clientConfig.register(feature);
			client = ClientBuilder.newClient(clientConfig);
		}
	}

	public Response post(List<String> resources, String para) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(
				Entity.entity(para, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response;
	}

	public Response post(List<String> resources, Map<String, String> para) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(
				Entity.entity(para, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response;
	}

	public Response get(List<String> resources) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		return response;
	}

	public Response delete(List<String> resources) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).delete();
		return response;
	}

	public Response put(List<String> resources, String para) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).put(
				Entity.entity(para, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response;
	}

	public Response put(List<String> resources, Map<String, String> para) {
		WebTarget target = createWebTarget(resources);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).put(
				Entity.entity(para, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response;
	}

	private WebTarget createWebTarget(List<String> resources) {
		WebTarget target = client().target(url);
		for (String resource : resources) {
			target = target.path(resource);
		}
		return target;
	}

	public Client client() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
