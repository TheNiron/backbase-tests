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

public class ArticleDeletionTest extends Authentication {
  String jwtToken;
  Response response;
  String articleTitle;
  String articleAbout;
  String articleContent;
  String articleTag;
  Logger logger = Logger.getLogger(ArticleCreationTest.class.getName());
  CommonUtils commonUtils = new CommonUtils();
  Article article = new Article();

  @BeforeClass
  public void printAll() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @BeforeMethod
  public void generateToken() throws IOException {
    logger.log(Level.INFO, "Generating JWT token");
    jwtToken = generateJwtToken(Constants.USER_EMAIL, Constants.USER_PASSWORD);
  }

  @Test
  public void verifyArticleDeletionRequest() throws IOException {
    articleTitle =
        commonUtils.generateRandomNumber()
            + commonUtils
                .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE)
                .replace("\"", "");
    articleAbout =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT)
            .replace("\"", "");
    articleContent =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT)
            .replace("\"", "");
    articleTag =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG)
            .replace("\"", "");

    logger.log(Level.INFO, "Invoke create article request");
    response =
        article.createArticleRequest(
            articleTitle, articleAbout, articleContent, articleTag, jwtToken);
    // Validate response code is 200
    Validations.ValidateResponseCode(response.getStatusCode(), Constants.STATUS_CODE_200);
    String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

    logger.log(Level.INFO, "Invoke delete article request");
    Response deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_204);
  }

  @Test
  public void deleteSameArticleTwice() throws IOException {
    articleTitle =
        commonUtils.generateRandomNumber()
            + commonUtils
                .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE)
                .replace("\"", "");
    articleAbout =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT)
            .replace("\"", "");
    articleContent =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT)
            .replace("\"", "");
    articleTag =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG)
            .replace("\"", "");

    logger.log(Level.INFO, "Invoke create article request");
    response =
        article.createArticleRequest(
            articleTitle, articleAbout, articleContent, articleTag, jwtToken);
    // Validate response code is 200
    Validations.ValidateResponseCode(response.getStatusCode(), Constants.STATUS_CODE_200);
    String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

    logger.log(Level.INFO, "Invoke delete article request twice");
    Response deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_204);
    deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_404);
  }

  @Test
  public void deleteArticleOfDifferentUser() throws IOException {
    articleTitle =
        commonUtils.generateRandomNumber()
            + commonUtils
                .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE)
                .replace("\"", "");
    articleAbout =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT)
            .replace("\"", "");
    articleContent =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT)
            .replace("\"", "");
    articleTag =
        commonUtils
            .getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG)
            .replace("\"", "");

    // Create an article with default user
    logger.log(Level.INFO, "Invoke create article request");
    response =
        article.createArticleRequest(
            articleTitle, articleAbout, articleContent, articleTag, jwtToken);
    // Validate response code is 200
    Validations.ValidateResponseCode(response.getStatusCode(), Constants.STATUS_CODE_200);
    String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

    // Generate JWT token from another user
    logger.log(Level.INFO, "Generating JWT token");
    jwtToken = generateJwtToken(Constants.USER_EMAIL2, Constants.USER_PASSWORD2);

    // Delete the created article with newly generated JWT token
    logger.log(Level.INFO, "Invoke delete article request");
    Response deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_403);
  }
}
