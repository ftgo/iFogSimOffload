package org.fog.offload;

import org.cloudbus.cloudsim.core.SimEntity;
import org.fog.entities.FogDevice;
import org.fog.entities.Tuple;
import org.fog.examples.DataPlacement;

import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.fog.offload.Bits.randomBits;

public class OffloadAllocation {
    private static final int NAME_PREFIX_LENGTH = "Temp".length();

    private static final transient Object LOCK = new Object();

    private static OffloadAllocation instance;

    private final Map<String, DeviceState> deviceMap;

    private OffloadAllocation() {
        if (DataPlacement.fogDevices == null || DataPlacement.fogDevices.isEmpty())
            throw new IllegalStateException("DataPlacement not initialized.");

        this.deviceMap = getDeviceMap();
    }

    private Map<String, DeviceState> getDeviceMap() {

        Map<String, DeviceState> map = DataPlacement.fogDevices.stream().collect(toMap(SimEntity::getName, DeviceState::new));

        StorageHandler handler = new StorageHandler();

        for (DeviceState state : map.values()) {
            StorageState storage = state.getStorageState();
            storage.add(handler);
        }

        return map;
    }

    public static void reset() {
        synchronized (LOCK) {
            instance = null;
        }
    }

    public static OffloadAllocation instance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new OffloadAllocation();
            }
        }
        return instance;
    }

    public int getEmplacementNodeId(Tuple tuple) {
        String name = getName(tuple);

        DeviceState state = this.deviceMap.get(name);

        Bits bits = randomBits(name);

        StorageState storage = state.getStorageState();
        if (storage.save(tuple, bits)) {
            FogDevice device = state.getDevice();

            Log.write(format("getEmplacementNodeId{tuple=%s, id=%s}", tuple, device.getId()));

            return device.getId();
        }

        Log.write(format("getEmplacementNodeId{tuple=%s, id=%s}", tuple, -1));

        return -1;
    }

    private String getName(Tuple tuple) {
        return tuple.getTupleType().substring(NAME_PREFIX_LENGTH);
    }
}
