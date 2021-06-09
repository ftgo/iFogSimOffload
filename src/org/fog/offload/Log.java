package org.fog.offload;

import org.fog.examples.DataPlacement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fog.examples.DataPlacement.nb_DataCons_By_DataProd;
import static org.fog.examples.DataPlacement.nb_HGW;


public class Log {
    public static final DateFormat DATE_TIME_INSTANCE = SimpleDateFormat.getDateTimeInstance();

    private static boolean append = false;

    private static boolean append() {
        if (!append) {
            append = true;
            return false;
        }
        return true;
    }

    public static void write(String msg) {
        java.io.File dir = new File("offload");
        dir.mkdir();

        String fileName = "offload/log_" + nb_HGW + "_" + nb_DataCons_By_DataProd + "_" + DataPlacement.storageMode + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, append()))) {
            String now = DATE_TIME_INSTANCE.format(new Date());
            writer.write(String.format("%s\t%s\n", now, msg));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
