package com.xinhuanet.commons.neo4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.xinhuanet.commons.neo4j.lang.Lang;

@Service("neo4jClient")
public class Neo4jClient extends RestfulClient {
	private static Logger logger = LoggerFactory.getLogger(Neo4jClient.class);

	public Neo4jClient(String url) {
		super(url);
	}

	public Neo4jClient(String url, String httpAuthUserName, String httpAuthPassword) {
		super(url, httpAuthUserName, httpAuthPassword);
	}

	public List<String[]> cypherQuery(String cypher, Map<String, String> para) {
		Map restParaMap = new HashMap<String, String>();
		restParaMap.put("query", cypher);
		restParaMap.put("params", para);
		Response resp = this.post(Lang.list("cypher"), JSON.toJSONString(restParaMap));
		String respJson = resp.readEntity(String.class);
		Map respMap = JSON.parseObject(respJson, Map.class);
		Object dataObj = respMap.get("data");
		String dataStr = dataObj.toString();
		List<String[]> data = JSON.parseArray(dataStr, String[].class);
		return data;

	}

	public Long createNode(Map<String, String> para) {
		Response resp = this.post(Lang.list("node"), para);
		if (resp.getStatus() == 201) {
			String respJson = resp.readEntity(String.class);
			Map respMap = JSON.parseObject(respJson, Map.class);
			Object selfObj = respMap.get("self");
			String selfStr = selfObj.toString();
			long selfNodeId = Long.parseLong(selfStr.substring(selfStr.lastIndexOf("/") + 1));
			return selfNodeId;
		} else {
			return -1L;
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
		Response resp = this.post(Lang.list("node", String.valueOf(nodeId), "labels"), JSON.toJSONString(labels));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			logger.error("" + resp.getStatus() + JSON.toJSONString(resp));
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
