package utils.api;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.builder.RequestSpecBuilder;
import models.ResponseResult;
import utils.StringUtil;

public class RestApiUtil {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String BASE_API_URL = TEST_DATA.getValue("/baseApiUrl").toString();
    private static final ISettingsFile API_DATA = new JsonSettingsFile("apiData.json");
    private static final String RESPONSE_POST_ID_KEY = API_DATA.getValue("/responsePostIdKey").toString();
    private static final String RESPONSE_ID_KEY = API_DATA.getValue("/responseIdKey").toString();

    public static RequestSpecBuilder requestBuilder(String method) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_API_URL + method)
                .addParams(ParamsMapUtil.baseParams());
    }

    public static String getIdFromPost(ResponseResult responseResult) {
        return responseResult.getResult().path(RESPONSE_POST_ID_KEY).toString();
    }

    public static String getIdFromUploadedPhoto(ResponseResult responseResult) {
        return StringUtil.removeSquareBrackets(responseResult.getResult().path(RESPONSE_ID_KEY).toString());
    }
}
