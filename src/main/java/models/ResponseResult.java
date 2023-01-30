package models;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lombok.Getter;

@Getter
public class ResponseResult {
    private int statusCode;
    private ResponseBody result;

    public ResponseResult(Response response) {
        this.statusCode = response.getStatusCode();
        this.result = response.getBody();
    }
}
