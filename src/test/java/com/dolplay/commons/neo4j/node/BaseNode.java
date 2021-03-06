package com.dolplay.commons.neo4j.node;

import com.dolplay.commons.neo4j.annotation.GraphId;
import com.dolplay.commons.neo4j.annotation.NodeEntity;
import com.dolplay.commons.neo4j.constant.LabelTypeName;

@NodeEntity(label = LabelTypeName.LABLE)
public class BaseNode {
	@GraphId
	private Long nodeId;

	private Long createdAt;
	private Long updatedAt;

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
}
