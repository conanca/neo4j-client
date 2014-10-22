package com.xinhuanet.commons.neo4j;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.xinhuanet.entity.ResData;

@Service("neo4jClient")
public class Neo4jClient extends RestfulClient {

	public Neo4jClient(String url) {
		super(url);
	}

	public Neo4jClient(String url, String httpAuthUserName, String httpAuthPassword) {
		super(url, httpAuthUserName, httpAuthPassword);
	}

	public ResData createNode(Integer userId, Map<String, String> param) {
		ResData resData = new ResData();
		// Response response =
		return resData;
	}
	// boolean createRelationship(Integer startUserId, Integer endUserId, String
	// relType, Map<String, String> map);
	//
	// boolean updateNode(Integer userId, Integer oldTypecode, Integer
	// newTypeCode, Map<String, String> map);
	//
	// boolean updateRelationship(Integer startUserId, Integer endUserId, String
	// relType, Map<String, String> map);
	//
	// boolean deleteRelationship(Integer startUserId, Integer endUserId, String
	// relType);

}
