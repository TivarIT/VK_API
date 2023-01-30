package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadUtil {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String ACTUAL_PHOTO_PATH = TEST_DATA.getValue("/actualPhotoPath").toString();

    public static void downloadPhoto(String photoUrl) {
        try (InputStream is = new URL(photoUrl).openStream()) {
            AqualityServices.getLogger().info(String.format("Download Image by url: %s", photoUrl));
            Files.copy(is, Paths.get(new File(ACTUAL_PHOTO_PATH).getAbsolutePath()));
        } catch (IOException e) {
            AqualityServices.getLogger().error(String.format("Unchecked I/O exception, URL: %s",
                    photoUrl));
            throw new IllegalArgumentException("Unchecked I/O exception");
        }
    }
}
