package com.dolplay.commons.neo4j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RestfulClientTest.class, Neo4jClientPoolTest.class, Neo4jClientTest.class,})
public class AllTests {

}
