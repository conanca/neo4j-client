package com.xinhuanet.commons.neo4j;

public class Neo4jClient extends RestfulClient {

	public Neo4jClient(String url) {
		super(url);
	}

	public Neo4jClient(String url, String httpAuthUserName, String httpAuthPassword) {
		super(url, httpAuthUserName, httpAuthPassword);
	}

	// TODO 节点和关系的增删改查方法，以及执行cypher的方法

}
