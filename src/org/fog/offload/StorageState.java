package org.fog.offload;

import org.fog.entities.Tuple;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.Float.compare;
import static org.fog.examples.DataPlacement.*;
import static org.fog.offload.Bits.Tag.*;

public class StorageState implements Subject<StorageEvent> {
    private final Set<Listener<StorageEvent>> listeners;
    private Set<Tuple> tuples;

    private final DeviceState deviceState;
    private final long total;
    private final float minThreshold;
    private final float maxThreshold;
    private final float lower;
    private final float upper;

    private long current;

    private boolean offloading;

    public StorageState(DeviceState deviceState, long total) {
        this(deviceState, total, 20.0f, 99.9f);
    }

    public StorageState(DeviceState deviceState, long total, float minThreshold, float maxThreshold) {
        if (compare(minThreshold, 0.1f) < 0 || compare(minThreshold, 99.9f) > 0)
            throw new IllegalArgumentException("Minimum threshold percentage should be between 0.1 and 99.9.");

        if (compare(maxThreshold, 0.1f) < 0 || compare(maxThreshold, 99.9f) > 0)
            throw new IllegalArgumentException("Maximum threshold percentage should be between 0.1 and 99.9.");

        if (minThreshold >= maxThreshold)
            throw new IllegalArgumentException("Minimum threshold should be less than maximum threshold.");


        this.deviceState = deviceState;
        this.total = total;

        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;

        this.lower = this.total * (this.minThreshold) / 100;
        this.upper = this.total * this.maxThreshold / 100;

        this.listeners = new LinkedHashSet<>();
        this.tuples = new HashSet<>();
    }

    @Override
    public boolean addListener(Listener<StorageEvent> listener) {
        return this.listeners.add(listener);
    }

    @Override
    public boolean removeListener(Listener<StorageEvent> listener) {
        return this.listeners.add(listener);
    }

    @Override
    public void notifyEvent(StorageEvent event) {
        notifyEvent(event, this.listeners);
    }

    public boolean save(Tuple tuple, Bits bits) {
        if (this.offloading)
            return false;

        long size = getSize(tuple, bits);
        long expected = this.current + size;

        if (compare(expected, this.total) > 0) {
            notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.FAILED));
            return false;
        }

        if (compare(expected, this.upper) > 0) {
            notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.HIT));
        }

        this.current += size;

        notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.SAVE, StorageEvent.Status.OK));

        this.tuples.add(tuple);

        return true;
    }

    public boolean delete(Tuple tuple, Bits bits) {
        long size = getSize(tuple, bits);

        long expected = this.current - size;

        if (compare(expected, 0) < 0) {
            notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.FAILED));
            return false;
        }

        if (compare(expected, this.lower) < 0) {
            notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.HIT));
        }

        this.current -= size;

        notifyEvent(new StorageEvent(this, tuple, bits, StorageEvent.Type.DELETE, StorageEvent.Status.OK));

        this.tuples.remove(tuple);

        return true;
    }

    private long getSize(Tuple tuple, Bits bits) {
        long size = tuple.getCloudletFileSize();

        if (!bits.get(TOUCHED) && bits.get(COMPRESSION)) {
            float compression = getCompression(tuple, bits);

            size *= (100 - compression) / 100;

            // TODO offload
            tuple.setCloudletFileSize(size);
        }

        bits.set(TOUCHED, true);

        return size;
    }

    private float getCompression(Tuple tuple, Bits bits) {
        DeviceType type = DeviceType.of(this.deviceState.getDevice().getName());
        switch (type) {
            case DC:
                return DC_Storage_Compression;
            case RPOP:
                return RPOP_Storage_Compression;
            case LPOP:
                return LPOP_Storage_Compression;
            case HGW:
                return HGW_Storage_Compression;
            default:
                return 0;
        }
    }


    public long getCurrent() {
        return this.current;
    }

    public long getTotal() {
        return this.total;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public Set<Listener<StorageEvent>> getListeners() {
        return listeners;
    }

    public float getMinThreshold() {
        return minThreshold;
    }

    public float getMaxThreshold() {
        return maxThreshold;
    }

    public float getLower() {
        return lower;
    }

    public float getUpper() {
        return upper;
    }

    public void setOffloading(boolean offloading) {
        this.offloading = offloading;
    }

    public boolean isOffloading() {
        return offloading;
    }

    public Tuple getTuple() {
        return this.tuples.stream().findFirst().orElse(null);
    }

    public Tuple getNonCriticalTuple() {
        OffloadAllocation allocation = OffloadAllocation.instance();
        Tuple tuple = this.tuples.stream().filter(n -> !allocation.getBits(n).get(CRITICAL)).findFirst().orElse(null);
        return tuple;
    }

    public int getTupleCount() {
        return this.tuples.size();
    }

    @Override
    public String toString() {
        return "StorageState{" +
                "total=" + total +
                ", current=" + current +
                ", lower=" + lower +
                ", upper=" + upper +
                ", minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                ", offloading=" + offloading +
                '}';
    }
}