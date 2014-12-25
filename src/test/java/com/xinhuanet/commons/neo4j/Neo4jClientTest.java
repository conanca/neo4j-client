package com.xinhuanet.commons.neo4j;

import com.alibaba.fastjson.JSON;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Neo4jClientTest {
    private static Logger logger = LoggerFactory.getLogger(RestfulClientTest.class);

    private static Neo4jClient neo4jClient;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        neo4jClient = new Neo4jClient("http://192.168.86.222:7474/db/data", "neo4j", "xinhua");
    }

    public Long createTestNode() {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("testname", "abc");
        String[] labels = {"TestNode"};
        return neo4jClient.createNode(labels, para);
    }

    public Long createTestRelationship() {
        Long id1 = createTestNode();
        Long id2 = createTestNode();
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("createdAt", System.currentTimeMillis());
        return neo4jClient.createRelationship(id1, id2, "TestRelType", para);
    }

    @Test
    public void testCypherQuery() {
        List<String[]> data = neo4jClient.cypherQuery("MATCH (n) RETURN id(n),n LIMIT 10", null);
        logger.debug(JSON.toJSONString(data));
        assertTrue(JSON.toJSONString(data).contains("self"));
    }

    @Test
    public void testCypherQueryReturnInt() {
        Map para = new HashMap();
        para.put("userId", 2955888);
        List<String[]> data = neo4jClient.cypherQuery("MATCH (user:UserNode { userId: {userId} }) RETURN id(user)", para);
        logger.debug(JSON.toJSONString(data));
        assertEquals("2938130", data.get(0)[0]);
    }

    @Test
    public void testCreateNode() {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("testname", "abc");
        Long id = neo4jClient.createNode(para);
        logger.debug("id:" + id);
        assertTrue(id > 0);
    }

    @Test
    public void testGetNodeProperties() {
        Long id = createTestNode();

        Map data = neo4jClient.getNodeProperties(id);
        logger.debug(JSON.toJSONString(data));
        assertEquals("abc", data.get("testname"));
    }

    @Test
    public void testAddLabels() {
        Long id = createTestNode();

        String[] labels = {"TestNode1", "TestNode2"};
        boolean a = neo4jClient.addNodeLabel(id, labels);
        logger.debug("" + a);
        assertTrue(a);
    }

    @Test
    public void testDeleteLabel() {
        Long id = createTestNode();

        boolean a = neo4jClient.deleteNodeLabel(id, "TestNode");
        logger.debug("" + a);
        assertTrue(a);
    }

    @Test
    public void testUpdateNodeProperties() {
        Long id = createTestNode();

        Map<String, Object> para = new HashMap<String, Object>();
        para.put("testname1", "abc1");
        boolean a = neo4jClient.updateNodeProperties(id, para);
        assertTrue(a);
    }

    @Test
    public void testCreateRelationship() {
        Long id1 = createTestNode();
        Long id2 = createTestNode();

        Map<String, Object> para = new HashMap<String, Object>();
        para.put("createdAt", System.currentTimeMillis());
        long relationshipId = neo4jClient.createRelationship(id1, id2, "TestRelType", para);
        logger.debug(relationshipId + "");
        assertTrue(relationshipId > 0);
    }

    @Test
    public void testUpdateRelationshipProperties() {
        Long id = createTestRelationship();

        Map<String, Object> para = new HashMap<String, Object>();
        para.put("createdAt", System.currentTimeMillis());
        para.put("privateFlag", true);
        boolean flag = false;
        flag = neo4jClient.updateRelationshipProperties(id, para);
        logger.debug(flag + "");
        assertTrue(flag);
    }

    @Test
    public void testDeleteRelationship() {
        Long id = createTestRelationship();

        boolean flag = false;
        flag = neo4jClient.deleteRelationship(id);
        logger.debug(flag + "");
        assertTrue(flag);
    }

    @Test
    public void testDeleteNode() {
        Long id = createTestNode();
        boolean flag = false;
        flag = neo4jClient.deleteNode(id);
        logger.debug(flag + "");
        assertTrue(flag);
    }

    @Test
    public void testCreateNodeWithLabels() {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("testname", "abc");
        String[] labels = {"TestNodeAAA"};
        Long id = neo4jClient.createNode(labels, para);
        logger.debug("" + id);
        assertTrue(id > 0);
    }


    @Test
    public void testGetNodeLabels() throws Exception {
        Long id = createTestNode();
        String[] labels = neo4jClient.getNodeLabels(id);
        logger.debug(JSON.toJSONString(labels));
        assertEquals("TestNode", labels[0]);
    }

    @Test
    public void testUpdateNodeLabel() throws Exception {
        Long id = createTestNode();

        String[] labels = {"TestNode1", "TestNode2"};
        boolean a = neo4jClient.updateNodeLabel(id, labels);
        logger.debug("" + a);
        assertTrue(a);
    }

    @Test
    public void testUpdateNodeProperty() throws Exception {
        Long id = createTestNode();

        boolean a = neo4jClient.updateNodeProperty(id, "testname", "abcdefg");
        logger.debug("" + a);
        assertTrue(a);
    }

    @Test
    public void testGetRelationshipProperties() throws Exception {
        Long id = createTestRelationship();

        Map data = neo4jClient.getRelationshipProperties(id);
        logger.debug(JSON.toJSONString(data));
        assertNotNull(data.get("createdAt"));
    }

    @Test
    public void testUpdateRelationshipProperty() throws Exception {
        Long id = createTestRelationship();
        boolean a = neo4jClient.updateRelationshipProperty(id, "testname1", "abcdefg");
        logger.debug("" + a);
        assertTrue(a);
    }

    @Test
    public void testUpdateRelationshipPropertyObjVal() throws Exception {
        Long id = createTestRelationship();
        boolean a = neo4jClient.updateRelationshipProperty(id, "testage", 29);
        logger.debug("" + a);
        assertTrue(a);
    }
}
