package com.application.utils;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;

import java.io.IOException;
import java.util.Base64;

public class DataUtils {
    public static String encodeImg(byte[] file) {
        String ans = "data:image/png;base64,";
        if (file != null) {
            ans += Base64.getEncoder().encodeToString(file);
        }
        return ans;
    }

    public static byte[] compressUserPicture(byte[] file) {
        return compress(file, 150, 150);
    }

    public static byte[] compressContestPicture(byte[] file) {
        return compress(file, 250, 450);
    }

    private static byte[] compress(byte[] file, int height, int width) {
        Tinify.setKey("1N976DQMQM3lJlJbhkSFyhCvGQXmgFZV");
        Options options = new Options()
                .with("method", "fit")
                .with("width", width)
                .with("height", height);
        Source s = Tinify.fromBuffer(file).resize(options);
        try {
            return s.toBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
