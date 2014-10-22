package com.xinhuanet.commons.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;

public class Neo4jClientPoolTest {
	private static Logger logger = LoggerFactory.getLogger(Neo4jClientPoolTest.class);

	private static Neo4jClientPool neo4jClientPool;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
		neo4jClientPool = appContext.getBean(Neo4jClientPool.class);

	}

	@Test
	public void test() throws NoSuchElementException, IllegalStateException, Exception {
		Neo4jClient client = neo4jClientPool.borrowObject();
		List<String> resources = new ArrayList<String>();
		resources.add("node");
		resources.add("1025");
		Response response = client.get(resources);
		logger.debug(JSON.toJSONString(response));
		logger.debug("Status:" + response.getStatus());
		logger.debug("Content:" + response.readEntity(String.class));
		neo4jClientPool.returnObject(client);
	}

}
