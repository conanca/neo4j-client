package com.dolplay.commons.neo4j.relationship;

import com.dolplay.commons.neo4j.annotation.EndNode;
import com.dolplay.commons.neo4j.annotation.Fetch;
import com.dolplay.commons.neo4j.annotation.GraphId;
import com.dolplay.commons.neo4j.annotation.RelationshipEntity;
import com.dolplay.commons.neo4j.annotation.StartNode;
import com.dolplay.commons.neo4j.constant.RelTypeName;
import com.dolplay.commons.neo4j.node.BaseNode;

@RelationshipEntity(type = RelTypeName.FOLLOWS)
public class BaseRelationship {
	@GraphId
	private Long relId;
	@EndNode
	@Fetch
	private BaseNode endNode;
	@StartNode
	@Fetch
	private BaseNode startNode;
	private Long createdAt;
	private Long updatedAt;

	public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public BaseNode getEndNode() {
		return endNode;
	}

	public void setEndNode(BaseNode endNode) {
		this.endNode = endNode;
	}

	public BaseNode getStartNode() {
		return startNode;
	}

	public void setStartNode(BaseNode startNode) {
		this.startNode = startNode;
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
