package me.eladmin.utils;


import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<Object> getTreeChildren(Long pid, List<T> allList, String key) {
        List<Object> list = allList.stream()
                .filter(item -> {
                    Field f = null;
                    try {
                        f = item.getClass().getDeclaredField(key);
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
            Field f1 = null;
            Field f2 = null;
            try {
                f1 = item.getClass().getDeclaredField("children");
                f2 = item.getClass().getDeclaredField("id");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            f1.setAccessible(true);
            f2.setAccessible(true);
            try {
                Long id = (Long)f2.get(item);
                f1.set(item, getTreeChildren(id, allList, "pid"));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return list;
    }
}
