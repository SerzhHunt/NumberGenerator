package ru.inovus.numbergenerator.model;

public enum Region {
    TATARSTAN(116);

    private final int region;

    Region(Integer region) {
        this.region = region;
    }

    public int getRegion() {
        return region;
    }

}
