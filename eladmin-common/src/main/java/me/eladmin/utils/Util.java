package me.eladmin.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

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
                .filter(item -> {
                    // 反射获取泛型对象的属性
                    Field f = null;
                    try {
                        f = item.getClass().getDeclaredField(pidKey);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    f.setAccessible(true);
                    Boolean state = false;
                    try {
                        state = f.get(item) == pid;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return state;
                })
                .collect(Collectors.toList());
        list.forEach(item -> {
            Field f2 = null;
            try {
                f2 = item.getClass().getDeclaredField("id");
                f2.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            try {
                T id = (T)f2.get(item);
                FieldUtils.writeDeclaredField(item, childKey, getTree(id, "pid", "children", allList), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return list;
    }
}
