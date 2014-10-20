package com.xinhuanet.commons.neo4j;

import java.util.NoSuchElementException;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestA {

	private static Neo4jClientFactory neo4jClientFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
		neo4jClientFactory = appContext.getBean(Neo4jClientFactory.class);
	}

	@Test
	public void test() throws NoSuchElementException, IllegalStateException, Exception {
		ObjectPool<Neo4jClient> pool = new GenericObjectPool<Neo4jClient>(neo4jClientFactory);
		Neo4jClient client = pool.borrowObject();
		client.post();
	}
}
