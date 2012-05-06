package net.bensdeals.util;

import com.xtremelabs.robolectric.util.Strings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestParseUtil {
    public static String fromXMLFile(String fileName) throws IOException {
        return Strings.fromStream(new FileInputStream(new File("test/responses", fileName)));
    }
}
