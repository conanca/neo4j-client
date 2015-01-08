package com.xinhuanet.commons.neo4j;

import com.xinhuanet.commons.neo4j.annotation.GraphId;
import com.xinhuanet.commons.neo4j.annotation.NodeEntity;
import com.xinhuanet.commons.neo4j.annotation.RelationshipEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Neo4jTemplate {

	private Neo4jClientPool neo4jClientPool;

	public Neo4jTemplate(com.xinhuanet.commons.neo4j.Neo4jClientPool neo4jClientPool) {
		this.neo4jClientPool = neo4jClientPool;
	}

	/**
	 * 创建节点
	 * 
	 * @param target 要创建节点的class对象
	 * @param properties 对象参数
	 * @return 节点对象
	 * @throws Exception
	 */
	public <T> T createNodeAs(Class<T> target, Map<String, Object> properties) throws Exception {
		T t = target.newInstance();

		Map<String, Object> para = new HashMap<String, Object>();
		Field[] field = t.getClass().getDeclaredFields();
		for (Field f : field) {
			String fieldName = f.getName();
			Object value = properties.get(fieldName);
			if (value != null) {
				// t属性初始化
				f.setAccessible(true);
				f.set(t, value);
				// para赋值
				para.put(fieldName, value);
			}
		}

		// 标签
		String[] labels = null;
		Annotation[] as = t.getClass().getAnnotations();
		if (as != null && as.length > 0) {
			for (Annotation a : as) {
				if (a.annotationType() == NodeEntity.class) {
					NodeEntity re = (NodeEntity) a;
					labels = re.label();
					break;
				}
			}
		}
		if (labels == null || labels.length == 0) {
			labels = new String[1];
			labels[0] = t.getClass().getSimpleName();
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		Long nodeId = neo4jClient.createNode(para);
		neo4jClient.addNodeLabel(nodeId, labels);
		neo4jClientPool.returnObject(neo4jClient);

		for (Field d : field) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				d.set(t, nodeId);
			}
		}

		return t;
	}

	/**
	 * @param entity 更新对象实体
	 * @return 节点对象
	 * @throws Exception
	 */
	public <T> T updateNode(T entity) throws Exception {
		Map<String, Object> para = new HashMap<String, Object>();
		Long nodeId = null;
		Field[] field = entity.getClass().getDeclaredFields();

		for (Field f : field) {
			f.setAccessible(true);
			String fieldName = f.getName();
			Object value = f.get(entity);
			if (value != null) {
				if (f.isAnnotationPresent(GraphId.class)) {
					nodeId = (Long) value;
				} else {
					// para赋值
					para.put(fieldName, value);
				}
			}
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		neo4jClient.updateNodeProperties(nodeId, para);
		neo4jClientPool.returnObject(neo4jClient);

		return entity;
	}

	/**
	 * 获取节点
	 * 
	 * @param target 要获取节点类的class
	 * @param nodeId 节点的nodeId
	 * @return 节点对象
	 * @throws Exception
	 */
	public <T> T getNode(Class<T> target, long nodeId) throws Exception {
		if (nodeId < 0) {
			throw new Exception("id is negative");
		}
		T t = target.newInstance();

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		Map map = neo4jClient.getNodeProperties(nodeId);
		neo4jClientPool.returnObject(neo4jClient);

		if (map != null) {
			Field[] field = t.getClass().getDeclaredFields();
			for (Field f : field) {
				f.setAccessible(true);
				String fieldName = f.getName();
				Object value = map.get(fieldName);
				if (value != null) {
					f.set(t, value);
				}
			}
		}

		Field[] field = t.getClass().getDeclaredFields();
		for (Field d : field) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				d.set(t, nodeId);
				break;
			}
		}

		return t;
	}

	/**
	 * 删除节点
	 * 
	 * @param nodeId 要删除节点的nodeId
	 * @return true:删除成功 false:失败
	 * @throws Exception
	 */
	public Boolean deleteNode(Long nodeId) throws Exception {
		Boolean result = false;
		if (nodeId < 0) {
			throw new Exception("id is negative");
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		result = neo4jClient.deleteNode(nodeId);
		neo4jClientPool.returnObject(neo4jClient);

		return result;
	}

	/**
	 * @param node 要删除的节点
	 * @return true:删除成功 false:失败
	 * @throws Exception
	 */
	public Boolean deleteNode(Object node) throws Exception {
		Long nodeId = null;
		Field[] field = node.getClass().getDeclaredFields();
		for (Field d : field) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				Object value = d.get(node);
				nodeId = (Long) value;
				break;
			}
		}
		return deleteNode(nodeId);
	}

	/**
	 * 创建关系
	 * 
	 * @param target 要创建关系的class
	 * @param startNodeId 起始节点的nodeId
	 * @param endNodeId 目标节点的nodeId
	 * @param para 关系参数
	 * @return 关系对象
	 * @throws Exception
	 */
	public <T> T createRelationshipBetween(Class<T> target, Long startNodeId, Long endNodeId, Map<String, Object> para)
			throws Exception {
		T t = target.newInstance();

		String relType = null;
		Annotation[] as = t.getClass().getAnnotations();
		for (Annotation a : as) {
			if (a.annotationType() == RelationshipEntity.class) {
				RelationshipEntity re = (RelationshipEntity) a;
				relType = re.type();
			}
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		Long nodeId = neo4jClient.createRelationship(startNodeId, endNodeId, relType, para);
		neo4jClientPool.returnObject(neo4jClient);

		Field[] field = t.getClass().getDeclaredFields();
		for (Field f : field) {
			if (f.isAnnotationPresent(GraphId.class)) {
				f.setAccessible(true);
				f.set(t, nodeId);
				continue;
			}

			String fieldName = f.getName();
			Object value = para.get(fieldName);
			if (value != null) {
				// t属性初始化
				f.setAccessible(true);
				f.set(t, value);
			}
		}

		return t;
	}

	/**
	 * @param target 要创建关系的class
	 * @param startNode 起始节点
	 * @param endNode 目标节点
	 * @param para 关系参数
	 * @return 关系对象
	 * @throws Exception
	 */
	public <T> T createRelationshipBetween(Class<T> target, Object startNode, Object endNode, Map<String, Object> para)
			throws Exception {
		Long startNodeId = null;
		Long endNodeId = null;

		Field[] fieldStartNode = startNode.getClass().getDeclaredFields();
		for (Field d : fieldStartNode) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				Object value = d.get(startNode);
				startNodeId = (Long) value;
				break;
			}
		}

		Field[] fieldEndNode = endNode.getClass().getDeclaredFields();
		for (Field d : fieldEndNode) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				Object value = d.get(endNode);
				endNodeId = (Long) value;
				break;
			}
		}

		T t = createRelationshipBetween(target, startNodeId, endNodeId, para);
		return t;
	}

	/**
	 * 更新关系
	 * 
	 * @param entity 关系对象
	 * @return 关系对象
	 * @throws Exception
	 */
	public <T> T updateRelationship(T entity) throws Exception {
		Map<String, Object> para = new HashMap<String, Object>();
		Long nodeId = null;
		Field[] field = entity.getClass().getDeclaredFields();

		for (Field f : field) {
			f.setAccessible(true);
			String fieldName = f.getName();
			Object value = f.get(entity);
			if (value != null) {
				if (f.isAnnotationPresent(GraphId.class)) {
					nodeId = (Long) value;
				} else {
					// para赋值
					para.put(fieldName, value);
				}
			}
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		neo4jClient.updateRelationshipProperties(nodeId, para);
		neo4jClientPool.returnObject(neo4jClient);

		return entity;
	}

	/**
	 * 删除关系
	 * 
	 * @param relationshipId 关系Id
	 * @return true:删除成功 false:失败
	 * @throws Exception
	 */
	public Boolean deleteRelationship(Long relationshipId) throws Exception {
		Boolean result = false;

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		result = neo4jClient.deleteRelationship(relationshipId);
		neo4jClientPool.returnObject(neo4jClient);
		return result;
	}

	/**
	 * @param relationship 关系对象
	 * @return true:删除成功 false:失败
	 * @throws Exception
	 */
	public Boolean deleteRelationship(Object relationship) throws Exception {
		Long relationshipId = null;
		Field[] field = relationship.getClass().getDeclaredFields();
		for (Field d : field) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				Object value = d.get(relationship);
				relationshipId = (Long) value;
				break;
			}
		}
		if (relationshipId != null) {
			return deleteRelationship(relationshipId);
		} else {
			return false;
		}
	}
}
