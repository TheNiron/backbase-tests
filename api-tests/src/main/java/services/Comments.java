package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;

public class Comments {

  RequestSpecification httpPostRequest;
  RequestSpecification httpDeleteRequest;
  RequestSpecification httpGetRequest;
  Response response;
  CommonUtils utils = new CommonUtils();

  public Response addComment(String slug, String jwtAuth, String comment) throws IOException {
    RestAssured.baseURI = Constants.BASE_URL;
    httpPostRequest = RestAssured.given();
    response =
        httpPostRequest
            .auth()
            .basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
            .header(Constants.JWT_AUTHORIZATION_HEADER, jwtAuth)
            .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
            .body(
                String.format(utils.readFile(Constants.REQUESTS_DIR + "addComment.json"), comment))
            .post(String.format(Constants.COMMENT_RESOURCE_PATH, slug));
    return response;
  }

  public Response deleteComment(String slug, String jwtAuth, String id) throws IOException {
    RestAssured.baseURI = Constants.BASE_URL;
    httpDeleteRequest = RestAssured.given();
    response =
        httpDeleteRequest
            .auth()
            .basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
            .header(Constants.JWT_AUTHORIZATION_HEADER, jwtAuth)
            .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
            .delete(String.format(Constants.COMMENT_RESOURCE_PATH + "/" + id, slug));
    return response;
  }

  public Response getComment(String slug, String jwtAuth) throws IOException {
    RestAssured.baseURI = Constants.BASE_URL;
    httpGetRequest = RestAssured.given();
    response =
        httpGetRequest
            .auth()
            .basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
            .header(Constants.JWT_AUTHORIZATION_HEADER, jwtAuth)
            .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
            .get(String.format(Constants.ARTICLE_RESOURCE_PATH + "/" + slug + "/comments"));
    return response;
  }
}
