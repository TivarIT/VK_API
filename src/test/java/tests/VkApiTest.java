package tests;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.ResponseResult;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.MyPage;
import pageObjects.NewsPage;
import pageObjects.VkIdPage;
import utils.DownloadUtil;
import utils.ImageComparisonUtils;
import utils.StringUtil;
import utils.api.ParamsMapUtil;
import utils.api.RestApiUtil;
import utils.api.VkApiUtil;

public class VkApiTest extends BaseTest{
    private static final ISettingsFile CREDENTIALS_DATA = new JsonSettingsFile("credentialsData.json");
    private static final String LOGIN = CREDENTIALS_DATA.getValue("/login").toString();
    private static final String PASSWORD = CREDENTIALS_DATA.getValue("/password").toString();
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final int RANDOM_STRING_LENGTH = Integer.parseInt(TEST_DATA.getValue("/randomStrLength").toString());
    private static final String USER_ID = TEST_DATA.getValue("/userId").toString();
    private static final String PHOTO_PATH = TEST_DATA.getValue("/photoPath").toString();
    private static final String ACTUAL_PHOTO_PATH = TEST_DATA.getValue("/actualPhotoPath").toString();
    private static final ISettingsFile API_DATA = new JsonSettingsFile("apiData.json");
    private static final String WALL_POST = API_DATA.getValue("/wallPost").toString();
    private static final String WALL_EDIT = API_DATA.getValue("/wallEdit").toString();
    private static final String CREATE_COMMENT = API_DATA.getValue("/createComment").toString();
    private static final String SAVE_WALL_PHOTO = API_DATA.getValue("/saveWallPhoto").toString();
    private static final String DELETE_POST = API_DATA.getValue("/deletePost").toString();

    private final LoginPage loginPage = new LoginPage();
    private final VkIdPage vkIdPage = new VkIdPage();
    private final NewsPage newsPage = new NewsPage();
    private final MyPage myPage = new MyPage();

    @Test
    public void vkApiTest(){
        Assert.assertTrue(loginPage.state().waitForDisplayed(), String.format("%s isn't opened", loginPage.getName()));
        loginPage.enterLogin(LOGIN);
        loginPage.clickSignInButton();

        Assert.assertTrue(vkIdPage.state().waitForDisplayed(), String.format("%s isn't opened", vkIdPage.getName()));
        vkIdPage.enterPassword(PASSWORD);
        vkIdPage.clickButtonContinue();
        log.info("Successfully logged in.");

        Assert.assertTrue(newsPage.state().waitForDisplayed(), String.format("%s isn't displayed", newsPage.getName()));
        log.info("News page is open.");
        newsPage.clickMyPageBtn();
        Assert.assertTrue(myPage.state().waitForDisplayed(), String.format("%s isn't opened", myPage.getName()));
        log.info("My page is open.");

        String randomStringExpected = StringUtil.getRandomString(RANDOM_STRING_LENGTH);
        ResponseResult createPostResponseActual = VkApiUtil.doVkApiMethod(WALL_POST, ParamsMapUtil.createPostParams(randomStringExpected));

        log.info(createPostResponseActual.getResult().asPrettyString());
        Assert.assertEquals(createPostResponseActual.getStatusCode(), HttpStatus.SC_OK, String.format("Response status isn't" +
                "%s", HttpStatus.SC_OK));
        log.info("Post created.");

        String postIdActual = RestApiUtil.getIdFromPost(createPostResponseActual);
        Assert.assertTrue(myPage.isUserInPostCorrect(USER_ID, postIdActual), "Wrong user of post!");
        Assert.assertEquals(myPage.getTextOfLabelOfPost(USER_ID, postIdActual), randomStringExpected,
                String.format("Text in post isn't as expected:" + " %s", randomStringExpected));
        log.info("Post correct.");

        String uploadUrlActual = VkApiUtil.getUploadUrl(VkApiUtil.getWallPhotoServerResponse());
        log.info(uploadUrlActual);

        ResponseResult uploadPhotoResultActual = VkApiUtil.doVkApiMethod(SAVE_WALL_PHOTO, ParamsMapUtil.saveUploadWallPhotoParams(VkApiUtil.uploadPhotoOnTheWall(uploadUrlActual)));
        String photoIdActual = RestApiUtil.getIdFromUploadedPhoto(uploadPhotoResultActual);
        ResponseResult editResponseResultActual = VkApiUtil.doVkApiMethod(WALL_EDIT, ParamsMapUtil.editPostParams(StringUtil
                .getRandomString(RANDOM_STRING_LENGTH), postIdActual, photoIdActual));
        Assert.assertEquals(editResponseResultActual.getStatusCode(), HttpStatus.SC_OK, String.format("Response status isn't" +
                "%s", HttpStatus.SC_OK));
        log.info("Photo added.");

        myPage.clickAndWaitPhotoFromPost(postIdActual, photoIdActual);
        DownloadUtil.downloadPhoto(myPage.getImageSrcAttribute());
        Assert.assertTrue(ImageComparisonUtils.compareImages(ACTUAL_PHOTO_PATH, PHOTO_PATH), "Another photo expected to be uploaded");
        ImageComparisonUtils.deleteComparedImage();
        log.info("Photo correct.");

        myPage.clickButtonClosePost();
        Assert.assertNotEquals(randomStringExpected, myPage.getTextOfLabelOfPost(USER_ID, postIdActual),"Text hasn't been changed!");

        ResponseResult createCommentResponseActual = VkApiUtil.doVkApiMethod(CREATE_COMMENT, ParamsMapUtil.createCommentOnPostParams(StringUtil.getRandomString(RANDOM_STRING_LENGTH)
                , postIdActual));
        Assert.assertEquals(createCommentResponseActual.getStatusCode(), HttpStatus.SC_OK, String.format("Response" +
                " status isn't %s", HttpStatus.SC_OK));
        log.info("Comment added.");

        myPage.clickButtonShowComments(USER_ID, postIdActual);
        Assert.assertTrue(myPage.isLabelOfCommentDisplayed(postIdActual, USER_ID),"No comment created by author");
        log.info("Comment correct.");

        myPage.clickButtonLikeOfPost(USER_ID, postIdActual);

        Assert.assertTrue(VkApiUtil.isPostLikedByAuthor(postIdActual), String.format("Post with" +
                "id: %s isn't liked by User with id: %s", postIdActual, USER_ID));
        log.info("Post liked.");

        Assert.assertEquals(VkApiUtil.doVkApiMethod(DELETE_POST, ParamsMapUtil.deletePostParams(postIdActual)).getStatusCode()
                , HttpStatus.SC_OK, String.format("Response status isn't %s", HttpStatus.SC_OK));

        Assert.assertTrue(myPage.isLabelLabelOfPost(USER_ID, postIdActual),"Created post isn't deleted!");
        log.info("Comment deleted.");
    }
}
