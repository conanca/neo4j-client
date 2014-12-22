package com.xinhuanet.commons.neo4j;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.TestCase.assertNotNull;

public class Neo4jClientPoolTest {
    private static Logger logger = LoggerFactory.getLogger(Neo4jClientPoolTest.class);

    private static Neo4jClientPool neo4jClientPool;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("test-context.xml");
        neo4jClientPool = appContext.getBean(Neo4jClientPool.class);

    }

    @Test
    public void test() throws Exception {
        Neo4jClient client = neo4jClientPool.borrowObject();
        assertNotNull(client);
        assertNotNull(client.getUrl());
        logger.debug(client.getUrl());
        neo4jClientPool.returnObject(client);
    }

}
