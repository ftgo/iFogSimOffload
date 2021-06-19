package org.Results;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.fog.examples.DataPlacement;
import org.fog.gui.lpFileConstuction.LatencyStats;


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
	
	public static void saveLatencyTimes(int dataConsPerDataProd, String storageMode, int nb_z, double write, double read,
			double overall, long readCount, long writeCount) throws IOException {
		
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
			fw.write("Write latency: " + String.format("%.0f", write) + "\n");
			fw.write("Read latency: " + String.format("%.0f", read) + "\n");
			fw.write("Overall latency: " + String.format("%.0f", overall) + "\n");
			fw.write("Read Count: " + readCount + "\n");
			fw.write("Write Count: " + writeCount + "\n");
			fw.write("Average Write latency: " + String.format("%.0f", write / writeCount) + "\n");
			fw.write("Average Read latency: " + String.format("%.0f", read / readCount) + "\n");
			fw.write("Average Overall latency: " + String.format("%.0f", overall / (writeCount + readCount)) + "\n");
			fw.write("----------------------------------------------------------------------------------\n");
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
