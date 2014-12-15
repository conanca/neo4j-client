package com.xinhuanet.commons.neo4j;

import com.alibaba.fastjson.JSON;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, Object> para = new HashMap<String, Object>();
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
		boolean a = neo4jClient.addNodeLabel(9054159L, "UserNode");
		logger.debug("" + a);
	}

	@Test
	public void testDeleteLabel() {
		boolean a = neo4jClient.deleteNodeLabel(9054159L, "UserNode");
		logger.debug("" + a);
	}

	@Test
	public void testUpdateNodeProperties() {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("testname1", "abc1");
		neo4jClient.updateNodeProperties(1025L, para);
	}

	@Test
	public void testCreateRelationship() {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("createdAt", System.currentTimeMillis() + "");
		long relationshipId = neo4jClient.createRelationship(1025L, 9054159L, "FOLLOWS", para);
		logger.debug(relationshipId + "");
	}

	@Test
	public void testUpdateRelationsihpProperties() {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("createdAt", System.currentTimeMillis() + "");
		para.put("privateFlag", true);
		boolean flag = false;
		flag = neo4jClient.updateRelationshipProperties(19075977L, para);
		logger.debug(flag + "");

	}

	@Test
	public void testDeleteRelationship() {
		boolean flag = false;
		flag = neo4jClient.deleteRelationship(19075977L);
		logger.debug(flag + "");
	}

	@Test
	public void testDeleteNode() {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("testname", "abc");
		Long id = neo4jClient.createNode(para);
		logger.debug(id + "");
		boolean flag = false;
		flag = neo4jClient.deleteNode(id);
		logger.debug(flag + "");
	}

	@Test
	public void testCreateNodeWithLabels() {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("testname", "abc");
		String[] labels = { "UserNode" };
		Long id = neo4jClient.createNode(labels, para);
		logger.debug("" + id);
	}


	@Test
	public void testGetNodeLabels() throws Exception {
		String[] labels = neo4jClient.getNodeLabels(1026L);
		logger.debug(JSON.toJSONString(labels));
	}

	@Test
	public void testUpdateNodeLabel() throws Exception {
		boolean a = neo4jClient.updateNodeLabel(9054159L, "UserNode1", "UserNode2");
		logger.debug("" + a);
	}

	@Test
	public void testUpdateNodeProperty() throws Exception {
		neo4jClient.updateNodeProperty(1025L, "testname1", "abcdefg");
	}

	@Test
	public void testGetRelationshipProperties() throws Exception {
		Map data = neo4jClient.getRelationshipProperties(4680053L);
		logger.debug(JSON.toJSONString(data));
	}

	@Test
	public void testUpdateRelationshipProperty() throws Exception {
		neo4jClient.updateNodeProperty(4680053L, "testname1", "abcdefg");
	}
}
