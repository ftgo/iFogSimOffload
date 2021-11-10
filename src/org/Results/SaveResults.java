package org.Results;

import org.fog.examples.DataPlacement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 
 * @author islam
 * this class contains methods to save various simulations results as latency time, cost, energy and so on
 * 
 *
 */


public class SaveResults {
	
	public SaveResults() {
		
	}
	
	public static void saveLatencyTimes(int dataConsPerDataProd, String storageMode, int nb_z, double read, double write,
										double reply, double overall, long readCount, long writeCount, long replyCount, long overallCount) throws IOException {
		
		System.out.println("Saving Latency time information");
		FileWriter fichier = new FileWriter("Stats/latencyStats" + DataPlacement.nb_HGW+"_"+DataPlacement.nb_DataCons_By_DataProd, true);
		try {
			BufferedWriter fw = new BufferedWriter(fichier);

			fw.write("offload: " + DataPlacement.offload + "\n");
			fw.write("HGW_Storage_Min_Threshold: " + DataPlacement.HGW_Storage_Min_Threshold + "\n");
			fw.write("HGW_Storage_Max_Threshold: " + DataPlacement.HGW_Storage_Max_Threshold + "\n");
			fw.write("HGW_Storage_Compression: " + DataPlacement.HGW_Storage_Compression + "\n");
			fw.write("HGW_Compression_Selection: " + DataPlacement.HGW_Compression_Selection + "\n");
			fw.write("HGW_Critical_Selection: " + DataPlacement.HGW_Critical_Selection + "\n");



			fw.write("DataCons/DataProd: " + dataConsPerDataProd + "\n");
			fw.write("StorageMode: " + storageMode + "\n");
			if (nb_z != -1) {
				fw.write("nb_zone: " + nb_z + "\n");
			}
			fw.write("Read latency: " + String.format("%.0f", read) + "\n");
			fw.write("Write latency: " + String.format("%.0f", write) + "\n");
			fw.write("Reply latency: " + String.format("%.0f", reply) + "\n");
			fw.write("Write+Reply latency: " + String.format("%.0f", write+reply) + "\n");
			fw.write("Overall latency: " + String.format("%.0f", overall) + "\n");
			fw.write("Read Count: " + readCount + "\n");
			fw.write("Write Count: " + writeCount + "\n");
			fw.write("Reply Count: " + replyCount + "\n");
			fw.write("Write+Reply Count: " + (writeCount+replyCount) + "\n");
			fw.write("Overall Count: " + overallCount + "\n");
			fw.write("Average Read latency: " + String.format("%.0f", read / readCount) + "\n");
			fw.write("Average Write latency: " + String.format("%.0f", write / writeCount) + "\n");
			fw.write("Average Reply latency: " + String.format("%.0f", reply / replyCount) + "\n");
			fw.write("Average Write+Reply latency: " + String.format("%.0f", (write+reply) / (writeCount+replyCount)) + "\n");
			fw.write("Average Overall latency: " + String.format("%.0f", overall / overallCount) + "\n");
			fw.write("----------------------------------------------------------------------------------\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
