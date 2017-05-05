package com.dolplay.commons.neo4j;

import com.alibaba.fastjson.JSON;
import com.dolplay.commons.neo4j.lang.JsonUtils;
import com.dolplay.commons.neo4j.lang.Lang;
import com.dolplay.commons.neo4j.lang.Strings;
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
     * @param cypher cypher语句
     * @param para   cypher中的参数Map
     * @return 返回cypher return的结果列表，其中元素为JSON字符串数组，表示每个Column
     */
    public List<String[]> cypherQuery(String cypher, Map<String, Object> para) {
        Map<String, Object> restParaMap = new HashMap<String, Object>();
        restParaMap.put("query", cypher);
        restParaMap.put("params", para);
        Response resp = this.post(Lang.list("cypher"), restParaMap);
        String respJson = resp.readEntity(String.class);
        String dataStr = JsonUtils.getVale(respJson, "data");
        if (!Strings.isEmpty(dataStr)) {
            return JSON.parseArray(dataStr, String[].class);
        } else {
            logger.error("Invalid response : " + respJson);
            return null;
        }
    }

    /**
     * 创建节点并指定属性
     *
     * @param para 属性Map
     * @return 创建的节点Id
     */
    public Long createNode(Map<String, Object> para) {
        Response resp = this.post(Lang.list("node"), para);
        if (resp.getStatus() == 201) {
            String respJson = resp.readEntity(String.class);
            String selfStr = JsonUtils.getVale(respJson, "self");
            if (!Strings.isEmpty(selfStr) && selfStr.contains("/")) {
                return Long.parseLong(selfStr.substring(selfStr.lastIndexOf("/") + 1));
            } else {
                logger.error("Invalid response : " + respJson);
                return null;
            }
        } else {
            logger.error(JSON.toJSONString(resp));
            return null;
        }
    }

    /**
     * 获取指定Id的节点的属性
     *
     * @param nodeId 节点Id
     * @return 属性Map
     */
    public Map getNodeProperties(Long nodeId) {
        Response resp = this.get(Lang.list("node", String.valueOf(nodeId), "properties"));
        if (resp.getStatus() == 200) {
            String respJson = resp.readEntity(String.class);
            if (!Strings.isEmpty(respJson)) {
                return JSON.parseObject(respJson, Map.class);
            } else {
                logger.error("Invalid response : " + respJson);
                return null;
            }
        } else {
            logger.error(JSON.toJSONString(resp));
            return null;
        }
    }

    /**
     * 获取指定Id的节点的Labels
     *
     * @param nodeId 节点Id
     * @return Label数组
     */
    public String[] getNodeLabels(Long nodeId) {
        Response resp = this.get(Lang.list("node", String.valueOf(nodeId), "labels"));
        if (resp.getStatus() == 200) {
            String respJson = resp.readEntity(String.class);
            if (!Strings.isEmpty(respJson)) {
                return JSON.parseObject(respJson, String[].class);
            } else {
                logger.error("Invalid response : " + respJson);
                return null;
            }
        } else {
            logger.error(JSON.toJSONString(resp));
            return null;
        }
    }

    /**
     * 为指定Id的节点添加Label
     *
     * @param nodeId 节点Id
     * @param labels 标签数组
     * @return 操作是否成功
     */
    public Boolean addNodeLabel(Long nodeId, String[] labels) {
        Response resp = this.post(Lang.list("node", String.valueOf(nodeId), "labels"), labels);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除改指定Id的节点原有Label，并设置新的指定Label
     *
     * @param nodeId 节点Id
     * @param labels 新标签数组
     * @return 操作是否成功
     */
    public Boolean updateNodeLabel(Long nodeId, String[] labels) {
        Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "labels"), labels);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除改指定Id的节点的指定Label
     *
     * @param nodeId 节点Id
     * @param label  标签
     * @return 操作是否成功
     */
    public Boolean deleteNodeLabel(Long nodeId, String label) {
        Response resp = this.delete(Lang.list("node", String.valueOf(nodeId), "labels", label));
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 设置指定Id的节点的指定属性的值
     *
     * @param nodeId        节点Id
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return 操作是否成功
     */
    public Boolean updateNodeProperty(Long nodeId, String propertyName, String propertyValue) {
        Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "properties", propertyName), propertyValue);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除改指定Id的节点原有属性，并设置新的指定属性
     *
     * @param nodeId 节点Id
     * @param para   属性Map
     * @return 操作是否成功
     */
    public Boolean updateNodeProperties(Long nodeId, Map<String, Object> para) {
        Response resp = this.put(Lang.list("node", String.valueOf(nodeId), "properties"), para);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除改指定Id的节点
     *
     * @param nodeId 节点Id
     * @return 操作是否成功
     */
    public Boolean deleteNode(Long nodeId) {
        Response resp = this.delete(Lang.list("node", String.valueOf(nodeId)));
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 创建关系
     *
     * @param startNodeId 起始节点Id
     * @param endNodeId   终止节点Id
     * @param relType     关系类型
     * @param para        属性Map
     * @return 创建的关系Id
     */
    public Long createRelationship(Long startNodeId, Long endNodeId, String relType, Map<String, Object> para) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("to", this.getUrl() + "/node/" + endNodeId);
        paraMap.put("type", relType);
        paraMap.put("data", para);
        Response resp = this.post(Lang.list("node", String.valueOf(startNodeId), "relationships"),
                paraMap);
        if (resp.getStatus() == 201) {
            String respJson = resp.readEntity(String.class);
            String selfStr = JsonUtils.getVale(respJson, "self");
            if (!Strings.isEmpty(selfStr) && selfStr.contains("/")) {
                return Long.parseLong(selfStr.substring(selfStr.lastIndexOf("/") + 1));
            } else {
                logger.error("Invalid response : " + respJson);
                return null;
            }
        } else {
            logger.error(JSON.toJSONString(resp));
            return null;
        }
    }

    /**
     * 获取指定Id的关系的属性
     *
     * @param nodeId 节点Id
     * @return 属性Map
     */
    public Map getRelationshipProperties(Long nodeId) {
        Response resp = this.get(Lang.list("relationship", String.valueOf(nodeId), "properties"));
        if (resp.getStatus() == 200) {
            String respJson = resp.readEntity(String.class);
            if (!Strings.isEmpty(respJson)) {
                return JSON.parseObject(respJson, Map.class);
            } else {
                logger.error("Invalid response : " + respJson);
                return null;
            }
        } else {
            logger.error(JSON.toJSONString(resp));
            return null;
        }
    }

    /**
     * 设置指定Id的关系的指定属性的值
     *
     * @param relationshipId 关系Id
     * @param propertyName   属性名
     * @param propertyValue  属性值
     * @return 操作是否成功
     */
    public Boolean updateRelationshipProperty(Long relationshipId, String propertyName, Object propertyValue) {
        Response resp = this.put(Lang.list("relationship", String.valueOf(relationshipId), "properties", propertyName),
                propertyValue);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除指定Id的关系原有属性，并设置新的指定属性
     *
     * @param relationshipId 关系Id
     * @param para           属性Map
     * @return 操作是否成功
     */
    public Boolean updateRelationshipProperties(Long relationshipId, Map<String, Object> para) {
        Response resp = this.put(Lang.list("relationship", String.valueOf(relationshipId), "properties"),
                para);
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 删除指定Id的关系
     *
     * @param relationshipId 关系Id
     * @return 操作是否成功
     */
    public Boolean deleteRelationship(Long relationshipId) {
        Response resp = this.delete(Lang.list("relationship", String.valueOf(relationshipId)));
        if (resp.getStatus() == 204) {
            return true;
        } else {
            logger.error(JSON.toJSONString(resp));
            return false;
        }
    }

    /**
     * 创建节点并指定标签
     *
     * @param labels 标签数组
     * @param para   属性Map
     * @return 创建的节点Id
     */
    public Long createNode(String[] labels, Map<String, Object> para) {
        long nodeId = this.createNode(para);
        if (nodeId > 0) {
            addNodeLabel(nodeId, labels);
        }
        return nodeId;
    }
}
