package com.xinhuanet.commons.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestA {

	private static Neo4jClientPool neo4jClientPool;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
		neo4jClientPool = appContext.getBean(Neo4jClientPool.class);

	}

	@Test
	public void test() throws NoSuchElementException, IllegalStateException, Exception {
		Neo4jClient client = neo4jClientPool.borrowObject();
		List<String> paths = new ArrayList<String>();
		paths.add("node");
		paths.add("1025");
		String a = client.get(paths);
		System.out.println(a);
	}

}
