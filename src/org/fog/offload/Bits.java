package org.fog.offload;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import static org.fog.examples.DataPlacement.*;
import static org.fog.offload.Bits.Tag.COMPRESSION;
import static org.fog.offload.Bits.Tag.CRITICAL;

public class Bits {
    public enum Tag {COMPRESSION, CRITICAL, TOUCHED}

    private boolean[] tags = new boolean[Tag.values().length];

    public void toggle(Tag tag) {
        set(tag, !this.tags[tag.ordinal()]);
    }

    public void set(Tag tag, boolean state) {
        this.tags[tag.ordinal()] = state;
    }

    public boolean get(Tag tag) {
        return this.tags[tag.ordinal()];
    }

    @Override
    public String toString() {
        String tagStr = String.join(",", Arrays.stream(Tag.values()).filter(this::get).map(Object::toString).collect(Collectors.toList()));


        return "Bits{" +
                "tags=" + tagStr
                + '}';
    }

    public static void randomTags(Bits bits, String name) {
        Random random = new Random();
        if (random.nextDouble() <= getCriticalThreshold(name)) {
            bits.toggle(CRITICAL);
        }

        if (random.nextDouble() <= getCompressionThreshold(name)) {
            bits.toggle(COMPRESSION);
        }
    }

    private static float getCriticalThreshold(String name) {
        DeviceType type = DeviceType.of(name);
        switch (type) {
            case DC:
                return DC_Critical_Selection;
            case RPOP:
                return RPOP_Critical_Selection;
            case LPOP:
                return LPOP_Critical_Selection;
            case HGW:
                return HGW_Critical_Selection;
            default:
                return 0;
        }
    }

    private static float getCompressionThreshold(String name) {
        DeviceType type = DeviceType.of(name);
        switch (type) {
            case DC:
                return DC_Compression_Selection;
            case RPOP:
                return RPOP_Compression_Selection;
            case LPOP:
                return LPOP_Compression_Selection;
            case HGW:
                return HGW_Compression_Selection;
            default:
                return 0;
        }
    }
}
