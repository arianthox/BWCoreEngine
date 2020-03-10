package com.globant.brainwaves.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Stream;
import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;

public interface Packet {

    default HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        Stream.of(this.getClass().getDeclaredFields()).forEach(field -> {
            try {
                    Object value = field.get(this);
                    String fieldValue=Match(field.getType().isArray()).of(
                            Case($(is(true)),
                                    (String) Match(field.getType().getComponentType()).of(
                                            new Match.Case[]{
                                                    Case($(instanceOf(int.class.getClass())),Arrays.toString((int[])value)),
                                                    Case($(), String.valueOf(value))
                                            }
                                    )
                            ),
                            Case($(),
                                    String.valueOf(value)
                            )
                    );

                    map.put(field.getName(), fieldValue);

            } catch (IllegalAccessException e) {
                System.err.println(e.getMessage());
            }
        });

        return map;
    }

}
