package com.nvh.motus.service;

public class Chrono

{
    private long dx;
    private boolean on;

    public Chrono(long dx, boolean on) {
        this.dx = dx;
        this.on = on;
    }

    public void begin() {
        on = true;
        dx = System.currentTimeMillis();
    }

    public long get() {
        if (!on) return dx;
        long now = System.currentTimeMillis();

        return now - dx;
    }
}
