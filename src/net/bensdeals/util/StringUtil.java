package net.bensdeals.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {
    private static final int BUFFER_SIZE = 4096;

    public static FileInputStream responseAsStream(String filename) throws IOException {
        if (!filename.endsWith(".xml")) {
            filename += ".xml";
        }
        File file = new File("test/responses", filename);
        return new FileInputStream(file);
    }

    public static String fromStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), BUFFER_SIZE);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                ALog.i(line);
                stringBuilder.append(line);
            }
        } finally {
            inputStream.close();
        }
        return stringBuilder.toString();
    }
}
