package net.bensdeals.utils;

import net.bensdeals.provider.CurrentDateProvider;

import java.util.Date;

public class TestCurrentDateProvider extends CurrentDateProvider {
    private Date now = new Date();

    @Override
    public Date get() {
        return this.now;
    }

    public void set(Date now) {
        this.now = now;
    }
}
