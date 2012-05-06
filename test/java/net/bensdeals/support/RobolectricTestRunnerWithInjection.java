package net.bensdeals.support;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import net.bensdeals.activity.DealsApplication;
import org.junit.runners.model.InitializationError;
import roboguice.config.AbstractAndroidModule;

import java.lang.reflect.Method;

public class RobolectricTestRunnerWithInjection extends RobolectricTestRunner {
    public RobolectricTestRunnerWithInjection(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public void beforeTest(Method method) {
        super.beforeTest(method);
    }

    @Override
    public void prepareTest(Object test) {
        DealsApplication application = (DealsApplication) Robolectric.application;
        application.setModule(new TestApplicationModule());
        application.getInjector().injectMembers(test);
    }

    public static class TestApplicationModule extends AbstractAndroidModule {
        @Override
        protected void configure() {
        }
    }
}

