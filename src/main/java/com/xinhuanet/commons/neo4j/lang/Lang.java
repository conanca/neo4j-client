package com.xinhuanet.commons.neo4j.lang;

import java.util.ArrayList;

public class Lang {
	/**
	 * 较方便的创建一个列表，比如：
	 * 
	 * <pre>
	 * List&lt;Pet&gt; pets = Lang.list(pet1, pet2, pet3);
	 * </pre>
	 * 
	 * 注，这里的 List，是 ArrayList 的实例
	 * 
	 * @param eles
	 *            可变参数
	 * @return 列表对象
	 */
	public static <T> ArrayList<T> list(T... eles) {
		ArrayList<T> list = new ArrayList<T>(eles.length);
		for (T ele : eles) {
			list.add(ele);
		}
		return list;
	}
}
