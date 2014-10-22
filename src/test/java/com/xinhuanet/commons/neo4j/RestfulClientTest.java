package com.xinhuanet.commons.neo4j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class RestfulClientTest {
	private static Logger logger = LoggerFactory.getLogger(RestfulClientTest.class);

	private static RestfulClient restfulClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		restfulClient = new RestfulClient("http://192.168.86.222:7474/db/data", "neo4j", "xinhua");
	}

	@Test
	public void testPostListOfStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPostListOfStringMapOfStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		resources.add("1025");
		Response response = restfulClient.get(resources);
		logger.debug(JSON.toJSONString(response));
		logger.debug("Status:" + response.getStatus());
		logger.debug("Content:" + response.readEntity(String.class));
	}

	@Test
	public void testPostMap() throws Exception {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		Map<String, String> form = new HashMap<String, String>();
		form.put("userId", "222");
		form.put("createdAt", "1400");
		Response response = restfulClient.post(resources, form);
		logger.debug("Status:" + response.getStatus());
		logger.debug("data:" + response);
		logger.debug("Content:" + response.readEntity(String.class));
	}

	@Test
	public void testPostString() throws Exception {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		String from = "{}";
		Response response = restfulClient.post(resources, from);
		logger.debug("Status:" + response.getStatus());
		logger.debug("Status:" + response.getStatus());
		logger.debug("Content:" + response.readEntity(String.class));
	}

	@Test
	public void testDelete() {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		resources.add("9054150");
		Response response = restfulClient.delete(resources);
		logger.debug(JSON.toJSONString(response));
		logger.debug("Status:" + response.getStatus());
		logger.debug("Content:" + response.readEntity(String.class));
	}

	@Test
	public void testPut() {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		resources.add("9054153");
		resources.add("properties");
		Map<String, String> form = new HashMap<String, String>();
		form.put("createdAt", "1411");
		Response response = restfulClient.put(resources, form);
		logger.debug("Status:" + response.getStatus());
		logger.debug("data:" + response);
		logger.debug("Content:" + response.readEntity(String.class));
	}

	@Test
	public void testPutListOfStringMapOfStringString() {
		fail("Not yet implemented");
	}

}
