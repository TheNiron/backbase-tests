/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page';
import { CreateArticle } from '../../support/pages/create-article-page';
import { Article } from '../../support/pages/article-page';
import { CommonUtils } from '../../support/common-util';
import { USERNAME, PASSWORD } from '../../support/constants';

const ARTICLE_002 = require('../../fixtures/test_article_002');
const ARTICLE_003 = require('../../fixtures/test_article_003');

describe('Test the article creation functionality', () => {

  beforeEach(() => {
    let userName = 'niron@gmail.com';
    let password = 'rasanjana29';
    cy.loginByAuth0Api(userName, password)
    HomePage.navigateToBackBase();
  });

  it('Verify user is able to create an Article as expected', () => {
    let articleTitle;
    let articlejson = "test_article_001.json";
    let randomNumber = CommonUtils.generateRandomNumber();

    HomePage.navigateToCreateNewArticle();
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_001) => {
      articleTitle = ARTICLE_001.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_001.article_about, ARTICLE_001.article_content);
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      Article.deleteArticleByTitle(articleTitle);
    })
  });

  // Following test is a data driven test, combinations of inputs for articles are fed from test_article_002.json
  ARTICLE_002.forEach((article) => {
    it(`Verify the contents of articles with different markdown styles after publishing - ${article.test_name}`, () => {
      let articleTitle;
      let randomNumber = CommonUtils.generateRandomNumber();

      HomePage.navigateToCreateNewArticle();
      articleTitle = article.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, article.article_about, article.article_content);
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      // Verify article about
      Article.verifyArticleAbout(article.article_about);
      HomePage.openArticleByATitle(articleTitle);
      // Verify article title
      Article.verifyPublishedArticleTitle(articleTitle);
      // Verify article content
      Article.verifyArticleBodyContent(article.expected_article_content);
      Article.deleteArticleByTitle(articleTitle);
    });
  });

  // This test is supposed to fail as it's a bug and there are no mandatory field validations. 
  // Following test is a data driven test, combinations of invalid inputs for articles are fed from test_article_003.json
  ARTICLE_003.forEach((article) => {
    it(`Verify user is able to publish article without filling mandatory fields - ${article.test_name}`, () => {
      HomePage.navigateToCreateNewArticle();
      CreateArticle.writeNewArticle(article.article_title, article.article_about, article.article_content);
      CreateArticle.clickPublishArticle();
      Article.verifyErrorMessage(article.error_message);
    });
  });


});