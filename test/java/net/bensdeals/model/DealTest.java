package net.bensdeals.model;

import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;
import static net.bensdeals.model.Deal.parseXml;
import static net.bensdeals.util.TestParseUtil.fromXMLFile;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DealTest {
    private List<Deal> deals;

    @Before
    public void setUp() throws Exception {
        deals = parseXml(fromXMLFile("homepage.xml"));
    }

    @Test
    public void shouldParseXML() throws Exception {
        expect(deals.size()).toEqual(20);
    }

    @Test
    public void shouldParseDealFeilds() throws Exception {
        Deal deal = deals.get(0);
        expect(deal.getTitle()).toEqual("3-Year Subscription to Muscle &amp; Fitness $9.58 at DiscountMags");
        expect(deal.getDescription()).toEqual("Today only. DiscountMags has a 3-Year Subscription to Muscle & Fitness Magazine (36 issues) for $59.97 - $50.39 off with coupon code <B>7938</b> = <B>$9.58</b> with free shipping.  A two-year subscription is <b>$7.18</b> and a one-year subscription is <b>$3.99</b> with same code.    [<a                 rel=\"nofollow\" href=\"/link/3-year-subscription-to-muscle-amp-fitness-9-58-at-discountmags-253527/?linkid=0\" target=\"_new\">Compare</a>]");
        expect(deal.getLink()).toEqual("http://bensbargains.net/deal/3-year-subscription-to-muscle-amp-fitness-9-58-at-discountmags-253527/?referrer=rss");
        expect(deal.getDate()).toEqual("Sat, 05 May 2012 10:42:48 -0700");
        expect(deal.getImageUrl()).toEqual("http://cdn2.bensimages.com/img118828l.jpg");
    }

    @Test
    public void shouldUseLargeImageUrl() throws Exception {
        expect(deals.get(0).getImageUrl()).toEqual("http://cdn2.bensimages.com/img118828l.jpg");
    }

    @Test
    public void shouldTestParseDealXml() throws Exception {
        String desc = "<img align=\"right\" src=\"http://cdn1.bensimages.com/img63536.jpg\" />Advance " +
                "Auto Parts is offering $10 off $30+ (33%) or $20 off $50+ (40%) or $30 off $100+ (30%) " +
                "with coupon code <B>A123</B>. In-store pickup is available for most products, and orders " +
                "over $75 ship free.<br><br><li>20% off Sitewide with code <B>P20</b><br><li>$30 off $75+ " +
                "(40%) with code <B>BIG30</b><br><li>$50 off $150+ (33%) with code <B>VISA</b>";

        String imageUrl = desc.substring(0, desc.indexOf("/>"));
        desc = desc.substring(imageUrl.length() + 2);
        String prefix = "src=\"";
        String substring = imageUrl.substring(imageUrl.indexOf(prefix) + prefix.length(), imageUrl.lastIndexOf("\""));
        System.out.println("substring = " + substring);
        System.out.println("desc = " + desc);
    }
}
