package org.fog.offload;

import org.fog.entities.FogDevice;
import org.fog.entities.Tuple;

public class StorageHandler implements Listener<StorageEvent> {
    @Override
    public void update(StorageEvent event) {
        StorageEvent.Status status = event.getStatus();
        switch (status) {
            case OK:
                break;
            case FAILED:
                break;
            case HIT:
                hit(event);
                break;
            default:
        }
    }

    private void hit(StorageEvent event) {
        StorageEvent.Type type = event.getType();
        switch (type) {
            case SAVE:
                offload(event);
                break;
            case DELETE:
                keep(event);
                break;
            default:
        }
    }

    private void keep(StorageEvent event) {
        System.out.println("  >>> keep, tupleType=" + event.getTuple().getTupleType());
        StorageState state = event.getStorageState();
        state.setOffloading(false);
    }

    private void offload(StorageEvent event) {
        System.out.println("  >>> offload, tupleType=" + event.getTuple().getTupleType());

        Tuple tuple = event.getTuple();
        StorageState state = event.getStorageState();
        DeviceState deviceState = state.getDeviceState();
        FogDevice device = deviceState.getDevice();

        state.setOffloading(true);


        while (state.isOffloading()) {
            System.out.println("  >>> offload, state=" + state);
            state.delete(tuple);
            device.replyUp(tuple);
        }
    }
}