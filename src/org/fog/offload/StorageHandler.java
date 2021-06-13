package org.fog.offload;

import org.fog.entities.FogDevice;
import org.fog.entities.Tuple;

import static java.lang.String.format;

public class StorageHandler implements Listener<StorageEvent> {
    @Override
    public void update(StorageEvent event) {
        StorageEvent.Status status = event.getStatus();
        switch (status) {
            case OK:
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
        StorageState state = event.getStorageState();

        Log.write(format("keep{event=%s, state=%s}", event, state));
        state.setOffloading(false);
    }

    private void offload(StorageEvent event) {
        StorageState state = event.getStorageState();
        DeviceState deviceState = state.getDeviceState();
        FogDevice device = deviceState.getDevice();

        state.setOffloading(true);

        while (state.isOffloading()) {
            Tuple tuple = state.getNonCriticalTuple();
            if (tuple == null) {
                if (state.getTupleCount() > 0)
                    Log.write(format("offload{event=%s, state=%s, CRITICAL_INTERRUPT}", event, state));

                state.setOffloading(false);
                break;
            }

            Log.write(format("offload{event=%s, state=%s}", event, state));

            Bits bits = OffloadAllocation.instance().getBits(tuple);

            state.delete(tuple, bits);
            device.replyUp(tuple);
        }
    }
}