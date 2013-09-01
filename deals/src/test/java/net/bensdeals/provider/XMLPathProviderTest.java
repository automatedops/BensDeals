package net.bensdeals.provider;

import android.content.Context;

import net.bensdeals.R;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class XMLPathProviderTest {
    @Inject Context context;

    @Test
    public void shouldMatchString() throws Exception {
        CharSequence[] textArray = context.getResources().getTextArray(R.array.xml_titles);
        XMLPathProvider.XMLPath[] values = XMLPathProvider.XMLPath.values();
        expect(textArray.length).toEqual(values.length);
        for (int i = 0; i < values.length; i++) {
            expect(textArray[i].toString()).toEqual(values[i].getTitle());
        }
    }
}
