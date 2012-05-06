package net.bensdeals.network.core;

import com.google.inject.Inject;
import net.bensdeals.provider.CacheDirProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UrlStreamer {
    private CacheDirProvider cacheDirProvider;

    @Inject
    public UrlStreamer(CacheDirProvider cacheDirProvider) {
        this.cacheDirProvider = cacheDirProvider;
    }

    public InputStream get(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        File file = new File(cacheDirProvider.get(), md5(url.getHost() + url.getPath() + url.getQuery()));

        if (file.exists() && file.length() > 0) {
            return new FileInputStream(file);
        }

        InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = inputStream.read(buffer)) >= 0) {
                fileOutputStream.write(buffer, 0, len);
            }
        } finally {
            fileOutputStream.close();
            inputStream.close();
        }
        return new FileInputStream(file);
    }

    public static String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
