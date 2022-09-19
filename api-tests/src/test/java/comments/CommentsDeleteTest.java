package comments;

import articles.ArticleCreationTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import services.Article;
import services.Authentication;
import services.Comments;
import utils.Validations;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentsDeleteTest extends Authentication {
  String jwtToken;
  Response response;
  String articleTitle;
  String articleAbout;
  String articleContent;
  String articleTag;
  Logger logger = Logger.getLogger(ArticleCreationTest.class.getName());
  CommonUtils commonUtils = new CommonUtils();
  Comments comments = new Comments();
  Article article = new Article();

  @BeforeMethod
  public void generateToken() throws IOException {
    logger.log(Level.INFO, "Generating JWT token");
    jwtToken = generateJwtToken(Constants.USER_EMAIL, Constants.USER_PASSWORD);
  }

  @Test
  public void verifyDeletingComment() throws IOException {
    String commentText = "This post is my own. Just commenting to see the comment :)";
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

    logger.log(Level.INFO, "Invoke post a comment request");
    Response commentResponse = comments.addComment(slug, jwtToken, commentText);
    Validations.ValidateResponseCode(commentResponse.getStatusCode(), Constants.STATUS_CODE_200);
    String commentId = commentResponse.body().jsonPath().getString(Constants.COMMENT_ID_JSONPATH);

    logger.log(Level.INFO, "Invoke delete a comment request");
    Response commentDeleteResponse = comments.deleteComment(slug, jwtToken, commentId);
    Validations.ValidateResponseCode(
        commentDeleteResponse.getStatusCode(), Constants.STATUS_CODE_204);

    logger.log(Level.INFO, "Invoke delete article request");
    Response deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_204);
  }

  @Test
  public void verifyDeletingOtherUsersComment() throws IOException {
    String commentText = "This post is my own. Just commenting to see the comment :)";
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

    logger.log(Level.INFO, "Invoke post a comment request");
    Response commentResponse = comments.addComment(slug, jwtToken, commentText);
    Validations.ValidateResponseCode(commentResponse.getStatusCode(), Constants.STATUS_CODE_200);
    String commentId = commentResponse.body().jsonPath().getString(Constants.COMMENT_ID_JSONPATH);

    // Generate JWT token from another user
    logger.log(Level.INFO, "Generating JWT token for user2");
    String jwtToken2 = generateJwtToken(Constants.USER_EMAIL2, Constants.USER_PASSWORD2);

    logger.log(Level.INFO, "Invoke delete a comment request");
    Response commentDeleteResponse = comments.deleteComment(slug, jwtToken2, commentId);
    Validations.ValidateResponseCode(
        commentDeleteResponse.getStatusCode(), Constants.STATUS_CODE_403);

    logger.log(Level.INFO, "Invoke delete article request");
    Response deleteResponse = article.deleteArticle(slug, jwtToken);
    Validations.ValidateResponseCode(deleteResponse.getStatusCode(), Constants.STATUS_CODE_204);
  }
}
