package org.fog.offload;

import org.cloudbus.cloudsim.core.SimEntity;
import org.fog.entities.FogDevice;
import org.fog.entities.Tuple;
import org.fog.examples.DataPlacement;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static org.fog.offload.Bits.randomTags;

public class OffloadAllocation {
    private static final int NAME_PREFIX_LENGTH = "Temp".length();

    private static final transient Object LOCK = new Object();

    private static OffloadAllocation instance;

    private final Map<String, DeviceState> deviceMap;

    private final Map<Tuple, Bits> bitsMap;

    private OffloadAllocation() {
        if (DataPlacement.fogDevices == null || DataPlacement.fogDevices.isEmpty())
            throw new IllegalStateException("DataPlacement not initialized.");

        this.deviceMap = getDeviceMap();

        this.bitsMap = new HashMap<>();
    }

    private Map<String, DeviceState> getDeviceMap() {

        Map<String, DeviceState> map = DataPlacement.fogDevices.stream().collect(toMap(SimEntity::getName, DeviceState::new));

        StorageHandler handler = new StorageHandler();

        for (DeviceState state : map.values()) {
            StorageState storage = state.getStorageState();
            storage.addListener(handler);
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
        DeviceState state = this.deviceMap.get(getName(tuple));

        Bits bits = getRandomBits(tuple);

        StorageState storage = state.getStorageState();

        int id = -1;
        if (storage.save(tuple, bits)) {
            FogDevice device = state.getDevice();

            id = device.getId();
        }

        Log.write(format("getEmplacementNodeId{tuple=%s, id=%s}", Tuples.toString(tuple), id));

        return id;
    }

    public Bits getBits(Tuple tuple) {
        return this.bitsMap.get(tuple);
    }

    private Bits getRandomBits(Tuple tuple) {
        Bits bits = this.bitsMap.get(tuple);
        if (bits == null) {
            bits = new Bits();
            randomTags(bits, getName(tuple));
            this.bitsMap.put(tuple, bits);
        }

        return bits;
    }

    private String getName(Tuple tuple) {
        return tuple.getTupleType().substring(NAME_PREFIX_LENGTH);
    }
}
