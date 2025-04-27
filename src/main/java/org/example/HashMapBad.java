package org.example;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HashMapBad {

    private static int countTreeNodes(Object root) throws Exception {
        if (root == null) return 0;

        Class<?> treeNodeClass = root.getClass();
        Field leftField = treeNodeClass.getDeclaredField("left");
        Field rightField = treeNodeClass.getDeclaredField("right");
        leftField.setAccessible(true);
        rightField.setAccessible(true);

        Object left = leftField.get(root);
        Object right = rightField.get(root);

        return 1 + countTreeNodes(left) + countTreeNodes(right);
    }

    protected static void logMapState(Map<?, ?> map) throws Exception {
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);

        System.out.println("== После вставки ==");
        System.out.println("Размер всей мапы: " + map.size());

        for (int i = 0; i < table.length; i++) {
            Object node = table[i];
            if (node != null) {
                System.out.println("  Бакет #" + i);

                Class<?> nodeClass = node.getClass();
                int count = 0;

                if (nodeClass.getSimpleName().equals("Node")) {
                    Field nextField = nodeClass.getDeclaredField("next");
                    nextField.setAccessible(true);

                    Object current = node;
                    while (current != null) {
                        count++;
                        current = nextField.get(current);
                    }
                } else if (nodeClass.getSimpleName().equals("TreeNode")) {
                    count = countTreeNodes(node);
                } else {
                    System.out.println("Неизвестный тип узла: " + nodeClass.getName());
                }

                System.out.println("    Количество элементов в бакете: " + count);
                System.out.println("    Тип ноды: " + nodeClass.getSimpleName());
            }
        }
    }
}
