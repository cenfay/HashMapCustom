package org.example;

import java.util.HashMap;
import java.util.Map;

import static org.example.HashMapBad.logMapState;


public class Main {
    public static void main(String[] args) throws Exception {
        Map<Object, String> map = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            final int id = i;
            Object badKey = new Object() {
                @Override
                public int hashCode() {
                    return 1;
                }

                @Override
                public boolean equals(Object obj) {
                    return this == obj;
                }

                @Override
                public String toString() {
                    return "BadKey{" + id + '}';
                }
            };
            map.put(badKey, "Value" + i);
            logMapState(map);
        }
    }
}
