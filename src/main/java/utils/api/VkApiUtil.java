package utils.api;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.specification.RequestSpecification;
import models.PhotoResponseResult;
import models.ResponseResult;

import java.util.Map;

public class VkApiUtil {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String PHOTO_PATH = TEST_DATA.getValue("/photoPath").toString();
    private static final int LIKE_COUNT = Integer.parseInt(TEST_DATA.getValue("/liked").toString());
    private static final ISettingsFile API_DATA = new JsonSettingsFile("apiData.json");
    private static final String WALL_PHOTO_SERVER = API_DATA.getValue("/wallPhotoUploadServer").toString();
    private static final String RESPONSE_UPLOAD_URL_KEY = API_DATA.getValue("/responseUploadUrlKey").toString();
    private static final String TYPE_PHOTO_KEY = API_DATA.getValue("/typePhotoKey").toString();
    private static final String RESPONSE_LIKED_KEY = API_DATA.getValue("/responseLikeKey").toString();
    private static final String IS_LIKED_KEY = API_DATA.getValue("/isLiked").toString();


    public static ResponseResult doVkApiMethod(String method, Map<String, String> paramsMap) {
        RequestSpecification specification = RestApiUtil.requestBuilder(method)
                .addParams(paramsMap)
                .build();
        return new ResponseResult(ApiUtils.doPost(specification));
    }

    public static ResponseResult getWallPhotoServerResponse() {
        RequestSpecification specification = RestApiUtil.requestBuilder(WALL_PHOTO_SERVER).build();
        return new ResponseResult(ApiUtils.doPost(specification));
    }

    public static String getUploadUrl(ResponseResult responseResult) {
        return responseResult.getResult().path(RESPONSE_UPLOAD_URL_KEY);
    }

    public static PhotoResponseResult uploadPhotoOnTheWall(String uploadUrl) {
        ResponseResult responseResult = new ResponseResult(ApiUtils.doMultiPartPost(uploadUrl, PHOTO_PATH, TYPE_PHOTO_KEY));
        return new PhotoResponseResult(responseResult.getResult());
    }

    public static boolean isPostLikedByAuthor(String postId) {
        RequestSpecification specification = RestApiUtil.requestBuilder(IS_LIKED_KEY)
                .addParams(ParamsMapUtil.postLikedByAuthorParams(postId))
                .build();
        var responseResult = new ResponseResult(ApiUtils.doPost(specification));
        return responseResult.getResult().path(RESPONSE_LIKED_KEY).equals(LIKE_COUNT);
    }
}
