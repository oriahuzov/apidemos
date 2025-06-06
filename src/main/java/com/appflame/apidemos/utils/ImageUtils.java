package com.appflame.apidemos.utils;

import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Utility class for capturing screenshots from an AndroidDriver and detecting motion
 * between two images by computing a dynamic threshold based on pixel differences.
 */
@Slf4j
public class ImageUtils {

    private ImageUtils() {
    }

    /**
     * Captures a screenshot of the current screen and returns it as a BufferedImage.
     * Also copies the underlying PNG into "./test-screenshots" with a timestamped name.
     */
    public static BufferedImage takeScreenshotAsImage(AndroidDriver driver) throws IOException {
        File tmp = driver.getScreenshotAs(OutputType.FILE);

        String timestamp = String.valueOf(System.currentTimeMillis());
        Path screenshotsDir = Path.of(System.getProperty("user.dir"), "test-screenshots");
        Files.createDirectories(screenshotsDir);

        Path destination = screenshotsDir.resolve(timestamp + ".png");
        Files.copy(tmp.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        log.info("Screenshot saved to: {}", destination.toAbsolutePath());

        return ImageIO.read(tmp);
    }

    /**
     * Compares two BufferedImages (before & after) and returns the X‐coordinate of the centroid
     * of all pixels whose color changed “significantly.” This method:
     * <p>
     * 1) Scans every pixel and computes diff = |r2–r1| + |g2–g1| + |b2–b1|.
     * 2) Tracks maxDiff = the largest such diff among all pixels.
     * 3) Sets a dynamic threshold = maxDiff * 0.5 (50% of the maximum).
     * 4) In a second pass, collects all pixels whose diff > dynamic threshold, and computes
     * the centroid X = (sum of their x-coordinates) / (their count).
     * <p>
     * Returns –1 if no pixels exceed the dynamic threshold (i.e. no detectable motion).
     *
     * @param before the BufferedImage taken before dragging the SeekBar
     * @param after  the BufferedImage taken after dragging the SeekBar
     * @return the X‐coordinate of the centroid of “changed” pixels, or –1 if none found
     */
    public static int findMovementX(BufferedImage before, BufferedImage after) {
        int width = before.getWidth();
        int height = before.getHeight();

        if (after.getWidth() != width || after.getHeight() != height) {
            throw new IllegalArgumentException("Before/After images must match dimensions");
        }

        // First pass: find the maximum R+G+B difference among all pixels
        int maxDiff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = before.getRGB(x, y);
                int rgb2 = after.getRGB(x, y);

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = (rgb1) & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = (rgb2) & 0xFF;

                int diff = Math.abs(r2 - r1) + Math.abs(g2 - g1) + Math.abs(b2 - b1);
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
        }

        // If nothing changed at all, maxDiff will remain 0 → no motion detected
        if (maxDiff == 0) {
            return -1;
        }

        // Compute a dynamic threshold at 50% of the maximum observed difference
        int dynamicThreshold = maxDiff / 2;

        long sumX = 0;
        int count = 0;

        // Second pass: collect all pixels whose diff > dynamicThreshold
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb1 = before.getRGB(x, y);
                int rgb2 = after.getRGB(x, y);

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = (rgb1) & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = (rgb2) & 0xFF;

                int diff = Math.abs(r2 - r1) + Math.abs(g2 - g1) + Math.abs(b2 - b1);
                if (diff > dynamicThreshold) {
                    sumX += x;
                    count++;
                }
            }
        }

        if (count == 0) {
            return -1;  // no pixel exceed the dynamic threshold
        }
        return (int) (sumX / count);
    }
}
