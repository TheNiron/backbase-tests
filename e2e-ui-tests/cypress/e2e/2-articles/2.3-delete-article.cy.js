/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'
import { CreateArticle } from '../../support/pages/create-article-page'
import { Article } from '../../support/pages/article-page'
import { CommonUtils } from '../../support/common-util'
import { Settings } from '../../support/pages/settings'
import { CONDUIT_USERNAME1, CONDUIT_PASSWORD1, CONDUIT_USERNAME2, CONDUIT_PASSWORD2 } from '../../support/constants'

describe('Test the article deletion functionality', () => {
  beforeEach(() => {
    cy.loginByAuth0Api(Cypress.env(CONDUIT_USERNAME1), Cypress.env(CONDUIT_PASSWORD1))
    HomePage.navigateToBackBase()
  })

  it('Verify user is able to delete an Article as expected', () => {
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
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.verifyPostedArticleListedInGlobalFeed(articleTitle)
      Article.deleteArticleByTitle(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      // Verify article is removed from the Global feed
      HomePage.verifyArticleTitleIsNotPresent(articleTitle)
    })
  })

  it('Verify created article can not be deleted by different user', () => {
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
      Article.verifyDeleteArticleButtonIsNotAvailable()
    })
  })
})
