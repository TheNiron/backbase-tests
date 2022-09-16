package utils;

public class Constants {
	public static final int STATUS_CODE_200 = 200;
	public static final int STATUS_CODE_204 = 204;
	public static final int STATUS_CODE_401 = 401;
	public static final int STATUS_CODE_404 = 404;
	public static final int STATUS_CODE_403 = 403;
	public static final String BASE_USERNAME = "candidatex";
	public static final String BASE_PASSWORD = "qa-is-cool";
	public static final String USER_EMAIL = "niron@gmail.com";
	public static final String USER_PASSWORD = "rasanjana29";
	public static final String USER_EMAIL2 = "sahan@gmail.com";
	public static final String USER_PASSWORD2 = "rasanjana29";
	public static final String BASE_URL = "https://qa-task.backbasecloud.com";
	public static final String FILE_PATH = "src/main/resources/";
	public static final String REQUESTS_DIR = "requests/";
	public static final String ARTICLES_DIR = "articles/";

	public static final String ARTICLES_TITLE = "article_title";
	public static final String ARTICLES_ABOUT = "article_about";
	public static final String ARTICLES_CONTENT = "article_content";
	public static final String ARTICLES_TAG = "article_tag";
	public static final String LOGIN_REQUEST_PATH = "/api/users/login";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_JSON = "application/json";
	public static final String ARTICLE_RESOURCE_PATH = "/api/articles";
	public static final String JWT_AUTHORIZATION_HEADER = "JWTAuthorization";
	public static final String AUTHOR_USERNAME_JSONPATH = "article.author.username";
	public static final String USERNAME = "harry";
	public static final String ARTICLE_TITLE_JSONPATH = "article.title";
	public static final String ARTICLE_DESCRIPTION_JSONPATH = "article.description";
	public static final String ARTICLE_BODY_JSONPATH = "article.body";
	public static final String ARTICLE_TAG_JSONPATH = "article.tagList[0]";
	public static final String ARTICLE_SLUG_JSONPATH = "article.slug";
	public static final String UTF8 = "UTF-8";
	public static final String NEW_ARTICLE = "article_001_new_article";
	public static final String FULLY_UPDATED_ARTICLE = "article_002_full_updated_article";
	public static final String COMMENT_RESOURCE_PATH = "/api/articles/%s/comments";

	public static final String COMMENT_ID_JSONPATH = "comment.id";
	public static final String COMMENT_BODY_JSONPATH = "comment.body";
	public static final String COMMENT_AUTHOR_JSONPATH = "comment.author.username";
}
