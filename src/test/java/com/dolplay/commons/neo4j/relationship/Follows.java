package com.dolplay.commons.neo4j.relationship;

import com.dolplay.commons.neo4j.annotation.RelationshipEntity;
import com.dolplay.commons.neo4j.constant.RelTypeName;

@RelationshipEntity(type = RelTypeName.FOLLOWS)
public class Follows extends BaseRelationship {
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
