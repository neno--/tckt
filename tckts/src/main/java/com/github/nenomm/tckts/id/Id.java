package com.github.nenomm.tckts.id;

public class Id {
    private long id;
    private long timeCreated;

    public Id(long id, long timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public long getId() {
        return id;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
