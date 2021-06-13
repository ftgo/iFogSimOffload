package org.fog.offload;

import org.fog.entities.Tuple;

public class Tuples {
    public static String toString(Tuple tuple) {
        return String.format("Tuple{id=%d, src=%s, dst=%s}",
                tuple.getCloudletId(), tuple.getSrcModuleName(), tuple.getDestModuleName());
    }
}
