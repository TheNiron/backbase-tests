package comments;

import articles.ArticleCreationTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import services.Article;
import services.Authentication;
import services.Comments;
import utils.Common;
import utils.CommonUtils;
import utils.Constants;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentsRetrieveTest extends Authentication {
    String jwtToken;
    Response response;
    String articleTitle;
    String articleAbout;
    String articleContent;
    String articleTag;
    Logger logger  = Logger.getLogger(ArticleCreationTest.class.getName());
    CommonUtils commonUtils = new CommonUtils();
    Comments comments = new Comments();
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
    public void retrieveCommentsFromAnArticle() throws IOException {
        String commentText = "This post is my own. Just commenting to see the comment :)";
        articleTitle = commonUtils.generateRandomNumber() + commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TITLE).replace("\"", "");
        articleAbout = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_ABOUT).replace("\"", "");
        articleContent = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_CONTENT).replace("\"", "");
        articleTag = commonUtils.getInputDataForArticles(Constants.NEW_ARTICLE, Constants.ARTICLES_TAG).replace("\"", "");

        logger.log(Level.INFO, "Invoke create article request");
        response = article.createArticleRequest(articleTitle, articleAbout, articleContent, articleTag, jwtToken);
        //Validate response code is 200
        Common.ValidateResponseCode(response.getStatusCode(),Constants.STATUS_CODE_200);
        String slug = response.body().jsonPath().getString(Constants.ARTICLE_SLUG_JSONPATH);

        logger.log(Level.INFO, "Invoke post a comment request");
        Response commentResponse = comments.addComment(slug, jwtToken,commentText);
        Common.ValidateResponseCode(commentResponse.getStatusCode(),Constants.STATUS_CODE_200);
        String commentId = commentResponse.body().jsonPath().getString(Constants.COMMENT_ID_JSONPATH);

        logger.log(Level.INFO, "Invoke retrieve comments request");
        Response commentGetResponse = comments.getComment(slug,jwtToken);
        Common.ValidateResponseCode(commentGetResponse.getStatusCode(),Constants.STATUS_CODE_200);
        //Validate retrieved comments contain the posted comment
        Common.ValidateValuesAreNotEqual(commentGetResponse.body().jsonPath().getString("comments"),commentId,"Response didn't contain the comment ID: "+commentId);

        logger.log(Level.INFO, "Invoke delete article request");
        Response deleteResponse = article.deleteArticle(slug,jwtToken);
        Common.ValidateResponseCode(deleteResponse.getStatusCode(),Constants.STATUS_CODE_204);
    }
}
