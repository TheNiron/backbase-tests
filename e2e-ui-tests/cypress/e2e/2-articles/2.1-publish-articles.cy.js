/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'
import { CreateArticle } from '../../support/pages/create-article-page'
import { Article } from '../../support/pages/article-page'
import { CommonUtils } from '../../support/common-util'
import { Settings } from '../../support/pages/settings'
import { CONDUIT_USERNAME1, CONDUIT_PASSWORD1, CONDUIT_USERNAME2, CONDUIT_PASSWORD2 } from '../../support/constants'

const ARTICLE_002 = require('../../fixtures/test_article_002')
const ARTICLE_003 = require('../../fixtures/test_article_003')

describe('Test the article creation functionality', () => {
  beforeEach(() => {
    // Custom cypress method that will handle login in API level
    cy.loginByAuth0Api(Cypress.env(CONDUIT_USERNAME1), Cypress.env(CONDUIT_PASSWORD1))
    HomePage.navigateToBackBase()
  })

  it('Verify user is able to create an Article as expected', () => {
    let articleTitle
    const articlejson = 'test_article_001.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_001) => {
      articleTitle = ARTICLE_001.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_001.article_about, ARTICLE_001.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  // Following test is a data driven test, combinations of inputs for articles are fed from test_article_002.json
  ARTICLE_002.forEach((article) => {
    it(`Verify the contents of articles with different markdown styles after publishing - ${article.test_name}`, () => {
      const randomNumber = CommonUtils.generateRandomNumber()
      const articleTitle = article.article_title + '_' + randomNumber
      HomePage.navigateToCreateNewArticle()
      CreateArticle.writeNewArticle(articleTitle, article.article_about, article.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle)
      // Verify article about
      Article.verifyArticleAbout(article.article_about)
      HomePage.openArticleByATitle(articleTitle)
      // Verify article title
      Article.verifyPublishedArticleTitle(articleTitle)
      // Verify article content
      Article.verifyArticleBodyContent(article.expected_article_content)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  // This test is supposed to fail as it's a bug and there are no mandatory field validations.
  // Following test is a data driven test, combinations of invalid inputs for articles are fed from test_article_003.json
  ARTICLE_003.forEach((article) => {
    it(`Verify user is able to publish article without filling mandatory fields - ${article.test_name}`, () => {
      HomePage.navigateToCreateNewArticle()
      CreateArticle.writeNewArticle(article.article_title, article.article_about, article.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyErrorMessage(article.error_message)
    })
  })

  it('Verify created article is displayed for other users', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToSettings()
      Settings.clickLogout()
      CommonUtils.loginWithDifferentUser(Cypress.env(CONDUIT_USERNAME2), Cypress.env(CONDUIT_PASSWORD2))
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle)
      HomePage.openArticleByATitle(articleTitle)
      Article.verifyPublishedArticleTitle(articleTitle)
      // Verify article content
      Article.verifyArticleBodyContent(ARTICLE_004.article_content)
    })
  })
})
