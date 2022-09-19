package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;

public class Article {
    CommonUtils utils = new CommonUtils();
    Response response;
    RequestSpecification httpPostRequest;
    RequestSpecification httpDeleteRequest;
    RequestSpecification httpPutRequest;

    public Response createArticleRequest(String articleTitle, String articleAbout, String articleContent, String articleTag, String jwtAuth) throws IOException {
        RestAssured.baseURI = Constants.BASE_URL;
        httpPostRequest = RestAssured.given();
        response = httpPostRequest
                .auth().basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.JWT_AUTHORIZATION_HEADER, jwtAuth)
                .body(String.format(utils.readFile(Constants.REQUESTS_DIR +"createArticle.json"), articleTitle, articleAbout, articleContent, articleTag))
                .post(Constants.ARTICLE_RESOURCE_PATH);
        return response;
    }

    public Response deleteArticle(String slug, String jwtToken) {
        RestAssured.baseURI = Constants.BASE_URL;
        httpDeleteRequest = RestAssured.given();
        response = httpDeleteRequest
                .auth().basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
                .header(Constants.JWT_AUTHORIZATION_HEADER, jwtToken)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .delete(Constants.ARTICLE_RESOURCE_PATH + "/" + slug);
        return response;
    }

    public Response updateArticle(String slug, String articleTitle, String articleAbout, String articleContent, String articleTag, String jwtAuth) throws IOException {
        RestAssured.baseURI = Constants.BASE_URL;
        httpPutRequest = RestAssured.given();
        response = httpPutRequest
                .auth().basic(Constants.BASE_USERNAME, Constants.BASE_PASSWORD)
                .header(Constants.JWT_AUTHORIZATION_HEADER, jwtAuth)
                .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body(String.format(utils.readFile(Constants.REQUESTS_DIR +"createArticle.json"), articleTitle, articleAbout, articleContent, articleTag))
                .put(Constants.ARTICLE_RESOURCE_PATH + "/" + slug);

        return response;
    }


}
