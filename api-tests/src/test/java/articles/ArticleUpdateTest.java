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

public class ArticleUpdateTest extends Authentication {

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
  public void updateAllTheFieldsOfArticle() throws IOException {
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
    String updatedArticleTitle =
        commonUtils.generateRandomNumber()
            + commonUtils
                .getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_TITLE)
                .replace("\"", "");
    String updatedArticleAbout =
        commonUtils
            .getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_ABOUT)
            .replace("\"", "");
    String updatedArticleContent =
        commonUtils
            .getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_CONTENT)
            .replace("\"", "");
    String updatedArticleTag =
        commonUtils
            .getInputDataForArticles(Constants.FULLY_UPDATED_ARTICLE, Constants.ARTICLES_TAG)
            .replace("\"", "");

    logger.log(Level.INFO, "Invoke create article request");
    response =
        article.createArticleRequest(
            articleTitle, articleAbout, articleContent, articleTag, jwtToken);
    // Validate response code is 200
    Validations.ValidateResponseCode(response.getStatusCode(), Constants.STATUS_CODE_200);
    String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

    logger.log(Level.INFO, "Invoke update article request");
    Response updateResponse =
        article.updateArticle(
            slug,
            updatedArticleTitle,
            updatedArticleAbout,
            updatedArticleContent,
            updatedArticleTag,
            jwtToken);
    Validations.ValidateResponseCode(updateResponse.getStatusCode(), Constants.STATUS_CODE_200);
    // Validate the response of update request
    Validations.ValidateValuesInResponseBody(
        updatedArticleTitle, Constants.ARTICLE_TITLE_JSONPATH, updateResponse);
    Validations.ValidateValuesInResponseBody(
        updatedArticleAbout, Constants.ARTICLE_DESCRIPTION_JSONPATH, updateResponse);
    Validations.ValidateValuesInResponseBody(
        updatedArticleContent, Constants.ARTICLE_BODY_JSONPATH, updateResponse);
    Validations.ValidateValuesInResponseBody(
        updatedArticleTag, Constants.ARTICLE_TAG_JSONPATH, updateResponse);
    Validations.ValidateValueExist(Constants.ARTICLE_SLUG_JSONPATH, updateResponse);

    logger.log(Level.INFO, "Invoke delete article request");
    article.deleteArticle(slug, jwtToken);
  }
}
