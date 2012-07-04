package net.bensdeals.utils;

import com.google.inject.Provider;

import java.util.Date;

public class CurrentDateProvider implements Provider<Date> {
    @Override
    public Date get() {
        return new Date();
    }
}
