package org.fog.gui.lpFileConstuction;

public class LatencyStats {
    public static double Overall_read_latency = 0;
    public static double Overall_write_latency = 0;
    public static double Overall_reply_latency = 0;
    public static double Overall_latency = 0;
    public static long Overall_read_count = 0;
    public static long Overall_write_count = 0;
    public static long Overall_reply_count = 0;
    public static long Overall_count = 0;

    public static double get_Overall_Latency() {
        return Overall_latency;
    }

    public static void set_Overall_Latency(double latency) {
        Overall_latency = latency;
    }

    public static void reset_Overall_Latency() {
        Overall_latency = 0;
    }

    public static double get_Overall_read_Latency() {
        return Overall_read_latency;
    }

    public static void set_Overall_read_Latency(double latency) {
        Overall_read_latency = latency;
    }

    public static void reset_Overall_read_Latency() {
        Overall_read_latency = 0;
    }

    public static double get_Overall_write_Latency() {
        return Overall_write_latency;
    }

    public static void set_Overall_write_Latency(double latency) {
        Overall_write_latency = latency;
    }

    public static void reset_Overall_write_Latency() {
        Overall_write_latency = 0;
    }

    public static double get_Overall_reply_Latency() {
        return Overall_reply_latency;
    }

    public static void set_Overall_reply_Latency(double latency) {
        Overall_reply_latency = latency;
    }

    public static void reset_Overall_reply_Latency() {
        Overall_reply_latency = 0;
    }

    public static long get_Overall_write_count() {
        return Overall_write_count;
    }

    public static void set_Overall_write_count(long count) {
        Overall_write_count = count;
    }

    public static void reset_Overall_write_count() {
        Overall_write_count = 0;
    }

    public static long get_Overall_reply_count() {
        return Overall_reply_count;
    }

    public static void set_Overall_reply_count(long count) {
        Overall_reply_count = count;
    }

    public static void reset_Overall_reply_count() {
        Overall_reply_count = 0;
    }

    public static long get_Overall_read_count() {
        return Overall_read_count;
    }

    public static void set_Overall_read_count(long count) {
        Overall_read_count = count;
    }

    public static void reset_Overall_read_count() {
        Overall_read_count = 0;
    }

    public static long get_Overall_count() {
        return Overall_count;
    }

    public static void set_Overall_count(long count) {
        Overall_count = count;
    }

    public static void reset_Overall_count() {
        Overall_count = 0;
    }

}
