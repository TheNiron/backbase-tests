/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page';
import { CreateArticle } from '../../support/pages/create-article-page';
import { Article } from '../../support/pages/article-page';
import { CommonUtils } from '../../support/common-util';

describe('Test Tags for the article functionality', () => {

  beforeEach(() => {
    let userName = 'niron@gmail.com';
    let password = 'rasanjana29';
    cy.loginByAuth0Api(userName, password)
    HomePage.navigateToBackBase();
  });

  it('Verify user is able to add tags', () => {
    let articleTitle;
    let articlejson = "test_article_004.json";
    let randomNumber = CommonUtils.generateRandomNumber();

    HomePage.navigateToCreateNewArticle();
    // below fixture will load input data from a json file which will be used as input to write the article
    var tags = ['this_is_tag_1', 'this_is_tag_w'];
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content);
      CreateArticle.addTag(tags)
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      Article.verifyArticleContainsTags(tags);
      Article.assertNumberOfTags(tags.length);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      Article.deleteArticleByTitle(articleTitle);
    })
  });

  it('Verify user is able to add only limited no. of tags', () => {
    let articleTitle;
    let articlejson = "test_article_004.json";
    let randomNumber = CommonUtils.generateRandomNumber();

    HomePage.navigateToCreateNewArticle();
    // below fixture will load input data from a json file which will be used as input to write the article
    var tags = ['this_is_tag_1', 'this_is_tag_2', 'this_is_tag_3', 'this_is_tag_4', 'this_is_tag_5', 'this_is_tag_6', 'this_is_tag_7'];
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content);
      CreateArticle.addTag(tags)
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      Article.verifyArticleContainsTags(tags);
      // User should only be able to add 5 tags per article
      Article.assertNumberOfTags(5);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      Article.deleteArticleByTitle(articleTitle);
    })
  });

  // This test fails as bug allows user to add empty tags
  it('Verify user is not allowed to add empty tags', () => {
    let articleTitle;
    let articlejson = "test_article_004.json";
    let randomNumber = CommonUtils.generateRandomNumber();

    HomePage.navigateToCreateNewArticle();
    // below fixture will load input data from a json file which will be used as input to write the article
    var tags = ['{backspace}','{backspace}'];
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber;
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content);
      CreateArticle.addTag(tags)
      CreateArticle.clickPublishArticle();
      Article.verifyArticlePublishedSuccessfully(articleTitle);
      // Empty tags shouldn't be added to article
      Article.assertNumberOfTags(0);
      HomePage.navigateToHomePage();
      HomePage.navigateToGlobalFeed();
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
      Article.deleteArticleByTitle(articleTitle);
    })
  });

    it('Verify user is not allowed to add duplicate tags', () => {
      let articleTitle;
      let articlejson = "test_article_004.json";
      let randomNumber = CommonUtils.generateRandomNumber();
      HomePage.navigateToCreateNewArticle();
      // below fixture will load input data from a json file which will be used as input to write the article
      var tags = ['Twin-tags','Twin-tags','Twin-tags'];
      cy.fixture(articlejson).then((ARTICLE_004) => {
        articleTitle = ARTICLE_004.article_title + '_' + randomNumber;
        CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content);
        CreateArticle.addTag(tags)
        CreateArticle.clickPublishArticle();
        Article.verifyArticlePublishedSuccessfully(articleTitle);
        // Empty tags shouldn't be added to article
        Article.verifyArticleContainsTags(['Twin-tags']);
        Article.assertNumberOfTags(1);
        HomePage.navigateToHomePage();
        HomePage.navigateToGlobalFeed();
        HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle);
        Article.deleteArticleByTitle(articleTitle);
      })
    });
  

});