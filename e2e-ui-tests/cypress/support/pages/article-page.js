/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'
import { TIMEOUT_SHORT } from '../constants'

const titleField = '[class="container"]>h1'
const deleteButton = 'button[class="btn btn-sm btn-outline-danger"]'
const articlePreview = 'a[class="preview-link"]>p'
const articleBody = 'p'
const errorText = '[class="error-message"]>'
const tagList = '[class="tag-list"]>li'
const commentField = 'textarea[placeholder="Write a comment..."]'
const commentPostButton = 'button[type="submit"]'
const postedCommentField = 'p[class="card-text"]'
const commentDeleteButton = 'span>i[class="ion-trash-a"]'

export class Article {
  static verifyArticlePublishedSuccessfully (articleTitle) {
    this.verifyPublishedArticleTitle(articleTitle)
    cy.get('button').contains('Delete Article').should('be.visible')
  }

  static verifyPublishedArticleTitle (articleTitle) {
    cy.get(titleField).contains(articleTitle).should('be.visible')
  }

  static clickDeleteArticle () {
    cy.get(deleteButton).contains('Delete Article').click()
  }

  static deleteArticleByTitle (title) {
    HomePage.navigateToHomePage()
    HomePage.navigateToGlobalFeed()
    HomePage.openArticleByATitle(title)
    Article.clickDeleteArticle(title)
  }

  static verifyArticleAbout (about) {
    cy.get(articlePreview).contains(about).should('be.visible')
  }

  static verifyArticleBodyContent (body) {
    // cy.log(cy.get(articleBody));
    cy.get(articleBody).then(function ($elem) {
      cy.log($elem.text())
    })
    cy.get(articleBody).contains(body).should('be.visible')
  }

  static verifyErrorMessage (message) {
    cy.get(errorText).contains(message).should('be.visible')
  }

  static verifyArticleContainsTags (tags) {
    for (let i = 0; i < tags.length; i++) {
      cy.get(tagList).contains(tags[i]).should('be.visible')
    }
  }

  static assertNumberOfTags (noOftags) {
    cy.get(tagList).should('have.length', noOftags)
  }

  static writeAcomment (comment) {
    cy.get(commentField).type(comment)
  }

  static postComment () {
    cy.get(commentPostButton).click()
  }

  static verifyCommentIsAvailable (comment) {
    cy.wait(TIMEOUT_SHORT)
    cy.get(postedCommentField).contains(comment).should('be.visible')
  }

  static deleteComment () {
    cy.get(commentDeleteButton).click()
  }

  static verifyCommentIsNotAvailable (comment) {
    // cy.get(postedCommentField).should('not.include.text', comment)
    cy.get(postedCommentField).should('not.exist')
  }

  static verifyCommentFieldIsNotAvailable () {
    cy.get(commentField).should('not.exist')
  }

  static verifyDeleteArticleButtonIsNotAvailable () {
    cy.get(deleteButton).should('not.exist')
  }
}
