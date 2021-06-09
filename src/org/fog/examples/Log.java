package org.fog.examples;

import java.io.*;

import static org.fog.examples.DataPlacement.nb_DataCons_By_DataProd;


public class Log {
    private static boolean append = false;

    private static boolean append() {
        if (!append) {
            append = true;
            return false;
        }
        return true;
    }

    public static void writeSolvingTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/solvingtime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeProblemFormulationTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/problemFormulationTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeDataAllocationStats(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/dataAllocationStats" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeDataAllocationStatsExternZoneCons(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/dataAllocationStatsExternZoneCons" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void writeSimulationTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/SimulationTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeInfraCreationTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/InfraCreationTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeGraphPonderationTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/graphPonderationTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writePartitionDevicesInformation(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/graphPartitionInformation" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writePartitionTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/partitionTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeShortPathTime(int nb_HGW, String log) throws IOException {
        File stats = new File("Stats");
        if (!stats.exists()) stats.mkdir();

        FileWriter lpFile = new FileWriter("Stats/shortPathTime" + nb_HGW + "_" + nb_DataCons_By_DataProd, append());
        try {
            BufferedWriter fw = new BufferedWriter(lpFile);
            fw.write(log + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
