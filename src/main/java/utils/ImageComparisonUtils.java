package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.github.romankh3.image.comparison.ImageComparisonUtil;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageComparisonUtils {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String ACTUAL_PHOTO_PATH = TEST_DATA.getValue("/actualPhotoPath").toString();

    public static boolean compareImages(String actualImg, String expectedImg){
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedImg);
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualImg);
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        return ImageComparisonState.MATCH.equals(imageComparisonResult.getImageComparisonState());
    }

    public static void deleteComparedImage() {
        File file = new File(ACTUAL_PHOTO_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
