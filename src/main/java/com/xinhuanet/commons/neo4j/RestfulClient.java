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

	public String post(List<String> paths, Map<String, String> form) {
		WebTarget target = createWebTarget(paths);
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(
				Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		return response.toString();
	}

	public String get(List<String> paths) {
		WebTarget target = createWebTarget(paths);
		String response = target.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		return response;
	}

	private WebTarget createWebTarget(List<String> paths) {
		WebTarget target = client().target(url).path("resource");
		for (String path : paths) {
			target = target.path(path);
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
