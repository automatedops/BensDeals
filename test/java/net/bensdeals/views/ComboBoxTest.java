package net.bensdeals.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.google.inject.Inject;
import net.bensdeals.R;
import net.bensdeals.provider.XMLPathProvider;
import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class ComboBoxTest {
    @Inject Context context;
    public ComboBox comboBox;

    @Before
    public void setup() throws Exception {
        comboBox = (ComboBox) LayoutInflater.from(context).inflate(R.layout.deal_pager_layout, null).findViewById(R.id.combo_box);
    }

    @Test
    public void render_shouldShowComboBox() throws Exception {
        expect(comboBox).toBeInvisible();
        comboBox.render(XMLPathProvider.XMLPath.DEALS);
        expect(comboBox).toBeVisible();
        expect(((TextView) comboBox.findViewById(R.id.page_title_view))).toHaveText("Deals");
    }
}
