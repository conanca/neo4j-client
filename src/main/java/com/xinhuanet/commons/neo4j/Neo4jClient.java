package com.xinhuanet.commons.neo4j;

import com.alibaba.fastjson.JSON;
import com.xinhuanet.commons.neo4j.lang.Lang;
import com.xinhuanet.commons.neo4j.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neo4jClient extends RestfulClient {
	private static Logger logger = LoggerFactory.getLogger(Neo4jClient.class);

	public Neo4jClient(String url) {
		super(url);
	}

	public Neo4jClient(String url, String httpAuthUserName, String httpAuthPassword) {
		super(url, httpAuthUserName, httpAuthPassword);
	}

	/**
	 * 执行cypher语句
	 * 
	 * @param cypher
	 * @param para cypher中的参数Map
	 * @return 返回cypher return的结果列表，其中元素为JSON字符串数组，表示每个Column
	 */
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

	/**
	 * 创建节点并指定属性
	 * 
	 * @param para 属性Map
	 * @return
	 */
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

	/**
	 * 获取指定Id的节点的属性
	 * 
	 * @param nodeId
	 * @return 属性Map
	 */
	public Map getNodeProperties(Long nodeId) {
		Response resp = this.get(Lang.list("node", String.valueOf(nodeId)));
		if (resp.getStatus() == 200) {
			String jsonResp = resp.readEntity(String.class);
			if (!Strings.isEmpty(jsonResp)) {
				Map m = JSON.parseObject(jsonResp, Map.class);
				Object dataObj = m.get("data");
				Map dataMap = JSON.parseObject(dataObj.toString(), Map.class);
				return dataMap;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取指定Id的节点的Labels
	 * 
	 * @param nodeId
	 * @return Label数组
	 */
	public String[] getNodeLabels(Long nodeId) {
		Response resp = this.get(Lang.list("node", String.valueOf(nodeId), "labels"));
		if (resp.getStatus() == 200) {
			String jsonResp = resp.readEntity(String.class);
			if (!Strings.isEmpty(jsonResp)) {
				return JSON.parseObject(jsonResp, String[].class);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 为指定Id的节点添加Label
	 * 
	 * @param nodeId
	 * @param labels
	 * @return
	 */
	public Boolean addNodeLabel(Long nodeId, String... labels) {
		Response resp = this.post(Lang.list("node", String.valueOf(nodeId), "labels"), JSON.toJSONString(labels));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			logger.error("" + resp.getStatus() + JSON.toJSONString(resp));
			return false;
		}
	}

	/**
	 * 删除改指定Id的节点原有Label，并设置新的指定Label
	 * 
	 * @param nodeId
	 * @param labels
	 * @return
	 */
	public Boolean updateNodeLabel(Long nodeId, String... labels) {
		Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "labels"), JSON.toJSONString(labels));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			logger.error("" + resp.getStatus() + JSON.toJSONString(resp));
			return false;
		}
	}

	/**
	 * 删除改指定Id的节点的指定Label
	 * 
	 * @param nodeId
	 * @param label
	 * @return
	 */
	public Boolean deleteNodeLabel(Long nodeId, String... label) {
		Response resp = this.delete(Lang.list("node", String.valueOf(nodeId), "labels", JSON.toJSONString(label)));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置指定Id的节点的指定属性的值
	 * 
	 * @param nodeId
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public Boolean updateNodeProperty(Long nodeId, String propertyName, String propertyValue) {
		Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "properties", propertyName), propertyValue);
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除改指定Id的节点原有属性，并设置新的指定属性
	 * 
	 * @param nodeId
	 * @param para 属性Map
	 * @return
	 */
	public Boolean updateNodeProperties(Long nodeId, Map<String, String> para) {
		Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "properties"), para);
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除改指定Id的节点
	 * 
	 * @param nodeId
	 * @return
	 */
	public Boolean deleteNode(Long nodeId) {
		Response resp = this.delete(Lang.list("node", String.valueOf(nodeId)));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建关系
	 * 
	 * @param startNodeId 起始节点Id
	 * @param endNodeId 终止节点Id
	 * @param relType 关系类型
	 * @param para 属性Map
	 * @return
	 */
	public Long createRelationship(Long startNodeId, Long endNodeId, String relType, Map<String, String> para) {
		Map paraMap = new HashMap();
		paraMap.put("to", this.getUrl() + "/node/" + endNodeId);
		paraMap.put("type", relType);
		paraMap.put("data", para);
		Response resp = this.post(Lang.list("node", String.valueOf(startNodeId), "relationships"),
				JSON.toJSONString(paraMap));
		if (resp.getStatus() == 201) {
			String respJson = resp.readEntity(String.class);
			Map respMap = JSON.parseObject(respJson, Map.class);
			Object selfObj = respMap.get("self");
			String selfStr = selfObj.toString();
			long selfRelationshipId = Long.parseLong(selfStr.substring(selfStr.lastIndexOf("/") + 1));
			return selfRelationshipId;
		} else {
			return -1L;
		}
	}

	/**
	 * 获取指定Id的关系的属性
	 * 
	 * @param nodeId
	 * @return 属性Map
	 */
	public Map getRelationshipProperties(Long nodeId) {
		Response resp = this.get(Lang.list("relationship", String.valueOf(nodeId), "properties"));
		if (resp.getStatus() == 200) {
			String jsonResp = resp.readEntity(String.class);
			if (!Strings.isEmpty(jsonResp)) {
				return JSON.parseObject(jsonResp, Map.class);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 设置指定Id的关系的指定属性的值
	 * 
	 * @param relationshipId
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public Boolean updateRelationshipProperty(Long relationshipId, String propertyName, String propertyValue) {
		Response resp = this.put(Lang.list("relationship", String.valueOf(relationshipId), "properties", propertyName),
				propertyValue);
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除指定Id的关系原有属性，并设置新的指定属性
	 * 
	 * @param relationshipId
	 * @param para 属性Map
	 * @return
	 */
	public Boolean updateRelationshipProperties(Long relationshipId, Map<String, String> para) {
		Response resp = this.put(Lang.list("relationship", String.valueOf(relationshipId), "properties"),
				JSON.toJSONString(para));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除指定Id的关系
	 * 
	 * @param relationshipId
	 * @return
	 */
	public Boolean deleteRelationship(Long relationshipId) {
		Response resp = this.delete(Lang.list("relationship", String.valueOf(relationshipId)));
		if (resp.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建节点并指定标签
	 * 
	 * @param labels
	 * @param para
	 * @return
	 */
	public Long createNode(String[] labels, Map<String, String> para) {
		long nodeId = this.createNode(para);
		addNodeLabel(nodeId, labels);
		return nodeId;
	}
}
