package com.xinhuanet.commons.neo4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.xinhuanet.commons.neo4j.annotation.GraphId;
import com.xinhuanet.commons.neo4j.annotation.RelationshipEntity;

public class Neo4jTemplate {

	private Neo4jClientPool neo4jClientPool;

	public Neo4jTemplate(com.xinhuanet.commons.neo4j.Neo4jClientPool neo4jClientPool) {
		this.neo4jClientPool = neo4jClientPool;
	}

	/**
	 * 创建节点
	 * 
	 * @param mapped entity class or Node.class
	 * @param the properties that should be initially set on the node
	 * @return
	 * @throws Exception
	 */
	<T> T createNodeAs(Class<T> target, Map<String, Object> properties) throws Exception {
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

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		Long nodeId = neo4jClient.createNode(para);
		neo4jClientPool.returnObject(neo4jClient);

		for (Field d : field) {
			if (d.isAnnotationPresent(GraphId.class)) {
				d.setAccessible(true);
				d.set(t, nodeId);
			}
		}

		return t;
	}

	<T> T updateNode(T entity) throws Exception {
		Map<String, Object> para = new HashMap<String, Object>();
		Long nodeId = null;
		Field[] field = entity.getClass().getDeclaredFields();
		Method metd = null;

		for (Field f : field) {
			f.setAccessible(true);
			String fieldName = f.getName();
			metd = entity.getClass().getMethod("get" + initName(fieldName));
			Object value = metd.invoke(entity);
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
	 * @param target
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	<T> T getNode(Class<T> target, long nodeId) throws Exception {
		if (nodeId < 0) {
			throw new Exception("id is negative");
		}

		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		Map map = neo4jClient.getNodeProperties(nodeId);
		neo4jClientPool.returnObject(neo4jClient);

		if (map != null) {
			T t = target.newInstance();
			Field[] field = t.getClass().getDeclaredFields();
			for (Field f : field) {
				f.setAccessible(true);
				String fieldName = f.getName();
				Object value = map.get(fieldName);
				if (value != null) {
					f.set(t, value);
				}
			}
			return t;
		}
		return null;
	}

	/**
	 * 删除节点
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	Boolean deleteNode(long nodeId) throws Exception {
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
	 * 创建关系
	 * 
	 * @param target
	 * @param startNodeId
	 * @param endNodeId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	<T> T createRelationshipBetween(Class<T> target, Long startNodeId, Long endNodeId, Map<String, Object> para)
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
	 * 更新关系
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	<T> T updateRelationshipBetween(T entity) throws Exception {
		Map<String, Object> para = new HashMap<String, Object>();
		Long nodeId = null;
		Field[] field = entity.getClass().getDeclaredFields();
		Method metd = null;

		for (Field f : field) {
			f.setAccessible(true);
			String fieldName = f.getName();
			metd = entity.getClass().getMethod("get" + initName(fieldName));
			Object value = metd.invoke(entity);
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
	 * @param relationshipId
	 * @throws Exception
	 */
	void deleteRelationshipBetween(Long relationshipId) throws Exception {
		Neo4jClient neo4jClient = neo4jClientPool.borrowObject();
		neo4jClient.deleteRelationship(relationshipId);
		neo4jClientPool.returnObject(neo4jClient);
	}

	public String initName(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}

}
