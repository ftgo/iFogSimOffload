package org.fog.offload;

import java.util.Random;

import static org.fog.examples.DataPlacement.*;
import static org.fog.offload.Bits.Tag.COMPRESSION;
import static org.fog.offload.Bits.Tag.CRITICAL;

public class Bits {
    public enum Tag {COMPRESSION, CRITICAL}

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
        return "Bits{" +
                "tags="
                + String.join(",", (get(COMPRESSION) ? "COMPRESSION" : ""), (get(CRITICAL) ? "CRITICAL" : ""))
                + '}';
    }

    public static Bits randomBits(String name) {
        Bits tags = new Bits();

        Random random = new Random();
        if (random.nextDouble() <= getCriticalThreshold(name)) {
            tags.toggle(CRITICAL);
        }

        if (random.nextDouble() <= getCompressionThreshold(name)) {
            tags.toggle(COMPRESSION);
        }

        return tags;
    }

    private static float getCriticalThreshold(String name) {
        DeviceType type = DeviceType.of(name);
        switch (type) {
            case DC:
                return DC_Critical_Threshold;
            case RPOP:
                return RPOP_Critical_Threshold;
            case LPOP:
                return LPOP_Critical_Threshold;
            case HGW:
                return HGW_Critical_Threshold;
            default:
                return 0;
        }
    }

    private static float getCompressionThreshold(String name) {
        DeviceType type = DeviceType.of(name);
        switch (type) {
            case DC:
                return DC_Compression_Threshold;
            case RPOP:
                return RPOP_Compression_Threshold;
            case LPOP:
                return LPOP_Compression_Threshold;
            case HGW:
                return HGW_Compression_Threshold;
            default:
                return 0;
        }
    }
}
