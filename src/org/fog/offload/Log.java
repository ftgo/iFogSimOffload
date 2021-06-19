package org.fog.offload;

import org.fog.examples.DataPlacement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.fog.examples.DataPlacement.nb_DataCons_By_DataProd;
import static org.fog.examples.DataPlacement.nb_HGW;


public class Log {
    public static final DateFormat DATE_TIME_INSTANCE = SimpleDateFormat.getDateTimeInstance();

    private static Set<String> appendSet = new HashSet<>();

    private static boolean append(String token) {
        if (!appendSet.contains(token)) {
            appendSet.add(token);
            return false;
        }
        return true;
    }

    public static void write(String msg) {
        java.io.File dir = new File("offload");
        dir.mkdir();

        String tag = String.format("%.1f_%.1f_%.1f_%.1f_%.1f_%s_%b", DataPlacement.HGW_Storage_Min_Threshold, DataPlacement.HGW_Storage_Max_Threshold, DataPlacement.HGW_Storage_Compression, DataPlacement.HGW_Compression_Selection, DataPlacement.HGW_Critical_Selection, DataPlacement.storageMode, DataPlacement.offload);

        String fileName = "Log/offload_" + tag + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append(tag)))) {
            String now = DATE_TIME_INSTANCE.format(new Date());
            writer.write(String.format("%s\t%s\n", now, msg));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
