package org.fog.gui.lpFileConstuction;

public class LatencyStats {
	public static double Overall_read_latency=0;
	public static double Overall_write_latency=0;
	public static double Overall_latency=0;
	public static long Overall_read_count =0;
	public static long Overall_write_count =0;

	public static void add_Overall_write_count(long count){
		Overall_write_count = count;
	}
	public static void add_Overall_read_count(long count){
		Overall_read_count = count;
	}
	public static void add_Overall_Letency(double latency){
		Overall_latency= latency;
	}
	public static void add_Overall_read_Letency(double latency){
		Overall_read_latency= latency;
	}
	public static void add_Overall_write_Letency(double latency){
		Overall_write_latency= latency;
	}
	
	public static double getOverall_Latency(){
		return Overall_latency;
	}
	
	public static double getOverall_read_Latency(){
		return Overall_read_latency;
	}
	
	public static double getOverall_write_Latency(){
		return Overall_write_latency;
	}

	public static long getOverall_write_count(){
		return Overall_write_count;
	}
	public static long getOverall_read_count(){
		return Overall_read_count;
	}

	public static void reset_Overall_write_count(){
		Overall_write_count = 0;
	}
	public static void reset_Overall_read_count(){
		Overall_read_count = 0;
	}
	public static void reset_Overall_Letency(){
		Overall_latency= 0;
	}
	public static void reset_Overall_read_Letency(){
		Overall_read_latency= 0;
	}
	public static void reset_Overall_write_Letency(){
		Overall_write_latency= 0;
	}
	
}
