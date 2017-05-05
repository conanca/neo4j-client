package com.dolplay.commons.neo4j;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.dolplay.commons.neo4j.node.BaseNode;
import com.dolplay.commons.neo4j.node.GroupNode;
import com.dolplay.commons.neo4j.relationship.BaseRelationship;

public class Neo4jTemplateTest {
	private static Logger logger = LoggerFactory.getLogger(Neo4jTemplateTest.class);

	private static Neo4jTemplate neo4jTemplate;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
		neo4jTemplate = appContext.getBean(Neo4jTemplate.class);
	}

	public BaseNode createTestNode() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);
		return bn;
	}

	@Test
	public void testCreateNodeAs() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());

		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);
		logger.debug(JSON.toJSONString(bn));
	}

	@Test
	public void testCreateNodeAsNoLabels() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("groupId", 123);

		GroupNode gn = neo4jTemplate.createNodeAs(GroupNode.class, properties);
		logger.debug(JSON.toJSONString(gn));
	}

	@Test
	public void testUpdateNode() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());

		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);
		logger.debug(JSON.toJSONString(bn));

		Thread.sleep(5000);
		bn.setUpdatedAt(System.currentTimeMillis());
		BaseNode bn2 = neo4jTemplate.updateNode(bn);

		logger.debug(JSON.toJSONString(bn));
		logger.debug(JSON.toJSONString(bn2));
		// assertEquals();
	}

	@Test
	public void testGetNode() throws Exception {
		BaseNode bn = createTestNode();
		BaseNode bn2 = neo4jTemplate.getNode(BaseNode.class, bn.getNodeId());

		assertTrue(bn.getNodeId().toString().equals(bn2.getNodeId().toString()));
	}

	@Test
	public void testDeleteNode() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);

		boolean flag = neo4jTemplate.deleteNode(bn.getNodeId());
		assertTrue(flag);
	}

	@Test
	public void testDeleteNodeEntity() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);

		boolean flag = neo4jTemplate.deleteNode(bn);
		assertTrue(flag);
	}

	@Test
	public void testCreateRelationshipsBetween() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1.getNodeId(),
				b2.getNodeId(), properties);

		logger.debug(JSON.toJSONString(f));
		assertTrue(f.getRelId() > 0);
	}

	@Test
	public void testCreateRelationshipsBetweenNode() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1, b2, properties);

		logger.debug(JSON.toJSONString(f));
		assertTrue(f.getRelId() > 0);
	}

	@Test
	public void testUpdateRelationship() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("startNode", b1);
		properties.put("endNode", b2);
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1.getNodeId(),
				b2.getNodeId(), properties);
		logger.debug(JSON.toJSONString(f));

		Thread.sleep(5000);

		Long timeNow = System.currentTimeMillis();
		f.setUpdatedAt(timeNow);
		BaseRelationship f2 = neo4jTemplate.updateRelationship(f);
		assertTrue(f2.getUpdatedAt() == timeNow);
	}

	@Test
	public void testDeleteRelationship() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1.getNodeId(),
				b2.getNodeId(), properties);
		logger.debug(JSON.toJSONString(f));

		boolean flag = neo4jTemplate.deleteRelationship(f.getRelId());
		logger.debug(JSON.toJSONString(flag));
		assertTrue(flag);
	}

	@Test
	public void testDeleteRelationshipEntity() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1.getNodeId(),
				b2.getNodeId(), properties);
		logger.debug(JSON.toJSONString(f));

		boolean flag = neo4jTemplate.deleteRelationship(f);
		logger.debug(JSON.toJSONString(flag));
		assertTrue(flag);
	}
}
