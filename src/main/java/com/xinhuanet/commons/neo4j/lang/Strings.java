package com.xinhuanet.commons.neo4j.lang;

/**
 * Created by conanca on 14-12-12.
 */
public class Strings {

    /**
     * 如果此字符串为 null 或者为空串（""），则返回 true
     *
     * @param cs
     *            字符串
     * @return 如果此字符串为 null 或者为空，则返回 true
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }
}
