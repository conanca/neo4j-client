package com.xinhuanet.commons.neo4j;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class Neo4jClientTest {
	private static Logger logger = LoggerFactory.getLogger(RestfulClientTest.class);

	private static Neo4jClient neo4jClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		neo4jClient = new Neo4jClient("http://192.168.86.222:7474/db/data", "neo4j", "xinhua");
	}

	@Test
	public void testCypherQuery() {
		List<String[]> data = neo4jClient.cypherQuery("MATCH (n) RETURN id(n),n LIMIT 100", null);
		logger.debug(JSON.toJSONString(data));
	}

	@Test
	public void testCreateNode() {
		Map<String, String> para = new HashMap<String, String>();
		para.put("testname", "abc");
		Long id = neo4jClient.createNode(para);
		logger.debug("id:" + id);
	}

	@Test
	public void testGetNode() {
		Map data = neo4jClient.getNodeProperties(1025L);
		logger.debug(JSON.toJSONString(data));
	}

	@Test
	public void testAddLabels() {
		boolean a = neo4jClient.addNodeLabels(9054159L, "UserNode");
		logger.debug("" + a);
	}

	@Test
	public void testDeleteLabel() {
		boolean a = neo4jClient.deleteNodeLabel(9054159L, "UserNode");
		logger.debug("" + a);
	}

	@Test
	public void testUpdateNodeProperties() {
		Map<String, String> para = new HashMap<String, String>();
		para.put("testname1", "abc1");
		neo4jClient.updateNodeProperties(1025L, para);
	}

	@Test
	public void testDeleteNode() {
		fail("Not yet implemented");
	}

}
