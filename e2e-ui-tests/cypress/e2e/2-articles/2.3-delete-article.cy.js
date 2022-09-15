/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page';
import { CreateArticle } from '../../support/pages/create-article-page';
import { Article } from '../../support/pages/article-page';
import { CommonUtils } from '../../support/common-util';


describe('Test the article deletion functionality', () => {

  beforeEach(() => {
    let userName = 'niron@gmail.com';
    let password = 'rasanjana29';
    cy.loginByAuth0Api(userName, password)
    HomePage.navigateToBackBase();
  });

  it('Verify user is able to delete an Article as expected', () => {
    let articleTitle;
    let articlejson = "test_article_004.json";
    let randomNumber = CommonUtils.generateRandomNumber();

    HomePage.navigateToCreateNewArticle();
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content);
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      Article.deleteArticleByTitle(articleTitle);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      // Verify article is removed from the Global feed
      HomePage.verifyArticleTitleIsNotPresent(articleTitle);

    })
  });


});