package com.xinhuanet.commons.neo4j;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Neo4jClientPool extends GenericObjectPool<Neo4jClient> {

	public Neo4jClientPool(PooledObjectFactory<Neo4jClient> factory) {
		super(factory);
	}

}
