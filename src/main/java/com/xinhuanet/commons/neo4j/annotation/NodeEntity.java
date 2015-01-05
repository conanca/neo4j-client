package com.xinhuanet.commons.neo4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Persistent
public @interface NodeEntity {
	/**
	 * @return true if the property names default to field names, otherwise the FQN of the class will be prepended
	 */
	boolean useShortNames() default true;

	/**
	 * <p>
	 * If partial is set, then construction of the node is delayed until the entity's id has been set by another
	 * persistent store. Only {@link org.springframework.data.neo4j.annotation.GraphProperty} annotated fields will be
	 * handled by the graph storage.
	 * </p>
	 * 
	 * <p>
	 * Currently, only JPA storage is supported for partial node entities.
	 * </p>
	 * 
	 * @return true if the entity is only partially managed by the Neo4jNodeBacking aspect.
	 */
	boolean partial() default false;

}
