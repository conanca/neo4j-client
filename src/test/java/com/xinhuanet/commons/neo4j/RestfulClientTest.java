package com.xinhuanet.commons.neo4j;

import com.alibaba.fastjson.JSON;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		assertEquals(200,response.getStatus());
	}

	@Test
	public void testPostMap() throws Exception {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		Map<String, Object> form = new HashMap<String, Object>();
		form.put("testId", "222");
		form.put("createdAt", 1400);
		Response response = restfulClient.post(resources, form);
		logger.debug("Status:" + response.getStatus());
		logger.debug("Content:" + response.readEntity(String.class));
		assertEquals(201,response.getStatus());
	}

	@Test
	public void testPostString() throws Exception {
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		resources.add("1025");
		resources.add("labels");
		String from = "TestLabels";
		Response response = restfulClient.post(resources, from);
		logger.debug("Status:" + response.getStatus());
		logger.debug("data:" + response);
		logger.debug("Content:" + response.readEntity(String.class));
		assertEquals(204,response.getStatus());
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
		resources.add("9055500");
		resources.add("properties");
		Map<String, Object> form = new HashMap<String, Object>();
		form.put("createdAt", 1411);
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
