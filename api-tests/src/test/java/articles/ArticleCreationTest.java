package articles;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import services.Article;
import services.Authentication;
import utils.Validations;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleCreationTest extends Authentication {

    String jwtToken;
    Response response;
    String articleTitle;
    String articleAbout;
    String articleContent;
    String articleTag;
    Logger logger  = Logger.getLogger(ArticleCreationTest.class.getName());
    CommonUtils commonUtils = new CommonUtils();
    Article article = new Article();

    @BeforeClass
    public void printAll(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeMethod
    public void generateToken() throws IOException {
        logger.log(Level.INFO, "Generating JWT token");
        jwtToken = generateJwtToken(Constants.USER_EMAIL,Constants.USER_PASSWORD);
    }

    @Test
    public void createNewArticleAndValidateResponse() throws IOException {
        articleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        articleAbout = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        articleContent = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        articleTag = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");

        logger.log(Level.INFO, "Invoke create article request");

        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, jwtToken);
        //Validate response code is 200
        Validations.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_200);
        //Validate the essentials fields in response body
        Validations.ValidateValuesInResponseBody(Constants.USERNAME,Constants.AUTHOR_USERNAME_JSONPATH,response);
        Validations.ValidateValuesInResponseBody(articleTitle,Constants.ARTICLE_TITLE_JSONPATH,response);
        Validations.ValidateValuesInResponseBody(articleAbout,Constants.ARTICLE_DESCRIPTION_JSONPATH,response);
        Validations.ValidateValuesInResponseBody(articleContent,Constants.ARTICLE_BODY_JSONPATH,response);
        Validations.ValidateValuesInResponseBody(articleTag,Constants.ARTICLE_TAG_JSONPATH,response);
        Validations.ValidateValueExist(Constants.ARTICLE_SLUG_JSONPATH,response);
        String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

        logger.log(Level.INFO, "Invoke delete article request");
        Response deleteResponse = article.deleteArticle(slug,jwtToken);
        Validations.ValidateResponseCode(deleteResponse.getStatusCode(),Constants.STATUS_CODE_204);
    }

    @Test
    public void updateAllTheFieldsOfArticle() throws IOException {
        articleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        articleAbout = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        articleContent = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        articleTag = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");
        String updatedArticleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        String updatedArticleAbout = commonUtils.getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        String updatedArticleContent = commonUtils.getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        String updatedArticleTag = commonUtils.getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");

        logger.log(Level.INFO, "Invoke create article request");
        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, jwtToken);
        //Validate response code is 200
        Validations.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_200);
        String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

        logger.log(Level.INFO, "Invoke update article request");
        Response updateResponse = article.updateArticle(slug, updatedArticleTitle, updatedArticleAbout, updatedArticleContent, updatedArticleTag, jwtToken);
        Validations.ValidateResponseCode(updateResponse.getStatusCode(),Constants.STATUS_CODE_200);
        //Validate the response of update request
        Validations.ValidateValuesInResponseBody(updatedArticleTitle,Constants.ARTICLE_TITLE_JSONPATH,updateResponse);
        Validations.ValidateValuesInResponseBody(updatedArticleAbout,Constants.ARTICLE_DESCRIPTION_JSONPATH,updateResponse);
        Validations.ValidateValuesInResponseBody(updatedArticleContent,Constants.ARTICLE_BODY_JSONPATH,updateResponse);
        Validations.ValidateValuesInResponseBody(updatedArticleTag,Constants.ARTICLE_TAG_JSONPATH,updateResponse);
        Validations.ValidateValueExist(Constants.ARTICLE_SLUG_JSONPATH,updateResponse);

        logger.log(Level.INFO, "Invoke delete article request");
        article.deleteArticle(slug,jwtToken);
    }

    @Test
    public void createNewArticleWithExistingTitle() throws IOException {
        articleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        articleAbout = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        articleContent = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        articleTag = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");

        logger.log(Level.INFO, "Invoke create article request");
        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, jwtToken);
        //Validate response code is 200
        Validations.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_200);
        String slugOfFirstArticle = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

        logger.log(Level.INFO, "Invoke second create article request");
        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, jwtToken);
        //Validate response code is 200
        Validations.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_200);
        String slugOfSecondArticle = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

        logger.log(Level.INFO, "Invoke delete articles request");
        article.deleteArticle(slugOfFirstArticle,jwtToken);
        article.deleteArticle(slugOfSecondArticle,jwtToken);
    }

    @Test
    public void invokeCreateArticleWithInvalidJWTtoken() throws IOException {
        articleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        articleAbout = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        articleContent = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        articleTag = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");
        String invalidJWT = "eyJhbGciOiJIUzI1NiIsIc2342IkpXVCJ9.eyJpZCI6IjYzMjFiZjNhMjA5MmNmYzYwNjhlNzk4YyIsInVzZXJuYW1lIjoiaGFycnkiLCJleHAiOjE2Njg1MjExNTEsImlhdCI6MTY2MzMzNzE1MX0.zd6pbBUPUZ8qd-1PwvxYuchRb7rHwbOQqXgI4uWJnKY";

        logger.log(Level.INFO, "Invoke create article request");
        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, invalidJWT);
        //Validate response code is 401
        Validations.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_401);
    }

}
