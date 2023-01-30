package pageObjects;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyPage extends Form {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String USER_ID = TEST_DATA.getValue("/userId").toString();
    private final String LABEL_OF_COMMENTS = "//div[contains(@id, '%s') and contains(@id, '%s')]//div[contains(@class," +
            " 'wall_reply_text')]";
    private final String LABEL_OF_POST = "wpt%s_%s";
    private final String BUTTON_LIKE_OF_POST = "//div[@data-reaction-target-object = 'wall%s_%s']";
    private final String BUTTON_SHOW_COMMENTS = "//a[@href = '/wall%s_%s']//span[contains(@class, 'next_label')]";
    private final String PHOTO_FROM_POST = "//div[@id='post%s_%s']//a[@href='/photo%s_%s']";
    private final IButton btnClosePost = getElementFactory().getButton(By.xpath("//div[@class = 'pv_close_btn']"),
            "Button close opened post");
    private final ILink image = getElementFactory().getLink(By.xpath("//div[@id='pv_photo']/img"), "Image");
    private final static String SRC_ATTRIBUTE = "src";

    public MyPage() {
        super(By.id("profile_redesigned"), "My page");
    }

    private ILabel getLabelOfPost(String userId, String postId) {
        return getElementFactory().getLabel(By.id(String.format(LABEL_OF_POST, userId, postId)), String.format("post with" +
                "id: %s, created by user with id: %s", postId, userId));
    }

    public String getTextOfLabelOfPost(String userId, String postId){
        return getLabelOfPost(userId, postId).getText();
    }

    public void clickButtonShowComments(String userId, String postId) {
        getElementFactory().getButton(By.xpath(String.format(BUTTON_SHOW_COMMENTS, userId, postId)),
                "show comments button").click();
    }

    private ILabel getLabelOfComment(String postId, String userId) {
        return getElementFactory().getLabel(By.xpath(String.format(LABEL_OF_COMMENTS, postId, userId)), String.format(
                "label of comment on the post with id: %s, created by user with id: %s", postId, userId));
    }

    public Boolean isUserInPostCorrect(String userId, String postId) {
        return getLabelOfPost(userId, postId).getLocator().toString().contains(userId);
    }

    private ILabel getPhotoFromPost(String postId, String photoId) {
        return getElementFactory().getLabel(By.xpath(String.format(PHOTO_FROM_POST, USER_ID, postId, USER_ID, photoId)),
                String.format("photo" + "with id: %s", photoId));
    }

    public void clickAndWaitPhotoFromPost(String postId, String photoId) {
        getPhotoFromPost(postId, photoId).clickAndWait();
    }

    public String getImageSrcAttribute() {
        return image.getAttribute(SRC_ATTRIBUTE);
    }

    public void clickButtonClosePost() {
        btnClosePost.click();
    }

    private IButton getButtonLikeOfPost(String userId, String postId) {
        return getElementFactory().getButton(By.xpath(String.format(BUTTON_LIKE_OF_POST, userId, postId)), String.format(
                "button like from post with id: %s, created by user with id: %s", postId, userId));
    }

    public boolean isLabelOfCommentDisplayed(String postId, String userId){
        return getLabelOfComment(postId, userId).state().waitForDisplayed();
    }

    public void clickButtonLikeOfPost(String userId, String postId){
        getButtonLikeOfPost(userId, postId).click();
    }

    public boolean isLabelLabelOfPost(String userId, String postId){
        return getLabelOfPost(userId, postId).state().waitForNotDisplayed();
    }
}
