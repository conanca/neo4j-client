package com.xinhuanet.commons.neo4j;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

@Service("neo4jClient")
public class Neo4jClient extends RestfulClient {

	public Neo4jClient(String url) {
		super(url);
	}

	public Neo4jClient(String url, String httpAuthUserName, String httpAuthPassword) {
		super(url, httpAuthUserName, httpAuthPassword);
	}

	public Boolean createNode(Map<String, String> para) {
		Response resp = this.post(Lang.list("node"), para);
		if (resp.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}

	public Map getNode(Long nodeId) {
		Response resp = this.get(Lang.list("node", String.valueOf(nodeId)));
		if (resp.getStatus() == 200) {
			String jsonResp = resp.readEntity(String.class);
			if (!StringUtils.isEmpty(jsonResp)) {
				Map m = JSON.parseObject(jsonResp, Map.class);
				return m;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Boolean addLabels(Long nodeId, String... labels) {
		Response resp = this.post(Lang.list("node", String.valueOf(nodeId)), JSON.toJSONString(labels));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean deleteLabel(Long nodeId, String label) {
		Response resp = this.delete(Lang.list("node", String.valueOf(nodeId), "labels", JSON.toJSONString(label)));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateNode(Long nodeId, Map<String, String> para) {
		Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "properties"), para);
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean deleteNode(Long nodeId) {
		Response resp = this.delete(Lang.list("node", String.valueOf(nodeId)));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
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
