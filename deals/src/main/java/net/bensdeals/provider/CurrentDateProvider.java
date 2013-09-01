package net.bensdeals.provider;

import java.util.Date;

import net.bensdeals.listener.Provider;

public class CurrentDateProvider implements Provider<Date> {
    @Override
    public Date get() {
        return new Date();
    }
}
