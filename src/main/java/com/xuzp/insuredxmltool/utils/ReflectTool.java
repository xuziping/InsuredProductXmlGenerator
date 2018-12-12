package com.xuzp.insuredxmltool.utils;

import com.xuzp.insuredxmltool.excel.model.险种信息;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 反射工具
 *
 * @author mac
 */

public class ReflectTool {

    public static final String[] exampleTypes = new String[]{"int", "Integer", "long", "Long", "Date", "String",
            "BigDecimal", "double", "Double", "boolean", "Boolean"};

    private static Logger log = LoggerFactory.getLogger(ReflectTool.class);

    /**
     * 执行对象中的方法
     *
     * @param obj
     * @param methodName
     * @param classes
     * @param args
     * @return
     */
    public static Object invoke(Object obj, String methodName, Class[] classes, Object[] args) {
        Object returnObj = null;
        try {
            Class c = obj.getClass();
            Method method = ReflectTool.getClassMethod(methodName, c, classes);
            if (method==null){
                log.error("invoke error:" + obj.getClass() + "." + methodName + "," + classes + "->" + args.getClass());
            }
            method.setAccessible(true);
            returnObj = method.invoke(obj, args);
        } catch (Exception e) {
            log.error("invoke error:" + obj.getClass() + "." + methodName + "," + classes + "->" + args.getClass(), e);
        }
        return returnObj;
    }

    /**
     * 执行对象中的方法
     *
     * @param obj
     * @param methodName
     * @param clazz
     * @param args
     * @return
     */
    public static Object invoke(Object obj, String methodName, Class clazz, Object args) {
        return invoke(obj, methodName, new Class[]{clazz}, new Object[]{args});
    }

    /**
     * 执行对象中的方法
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Object invoke(Object obj, String methodName) {
        Object returnObj = null;
        try {
            Class c = obj.getClass();
            Method method = ReflectTool.getClassMethod(methodName, c, null);
            method.setAccessible(true);
            returnObj = method.invoke(obj, null);
        } catch (Exception e) {
            log.error("invoke error:" + obj.getClass() + "." + methodName);
        }
        return returnObj;
    }

    /**
     * 递归获取类以及超类的方法
     *
     * @param methodName
     * @param clazz
     * @return
     */
    public static Method getClassMethod(String methodName, Class clazz, Class[] classes) {
        Method method = null;
        if (!clazz.equals(Object.class)) {
            try {
                method = clazz.getDeclaredMethod(methodName, classes);

            } catch (Exception e) {
                method = getClassMethod(methodName, clazz.getSuperclass(), classes);
            }
        }
        return method;

    }

    /**
     * 设置对象中的属性值
     *
     * @param obj
     * @param attName
     * @param attValue
     */
    public static void setAttribute(Object obj, String attName, Object attValue) {
        Field field = null;
        try {
            // String dateType[]={"DATE","DATETIME","DAY"};
            field = getAttribute(obj, attName);
            // obj.getClass().getDeclaredField(attName);
            field.setAccessible(true);// 强制访问private变量
            if (attValue == null) {
                if (field.getType().equals(int.class) || field.getType().equals(double.class)
                        || field.getType().equals(long.class)) {
                    field.set(obj, 0);
                } else {
                    field.set(obj, attValue);
                }

            }

			/*
             * else if(StringTool.isInArray(attName.toUpperCase(), dateType)){
			 * Date date=DateTool.convertToDate(attValue);
			 * if(field.getType().equals(Date.class)){ field.set(obj, date); }
			 * else{ field.set(obj, attValue); } }
			 */
            else if (attValue.getClass().equals(Date.class)) {
                if (field.getType().equals(Date.class)) {
                    field.set(obj, attValue);
                } else if (field.getType().equals(Timestamp.class)) {
                    field.set(obj, new Date(((Timestamp) attValue).getTime()));
                }
            } else if (attValue.getClass().equals(Timestamp.class)) {
                if (field.getType().equals(Date.class)) {
                    field.set(obj, new Date(((Timestamp) attValue).getTime()));
                } else {
                    field.set(obj, attValue);
                }

            }
            // 对于BigDecimal特殊处理
            else if (attValue.getClass().equals(BigDecimal.class)) {
                if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                    field.set(obj, ((BigDecimal) attValue).intValue());
                } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                    field.set(obj, ((BigDecimal) attValue).longValue());
                } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                    field.set(obj, ((BigDecimal) attValue).doubleValue());
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, ((BigDecimal) attValue).toString());
                } else if (field.getType().equals(BigDecimal.class)) {
                    field.set(obj, attValue);
                }
            }
            // 对于BigDecimal特殊处理
            else if (attValue.getClass().equals(Long.class)) {
                if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                    field.set(obj, ((Long) attValue).intValue());
                } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                    field.set(obj, ((Long) attValue).longValue());
                } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                    field.set(obj, ((Long) attValue).doubleValue());
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, ((Long) attValue).toString());
                }
            }
            // 对于BigDecimal特殊处理
            else if (attValue.getClass().equals(Double.class)) {
                if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                    field.set(obj, ((Double) attValue).intValue());
                } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                    field.set(obj, ((Double) attValue).longValue());
                } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                    field.set(obj, ((Double) attValue).doubleValue());
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, ((Double) attValue).toString());
                }
            }
            // 对于BigDecimal特殊处理
            else if (attValue.getClass().equals(Integer.class)) {
                if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                    field.set(obj, ((Integer) attValue).intValue());
                } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                    field.set(obj, ((Integer) attValue).longValue());
                } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                    field.set(obj, ((Integer) attValue).doubleValue());
                } else if (field.getType().equals(String.class)) {
                    field.set(obj, ((Integer) attValue).toString());
                }
            } else {
                field.setAccessible(true);// 强制访问private变量
                field.set(obj, attValue);

            }
        } catch (Exception e) {
            log.info("忽略{}的\"{}\"",obj.getClass().getSimpleName(), attName);
            // LogTool.debug(ReflectTool.class,
            // "field:"+field.getType()+"-"+attValue.getClass());
//            e.printStackTrace();
        }

    }

    /**
     * 获取对象的属性
     *
     * @param obj
     * @param attName
     * @return
     */
    public static Field getAttribute(Object obj, String attName) {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(attName);
        } catch (Exception e) {
            log.debug(obj.getClass().getName() + " no such field:" + attName);
            try {
                field = obj.getClass().getSuperclass().getDeclaredField(attName);
            } catch (Exception e2) {
                log.warn(obj.getClass().getSuperclass().getName() + "no such field:" + attName);
            }

        }
        return field;

    }

    /**
     * 获取对象属性值
     *
     * @param obj
     * @param attName
     * @return
     */
    public static Object getAttributeValue(Object obj, String attName) {
        Field field = null;
        Object value = null;
        try {
            field = obj.getClass().getDeclaredField(attName);
            field.setAccessible(true);// 强制访问private变量
            value = field.get(obj);
        } catch (Exception e) {
            log.debug(obj.getClass().getName() + " no such field:" + attName);
            try {
                field = obj.getClass().getSuperclass().getDeclaredField(attName);
                field.setAccessible(true);// 强制访问private变量
                value = field.get(obj);
            } catch (Exception e2) {
                log.warn(obj.getClass().getSuperclass().getName() + "no such field:" + attName);
            }

        }
        return value;

    }

    /**
     * 获取类的所有属性(含其直接类型)
     *
     * @param clazz
     */
    public static Field[] getAllField(Class clazz) {
        List<Field> allFields = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();

        Field[] superFields = clazz.getSuperclass().getDeclaredFields();

        // 先添加子类属性
        allFields.addAll(ListTool.arrayToList(fields));

        // 循环父类中的属性
        boolean isRepeat = false;
        for (Field f : superFields) {
            isRepeat = false;
            // 循环子类中的属性
            for (Field sf : fields) {
                if (sf.getName().equals(f.getName())) {
                    isRepeat = true;
                    break;
                }

            }
            // 不重复属性
            if (isRepeat == false) {
                if (!f.getName().equals("serialVersionUID") && !f.getName().equals("log")) {
                    allFields.add(f);
                }
            }

        }

        Field[] fieldArray = allFields.toArray(new Field[allFields.size()]);
        return fieldArray;

    }

    /**
     * 获取类的所有简单属性(不包含自属性的类型)
     *
     * @param clazz
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Field[] getAllSimpleField(Class clazz) {
        List<Field> allFields = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        // 先添加子类属性
        for (Field f : fields) {
            if (ListTool.isInList(f.getType().getSimpleName(), ReflectTool.exampleTypes)) {
                if (!f.getName().equals("serialVersionUID")) {
                    allFields.add(f);
                }
            }
        }

        Field[] superFields = clazz.getSuperclass().getDeclaredFields();

        // 循环父类中的属性
        boolean isRepeat = false;
        for (Field f : superFields) {
            isRepeat = false;
            // 循环子类中的属性
            for (Field sf : fields) {
                if (sf.getName().equals(f.getName())) {
                    isRepeat = true;
                    break;
                }

            }
            // 不重复属性
            if (isRepeat == false) {

                if (ListTool.isInList(f.getType().getSimpleName(), ReflectTool.exampleTypes)) {
                    if (!f.getName().equals("serialVersionUID")) {
                        allFields.add(f);
                    }
                }

            }

        }

        Field[] fieldArray = allFields.toArray(new Field[allFields.size()]);
        return fieldArray;

    }

    public static void main(String[] args) {
        Field[] fields = ReflectTool.getAllSimpleField(险种信息.class);
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }
}
