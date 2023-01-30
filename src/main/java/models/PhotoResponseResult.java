package models;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.response.ResponseBody;
import lombok.Getter;

@Getter
public class PhotoResponseResult {
    private static final ISettingsFile API_PARAM_AND_PATHS = new JsonSettingsFile("apiParams.json");
    private static final String HASH_PARAM = API_PARAM_AND_PATHS.getValue("/hashParam").toString();
    private static final String SERVER_PARAM = API_PARAM_AND_PATHS.getValue("/serverParam").toString();
    private static final String PHOTO_PARAM = API_PARAM_AND_PATHS.getValue("/photoParam").toString();

    private int server;
    private String photo;
    private String hash;

    public PhotoResponseResult(ResponseBody responseBody) {
        this.server = responseBody.jsonPath().get(SERVER_PARAM);
        this.photo = responseBody.jsonPath().get(PHOTO_PARAM);
        this.hash = responseBody.jsonPath().get(HASH_PARAM);
    }
}
