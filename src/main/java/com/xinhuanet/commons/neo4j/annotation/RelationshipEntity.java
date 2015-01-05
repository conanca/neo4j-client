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
public @interface RelationshipEntity {

	/**
	 * @return true if the property names default to field names, otherwise the FQN of the class will be prepended
	 */
	boolean useShortNames() default true;

	/**
	 * @return the relationship-type of this entity will be used if no other type is provided, either as field inside
	 *         the relationship-entity or via an annotation in the node-entity, this type will take precedence over
	 *         node-entity #
	 *         field names.
	 */
	String type() default "";
}
