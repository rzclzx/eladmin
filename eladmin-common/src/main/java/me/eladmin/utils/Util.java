package me.eladmin.utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    /**
     * 获取泛型object的属性
     * obj 传入的对象
     * key 属性值
     * @return T
     */
    public static <T> T getProperty(T obj, String key) {
        Field f = null;
        try {
            f = obj.getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        T val = null;
        try {
            val = (T)f.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return val;
    }

    /**
     * 通过pid组装list成为tree
     * pid：父级id
     * pidkey：承载父级id的key
     * childkey：承载children的key
     * allList：所有数据列表
     * @return List<T>
     */
    // List<T> 前面的 <T> 为允许 非泛型的实体传入和返回
    // 有此标识， allList 可以为List<Menu>，并且List<Menu> 也可以承接getTree的泛型返回
    public static <T> List<T> getTree(T pid, String pidKey, String childKey, List<T> allList) {

        // stream() 用法相当于js filter 和map 方法
        List<T> list = allList.stream()
                .filter(item -> Util.getProperty(item, "pid") == pid)
                .collect(Collectors.toList());
        list.forEach(item -> {
            T id = Util.getProperty(item, "id");
            try {
                // FieldUtils.writeDeclaredField方法用于设置泛型object对象的属性
                FieldUtils.writeDeclaredField(item, childKey, getTree(id, "pid", "children", allList), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return list;
    }
}