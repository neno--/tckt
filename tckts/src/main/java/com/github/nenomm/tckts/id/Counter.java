package com.github.nenomm.tckts.id;

// This class is NOT thread safe.
public class Counter {
    private long count;
    private long lastActivity;

    public Counter() {
        count = -1;
    }

    public void inc() {
        count++;
        lastActivity = System.currentTimeMillis();
    }

    public long getCount() {
        if (count == -1) {
            throw new IllegalStateException("counter must be incremented");
        }

        return count;
    }

    public long getLastActivity() {
        if (count == -1) {
            throw new IllegalStateException("counter must be incremented");
        }

        return lastActivity;
    }
}
