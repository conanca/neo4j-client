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

	public Boolean createNode(Integer userId, Integer typeCode, Map<String, String> param) {

		return true;
	}

	public ResData getNode(Integer userid) {
		return null;
	}

	public Boolean updateNode(Integer userId, Map<String, String> param) {
		return true;
	}

	public Boolean changeNodeType(Integer userId, Integer oldTypeCode, Integer newTypeCode) {
		return true;
	}

	public Boolean deleteNodeRelation(Integer userId) {
		return true;
	}

	public Boolean deleteNode(Integer userId) {
		return true;
	}

	public Boolean createRelation(Integer startUserId, Integer endUserId, String relType, Map<String, String> param) {
		return true;
	}

	public Boolean updateRelation(Integer startUserId, Integer endUserId, String relType, Map<String, String> param) {
		return true;
	}

	public Boolean deleteRelation(Integer startUserId, Integer endUserId, String relType) {
		return true;
	}

}
