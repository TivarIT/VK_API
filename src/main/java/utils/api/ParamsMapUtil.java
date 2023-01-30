package utils.api;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.PhotoResponseResult;

import java.util.HashMap;
import java.util.Map;

public class ParamsMapUtil {
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String POST_TYPE_VALUE = TEST_DATA.getValue("/postTypeValue").toString();
    private static final String OWNER_ID = TEST_DATA.getValue("/userId").toString();
    private static final String API_VERSION = TEST_DATA.getValue("/vkApiVersion").toString();
    private static final ISettingsFile CREDENTIALS_DATA = new JsonSettingsFile("credentialsData.json");
    private static final String ACCESS_TOKEN = CREDENTIALS_DATA.getValue("/token").toString();
    private static final ISettingsFile API_PARAM_AND_PATHS = new JsonSettingsFile("apiParams.json");
    private static final String MESSAGE_PARAM = API_PARAM_AND_PATHS.getValue("/messageParam").toString();
    private static final String POST_ID_PARAM = API_PARAM_AND_PATHS.getValue("/postIdParam").toString();
    private static final String ITEM_ID_PARAM = API_PARAM_AND_PATHS.getValue("/itemIdParam").toString();
    private static final String ATTACHMENTS_PARAM = API_PARAM_AND_PATHS.getValue("/attachmentsParam").toString();
    private static final String TYPE_PARAM = API_PARAM_AND_PATHS.getValue("/typeParam").toString();
    private static final String HASH_PARAM = API_PARAM_AND_PATHS.getValue("/hashParam").toString();
    private static final String SERVER_PARAM = API_PARAM_AND_PATHS.getValue("/serverParam").toString();
    private static final String PHOTO_PARAM = API_PARAM_AND_PATHS.getValue("/photoParam").toString();
    private static final String OWNER_ID_PARAM = API_PARAM_AND_PATHS.getValue("/ownerIdParam").toString();
    private static final String ACCESS_TOKEN_PARAM = API_PARAM_AND_PATHS.getValue("/accessTokenParam").toString();
    private static final String API_VERSION_PARAM = API_PARAM_AND_PATHS.getValue("/apiVersionParam").toString();
    private static Map<String, String> parameters;

    public static Map<String, String> baseParams(){
        parameters = new HashMap<String, String>();
        parameters.put(OWNER_ID_PARAM, OWNER_ID);
        parameters.put(ACCESS_TOKEN_PARAM, ACCESS_TOKEN);
        parameters.put(API_VERSION_PARAM, API_VERSION);
        return parameters;
    }

    public static Map<String, String> createPostParams(String message){
      parameters = new HashMap<String, String>();
        parameters.put(MESSAGE_PARAM, message);
        return parameters;
    }

    public static Map<String, String> editPostParams(String message, String postId, String photoId){
       parameters = new HashMap<String, String>();
        parameters.put(POST_ID_PARAM, postId);
        parameters.put(MESSAGE_PARAM, message);
        parameters.put(ATTACHMENTS_PARAM, String.format("photo%s_%s", OWNER_ID, photoId));
        return parameters;
    }

    public static Map<String, String> saveUploadWallPhotoParams(PhotoResponseResult photoResponseResult){
        parameters = new HashMap<String, String>();
        parameters.put(HASH_PARAM, photoResponseResult.getHash());
        parameters.put(SERVER_PARAM, String.valueOf(photoResponseResult.getServer()));
        parameters.put(PHOTO_PARAM, photoResponseResult.getPhoto());
        return parameters;
    }

    public static Map<String, String> createCommentOnPostParams(String message, String postId){
        parameters = new HashMap<String, String>();
        parameters.put(MESSAGE_PARAM, message);
        parameters.put(POST_ID_PARAM, postId);
        return parameters;
    }

    public static Map<String, String> postLikedByAuthorParams(String postId){
        parameters = new HashMap<String, String>();
        parameters.put(TYPE_PARAM, POST_TYPE_VALUE);
        parameters.put(ITEM_ID_PARAM, postId);
        return parameters;
    }

    public static Map<String, String> deletePostParams(String postId){
        parameters = new HashMap<String, String>();
        parameters.put(POST_ID_PARAM, postId);
        return parameters;
    }
}
