package org.fog.offload;

import org.apache.commons.math3.util.Pair;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.fog.entities.FogDevice;

import static org.fog.examples.DataPlacement.*;

public class DeviceState {

    private final FogDevice device;

    private final StorageState storageState;

    public DeviceState(FogDevice device) {
        this.device = device;

        long totalStorage = getTotalStorage(device);

        Pair<Float, Float> threshold = getThreshold(device);
        float minThreshold = threshold.getFirst();
        float maxThreshold = threshold.getSecond();

        this.storageState = new StorageState(this, totalStorage, minThreshold, maxThreshold);
    }

    private Pair<Float, Float> getThreshold(FogDevice device) {
        String name = device.getName();

        if (name.contains("RPOP")) {
            return new Pair<>(RPOP_Storage_Min_Threshold, RPOP_Storage_Max_Threshold);
        } else if (name.contains("LPOP")) {
            return new Pair<>(LPOP_Storage_Min_Threshold, LPOP_Storage_Max_Threshold);
        } else if (name.contains("HGW")) {
            return new Pair<>(HGW_Storage_Min_Threshold, HGW_Storage_Max_Threshold);
        } else if (name.contains("DC")) {
            return new Pair<>(DC_Storage_Min_Threshold, DC_Storage_Max_Threshold);
        }

        return new Pair<>(-1f, -1f);
    }

    private long getTotalStorage(FogDevice device) {
        VmAllocationPolicy policy = device.getVmAllocationPolicy();

        long total = 0;
        for (Host host : policy.getHostList()) {
            for (Vm vm : host.getVmList()) {
                total += vm.getSize();
            }
        }
        return total;
    }

    public FogDevice getDevice() {
        return device;
    }

    public StorageState getStorageState() {
        return storageState;
    }
}