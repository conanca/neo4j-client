package com.dolplay.commons.neo4j.node;

import com.dolplay.commons.neo4j.annotation.NodeEntity;

@NodeEntity
public class GroupNode extends BaseNode {
	private int groupId;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
