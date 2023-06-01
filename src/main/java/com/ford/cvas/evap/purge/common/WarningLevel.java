package com.ford.cvas.evap.purge.common;

public enum WarningLevel {
    EARLY_WARNING(2),
    NEARING_EARLY_WARNING(1),
    NO_WARNING(0);


    public int getWarningLevel() {
        return warningLevel;
    }

    private int warningLevel;

    WarningLevel(int warningLevel) {
        this.warningLevel = warningLevel;
    }

}

