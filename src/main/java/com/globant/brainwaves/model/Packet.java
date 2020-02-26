package com.globant.brainwaves.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.stream.Stream;

public interface Packet {

    default HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        Stream.of(this.getClass().getDeclaredFields()).forEach(field -> {
            try {
                Object value = field.get(this);
                map.put(field.getName(), field.getType().isArray() ? Arrays.toString((Object[]) value) : value.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return map;
    }

}
