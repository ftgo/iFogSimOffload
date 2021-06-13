package org.fog.offload;

public enum DeviceType {
        DC, RPOP, LPOP, HGW;

        public static DeviceType of(String value) {
            if (value.contains(DC.toString())) {
                return DC;
            } else if (value.contains(RPOP.toString())) {
                return RPOP;
            } else if (value.contains(LPOP.toString())) {
                return LPOP;
            } else if (value.contains(HGW.toString())) {
                return HGW;
            }
            return null;
        }
    }
