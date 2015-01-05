package com.xinhuanet.commons.neo4j.node;

import com.xinhuanet.commons.neo4j.annotation.GraphId;
import com.xinhuanet.commons.neo4j.annotation.NodeEntity;

@NodeEntity
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
