package com.xinhuanet.commons.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.xinhuanet.commons.neo4j.node.BaseNode;
import com.xinhuanet.commons.neo4j.relationship.BaseRelationship;
import com.xinhuanet.commons.neo4j.relationship.Follows;

public class Neo4jTemplateTest {
	private static Logger logger = LoggerFactory.getLogger(Neo4jTemplateTest.class);

	private static Neo4jTemplate neo4jTemplate;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
		neo4jTemplate = appContext.getBean(Neo4jTemplate.class);
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
	}

	@Test
	public void testGetNode() throws Exception {
		BaseNode bn = neo4jTemplate.getNode(BaseNode.class, 10738423L);
		logger.debug(JSON.toJSONString(bn));
	}

	@Test
	public void testDeleteNode() throws Exception {
		boolean bn = neo4jTemplate.deleteNode(10738423L);
		logger.debug(JSON.toJSONString(bn));
	}

	@Test
	public void testCreateRelationshipsBetween() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("startNode", b1);
		properties.put("endNode", b2);
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		BaseRelationship f = neo4jTemplate.createRelationshipBetween(BaseRelationship.class, b1.getNodeId(),
				b2.getNodeId(), properties);

		logger.debug(JSON.toJSONString(f));
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

		f.setUpdatedAt(System.currentTimeMillis());
		BaseRelationship f2 = neo4jTemplate.updateRelationship(f);
		logger.debug(JSON.toJSONString(f));
		logger.debug(JSON.toJSONString(f2));

	}

	@Test
	public void testDeleteRelationship() throws Exception {
		BaseNode b1 = TestUtils.initNode(neo4jTemplate);
		BaseNode b2 = TestUtils.initNode(neo4jTemplate);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("startNode", b1);
		properties.put("endNode", b2);
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());
		properties.put("remark", "123456");

		Follows f = neo4jTemplate.createRelationshipBetween(Follows.class, b1.getNodeId(), b2.getNodeId(), properties);
		logger.debug(JSON.toJSONString(f));

		neo4jTemplate.deleteRelationship(f.getRelId());
	}
}
