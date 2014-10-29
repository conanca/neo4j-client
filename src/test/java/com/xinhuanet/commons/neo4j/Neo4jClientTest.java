package com.xinhuanet.commons.neo4j;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Neo4jClientTest {
	private static Logger logger = LoggerFactory.getLogger(RestfulClientTest.class);

	private static Neo4jClient neo4jClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		neo4jClient = new Neo4jClient("http://192.168.86.222:7474/db/data", "neo4j", "xinhua");
	}

	@Test
	public void testCypherQuery() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public void testAddLabels() {
		boolean a = neo4jClient.addLabels(9054159L, "UserNode");
		logger.debug("" + a);

	}

	@Test
	public void testDeleteLabel() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteNode() {
		fail("Not yet implemented");
	}

}
