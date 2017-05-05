package com.dolplay.commons.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dolplay.commons.neo4j.node.BaseNode;

public class TestUtils {
	private static Logger logger = LoggerFactory.getLogger(TestUtils.class);

	public static BaseNode initNode(Neo4jTemplate neo4jTemplate) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("createdAt", System.currentTimeMillis());
		properties.put("updatedAt", System.currentTimeMillis());

		BaseNode bn = neo4jTemplate.createNodeAs(BaseNode.class, properties);
		return bn;
	}
}
