package com.xinhuanet.commons.neo4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Neo4jTemplate {
	/**
	 * @param mapped entity class or Node.class
	 * @param the properties that should be initially set on the node
	 * @return
	 * @throws Exception
	 */
	<T> T createNodeAs(Class<T> target, Map<String, Object> properties) throws Exception {
		T t = target.newInstance();

		Field[] field = t.getClass().getFields();
		for (Field f : field) {
			String fieldName = f.getName();
			Object value = properties.get(fieldName);
			if (value != null) {
				f.set(fieldName, value);
			}
		}
		Map<String, String> para = new HashMap<String, String>();
		// para.put(t.getClass().getName(), t.getClass().g);

		return t;
	}

	<T> T getNode(long id) {
		return null;
	}

	<R> Iterable<R> getRelationshipsBetween(Object start, Object end, Class<R> relationshipEntityClass,
			String relationshipType) {
		return null;
	}

	<T> T save(T entity) {
		return entity;
	}

	void delete(Object entity) {

	}

	void deleteRelationshipBetween(Object start, Object end, String type) {

	}

}
