package net.bensdeals.model.wrapper;

import net.bensdeals.model.SearchItem;
import net.bensdeals.network.response.Response;
import net.bensdeals.utils.StringUtil;
import org.junit.Before;
import org.junit.Test;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;

public class SearchResponseWrapperTest {

    public SearchResponseWrapper searchResponseWrapper;

    @Before
    public void setup() throws Exception {
        searchResponseWrapper = Response.OBJECT_MAPPER.readValue(StringUtil.responseAsStream("search_response.json"), SearchResponseWrapper.class);
    }

    @Test
    public void shouldParseJson() throws Exception {
        expect(searchResponseWrapper.getItems().size()).toEqual(25);
    }

    @Test
    public void shouldParsePaginationUrl() throws Exception {
        expect(searchResponseWrapper.getNextLink()).toEqual("https://www.googleapis.com/shopping/search/v1/public/products?country=US&q=mp3+player&startIndex=26&alt=json");
    }

    @Test
    public void shouldParseSearchItem() throws Exception {
        SearchItem item = searchResponseWrapper.getItems().get(0).item;
        expect(item.getBrand()).toEqual("Apple");
        expect(item.getTitle()).toEqual("Apple iPod touch Black 4th Generation 8GB Touch Screen Wi-Fi MP3");
        expect(item.getLink()).toEqual("http://www.target.com/p/Apple-iPod-touch-8GB-MP3-Player-4th-Generation-with-Touch-Screen-Wi-Fi-Black-MC540LL-A/-/A-12990622?ref=tgt_adv_XSG10001&AFID=Froogle_df&LNM=%7C12990622&CPNG=electronics&ci_src=14110944&ci_sku=12990622");
        expect(item.getDescription()).toEqual("Find portable mp3 players at Target.com! The world's most popular portable gaming device is even more fun. Now available in black and white, ipod touch includes ios 5 with over 200 new features, like imessage, notification center, and twitter integration. Send free, unlimited text messages over wi-fi with imessage. record hd video and make facetime calls. Visit the app store to choose from over 500000 apps. Ipod touch also features icloud, which stores your music, photos, apps, and more and wirelessly pushes them to all your devices.");
        expect(item.getImages().get(0).getLink()).toEqual("http://img3.targetimg3.com/wcsstore/TargetSAS/img/p/12/99/12990622.jpg");
    }
}
