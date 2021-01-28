package me.eladmin.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    /**
     * 获取泛型object的属性
     * obj 传入的对象
     * key 属性key
     */
    public static <V, T> V getProperty(T obj, String key) {
        Field f = null;
        try {
            f = obj.getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        V val = null;
        try {
            val = (V) f.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * // 记
     * <T, V>代表 入参或函数用到的不确定类型，一般标准如下
     * 1、E：元素（Element），多用于java集合框架
     * 2、K：关键字（Key）
     * 3、N：数字（Number）
     * 4、T：对象实体
     * 5、V：值（Value）
     *
     * stream() 用法相当于js filter 和map 方法
     *
     * FieldUtils.writeDeclaredField方法用于设置泛型object对象的属性
     *
     * 上面getProperty方法通过反射 Field获取不确定对象属性值
     */

    /**
     * 通过pid组装list成为tree
     * pid 父级id
     * pidKey 承载父级id的key
     * childKey 承载children的key
     * allList 所有数据列表
     */
    public static <T, V> List<T> getTree(V pid, String pidKey, String childKey, List<T> allList) {
        List<T> list = allList.stream()
                .filter(item -> Util.getProperty(item, pidKey) == pid)
                .collect(Collectors.toList());
        list.forEach(item -> {
            V id = Util.getProperty(item, "id");
            try {
                FieldUtils.writeDeclaredField(item, childKey, getTree(id, pidKey, childKey, allList), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return list;
    }
}
