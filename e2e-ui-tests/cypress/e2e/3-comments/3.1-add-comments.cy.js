/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'
import { CreateArticle } from '../../support/pages/create-article-page'
import { Article } from '../../support/pages/article-page'
import { CommonUtils } from '../../support/common-util'
import { Settings } from '../../support/pages/settings'
import { CONDUIT_USERNAME1, CONDUIT_PASSWORD1, CONDUIT_USERNAME2, CONDUIT_PASSWORD2 } from '../../support/constants'

describe('Test the adding comments functionality', () => {
  beforeEach(() => {
    cy.loginByAuth0Api(Cypress.env(CONDUIT_USERNAME1), Cypress.env(CONDUIT_PASSWORD1))
    HomePage.navigateToBackBase()
  })

  it('Verify user is able to add comments to own articles as signed in user', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    const commentText = 'Very informative article. Thanks and keep up the good work! :) '
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.writeAcomment(commentText)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  it('Verify user is able to add multiple comments to own article', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    const commentText1 = 'Very informative article. Thanks and keep up the good work! :) '
    const commentText2 = 'Good read. Thanks and keep up the good work! :) '
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.writeAcomment(commentText1)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText1)
      Article.writeAcomment(commentText2)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText2)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  // This test is failing cause comments are not retrieved after coming back to the article
  it('Verify posted comments are displayed after user comes back to Article', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    const commentText = 'Very informative article. Thanks and keep up the good work! :) '
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.writeAcomment(commentText)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.verifyCommentIsAvailable(commentText)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  it('Verify user is able to delete own comments', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    HomePage.navigateToCreateNewArticle()
    const commentText = 'Very informative article. Thanks and keep up the good work! :) '
    // below fixture will load input data from a json file which will be used as input to write the article
    cy.fixture(articlejson).then((ARTICLE_004) => {
      articleTitle = ARTICLE_004.article_title + '_' + randomNumber
      CreateArticle.writeNewArticle(articleTitle, ARTICLE_004.article_about, ARTICLE_004.article_content)
      CreateArticle.clickPublishArticle()
      Article.verifyArticlePublishedSuccessfully(articleTitle)
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.writeAcomment(commentText)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText)
      Article.deleteComment()
      Article.verifyCommentIsNotAvailable(commentText)
      Article.deleteArticleByTitle(articleTitle)
    })
  })

  it('Verify user is not able to add comments without signin', () => {
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
      HomePage.navigateToHomePage()
      HomePage.navigateToGlobalFeed()
      HomePage.openArticleByATitle(articleTitle)
      Article.verifyCommentFieldIsNotAvailable()
    })
  })

  it('Verify user is able to add comments to others articles as signed in user', () => {
    let articleTitle
    const articlejson = 'test_article_004.json'
    const randomNumber = CommonUtils.generateRandomNumber()
    const commentText = 'Very informative article. Thanks and keep up the good work! :) '
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
      HomePage.openArticleByATitle(articleTitle)
      Article.writeAcomment(commentText)
      Article.postComment()
      Article.verifyCommentIsAvailable(commentText)
    })
  })
})
