package org.fog.cplex;

import org.fog.entities.FogDevice;
import org.fog.examples.DataPlacement;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class DataAllocationOffload {
    public static Map<String, FogDevice> deviceMap = new HashMap<String, FogDevice>();

    public void setDataPlacementMap() {
        deviceMap = DataPlacement.fogDevices.stream().collect(toMap(FogDevice::getName, n -> n));
    }

    public static int getEmplacementNodeId(String tupleType) {
        FogDevice fogDevice = deviceMap.get(tupleType);
        if (Math.random() < 0.5) {
            return fogDevice.getId();
        } else {
            return fogDevice.getParentId();
        }
    }
}
