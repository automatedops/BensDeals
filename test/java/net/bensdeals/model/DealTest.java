package net.bensdeals.model;

import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import net.bensdeals.utils.DateFormatter;
import net.bensdeals.utils.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static net.bensdeals.model.Deal.parseXml;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealTest {
    private List<Deal> deals;

    @Before
    public void setUp() throws Exception {
        deals = parseXml(StringUtil.responseAsStream("homepage.xml"));
    }

    @Test
    public void shouldParseXML() throws Exception {
        expect(deals.size()).toEqual(20);
    }

    @Test
    public void shouldParseAnotherXml() throws Exception {
        deals = parseXml(StringUtil.responseAsStream("feed.xml"));
        expect(deals.size()).toEqual(20);
    }

    @Test
    public void shouldParseDealFields() throws Exception {
        Deal deal = deals.get(0);
        expect(deal.getTitle()).toEqual("3-Year Subscription to Muscle &amp; Fitness $9.58 at DiscountMags");
        expect(deal.getDescription()).toEqual("Today only. DiscountMags has a 3-Year Subscription to Muscle & Fitness Magazine (36 issues) for $59.97 - $50.39 off with coupon code <B>7938</b> = <B>$9.58</b> with free shipping.  A two-year subscription is <b>$7.18</b> and a one-year subscription is <b>$3.99</b> with same code.    [<a                 rel=\"nofollow\" href=\"/link/3-year-subscription-to-muscle-amp-fitness-9-58-at-discountmags-253527/?linkid=0\" target=\"_new\">Compare</a>]");
        expect(deal.getLink()).toEqual("http://bensbargains.net/deal/3-year-subscription-to-muscle-amp-fitness-9-58-at-discountmags-253527/?referrer=rss");
        expect(deal.getDate()).toEqual(DateFormatter.stringToDate("Sat, 05 May 2012 10:42:48 -0700"));
        expect(deal.getImageUrl()).toEqual("http://cdn2.bensimages.com/img118828l.jpg");
    }

    @Test
    public void shouldUseLargeImageUrl() throws Exception {
        expect(deals.get(0).getImageUrl()).toEqual("http://cdn2.bensimages.com/img118828l.jpg");
    }

    @Test
    public void shouldRemoveIndentImage() throws Exception {
        deals = parseXml(StringUtil.responseAsStream("feed_with_indent_image.xml"));
        expect(deals.get(0).getDescription()).toEqual("Newegg has the MSI N670GTX-PM2D2GD5/OC GeForce GTX 670 2GB GDDR5 Overclocked Video Card for <B>$400</b> with free shipping. $30 Newegg Gift Card included with purchase. Check out this <a  rel=\"nofollow\" href=\"/link/msi-geforce-gtx-670-2gb-oc-video-card-400-at-newegg-265641/?linkid=0\" target=\"_new\">review</a> for comparison with other high end cards. Features 1344 CUDA cores.   [<a   rel=\"nofollow\" href=\"/link/msi-geforce-gtx-670-2gb-oc-video-card-400-at-newegg-265641/?linkid=1\" target=\"_new\">Compare</a>]");
    }
}
