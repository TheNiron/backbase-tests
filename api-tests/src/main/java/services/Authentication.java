package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;

public class Authentication {
    CommonUtils utils = new CommonUtils();
    Response response;
    RequestSpecification httpPostRequest;
    public String generateJwtToken(String userEmail, String userPassword) throws IOException {
        RestAssured.baseURI = Constants.BASE_URL;
        httpPostRequest = RestAssured.given();
        response = httpPostRequest
                .auth().basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body(String.format(utils.readFile(Constants.REQUESTS_DIR + "userLoginBody.json"), userEmail, userPassword))
                .post(Constants.LOGIN_REQUEST_PATH);
        return "Token " + response.body().jsonPath().getString("user.token");
    }
}
