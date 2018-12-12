package com.xuzp.insuredxmltool.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类简述： 排序工具
 *
 * @author za-xusiyong
 * @version 2018/1/3 13:56
 */
@Slf4j
public class ListTool {
    /**
     * 简述：  对List<Map>进行升序排序
     *
     * @param maplist,orderKey
     * @return void
     * @author za-xusiyong
     * @version 13:56 2018/1/3
     * @since modify by [za-xusiyong 13:56 2018/1/3 ]
     */
    public static void orderMapList(List maplist, String orderKey) {
        SortUtil.sort(maplist, orderKey);
    }

    /**
     * 简述： 对List<Map>进行倒序排序
     *
     * @param maplist,orderKey
     * @return void
     * @author za-xusiyong
     * @version 13:57 2018/1/3
     * @since modify by [za-xusiyong 13:57 2018/1/3 ]
     */
    public static void orderMapListDesc(List maplist, String orderKey) {
        SortUtil.sort(maplist, orderKey, SortUtil.DESC);

    }

    /**
     * 简述： 对List<Object>进行升序排序,要求Object为pojo对象
     *
     * @param list,orderKey
     * @return void
     * @author za-xusiyong
     * @version 13:57 2018/1/3
     * @since modify by [za-xusiyong 13:57 2018/1/3 ]
     */
    public static void orderObjList(List list, String orderKey) {
        SortUtil.sort(list, orderKey);
    }

    /**
     * 简述： 对List<Object>进行倒序排序,要求Object为pojo对象
     *
     * @param list,orderKey
     * @return void
     * @author za-xusiyong
     * @version 13:58 2018/1/3
     * @since modify by [za-xusiyong 13:58 2018/1/3 ]
     */
    public static void orderObjListDesc(List list, String orderKey) {
        SortUtil.sort(list, orderKey, SortUtil.DESC);
    }

    /**
     * 简述： 将数组转化为List
     *
     * @param array
     * @return java.util.List
     * @author za-xusiyong
     * @version 13:58 2018/1/3
     * @since modify by [za-xusiyong 13:58 2018/1/3 ]
     */
    public static List arrayToList(Object[] array) {
        List list = new ArrayList();
        if (array != null)
            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
        return list;

    }

    /**
     * 简述： 将List转化为数组
     *
     * @param list
     * @return java.lang.Object[]
     * @author za-xusiyong
     * @version 13:59 2018/1/3
     * @since modify by [za-xusiyong 13:59 2018/1/3 ]
     */
    public static Object[] listToArray(List list) {
        if (list == null) {
            return null;
        }
        Object[] objs = list.toArray();

        return objs;

    }

    /**
     * 简述： 判断对象是否在列表中
     *
     * @param obj,list
     * @return boolean
     * @author za-xusiyong
     * @version 14:00 2018/1/3
     * @since modify by [za-xusiyong 14:00 2018/1/3 ]
     */
    public static boolean isInList(Object obj, List list) {
        boolean result = false;
        if (obj == null || list == null) {
            return false;
        }
        for (Object object : list) {
            if (obj.equals(object)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 简述： 判断对象是否在数组中
     *
     * @param obj,list
     * @return boolean
     * @author za-xusiyong
     * @version 14:00 2018/1/3
     * @since modify by [za-xusiyong 14:00 2018/1/3 ]
     */
    public static boolean isInList(Object obj, Object[] list) {
        boolean result = false;
        for (Object object : list) {
            if (obj.equals(object)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 简述： 将list 顺序打乱
     *
     * @param list
     * @return void
     * @author za-xusiyong
     * @version 14:00 2018/1/3
     * @since modify by [za-xusiyong 14:00 2018/1/3 ]
     */
    public static void randomList(List list) {
        Collections.shuffle(list);
    }

    /**
     * 简述： 判断列表是否为null或长度为0
     *
     * @param list
     * @return boolean
     * @author za-xusiyong
     * @version 14:01 2018/1/3
     * @since modify by [za-xusiyong 14:01 2018/1/3 ]
     */
    public static boolean isEmpty(List list) {
        boolean result = false;
        if (list == null) {
            result = true;
        } else if (list.isEmpty()) {
            result = true;
        }
        return result;
    }

    /**
     * 简述： 清空list列表
     *
     * @param list
     * @return void
     * @author za-xusiyong
     * @version 14:02 2018/1/3
     * @since modify by [za-xusiyong 14:02 2018/1/3 ]
     */
    public static void clear(List list) {
        if (list != null) {
            list.clear();
        }
    }

    /**
     * 简述： 判断list不为空
     *
     * @param list
     * @return boolean
     * @author za-xusiyong
     * @version 14:02 2018/1/3
     * @since modify by [za-xusiyong 14:02 2018/1/3 ]
     */
    public static boolean isNotNull(List list) {
        boolean flag = false;
        if (list != null && list.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 简述： 判断list为空
     *
     * @param list
     * @return boolean
     * @author za-xusiyong
     * @version 14:03 2018/1/3
     * @since modify by [za-xusiyong 14:03 2018/1/3 ]
     */
    public static boolean isNull(List list) {
        boolean flag = false;
        if (list == null || list.size() == 0) {
            flag = true;
        }
        return flag;
    }

}
