package com.dolplay.commons.neo4j.constant;

/**
 * 关系名称
 * 
 * @author chenwc
 * 
 */
public final class RelTypeName {
	public static final String FOLLOWS = "FOLLOWS"; // 关注
	public static final String FRIEND_REQUEST = "FRIEND_REQUEST"; // 好友请求
	public static final String FRIEND = "FRIEND"; // 好友
	public static final String BLACKLISTS = "BLACKLISTS";// 在黑名单中
	public static final String BLOCKS = "BLOCKS";// 屏蔽
	public static final String OBSTRUCTS = "OBSTRUCTS";// 阻止
	public static final String JOIN = "JOIN";// 加入，从属于
	public static final String JOIN_INVITE = "JOIN_INVITE";// 加入请求
}
