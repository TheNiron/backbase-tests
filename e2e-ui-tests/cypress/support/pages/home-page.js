/// <reference types="cypress" />

import { USERNAME, PASSWORD, BACKBASE_URL, TIMEOUT_MEDIUM } from '../constants'

const homePageTitle = 'Conduit'
const articleTitleField = 'a[class="preview-link"]>h1'
const articlePreview = '[class="article-preview"]'
const settingsButton = 'a[routerlink="/settings"]'

export class HomePage {
  static navigateToBackBase () {
    // credentials and url is defined as an environment variable in cypress.config.js
    cy.visit('https://' + Cypress.env(USERNAME) + ':' + Cypress.env(PASSWORD) + Cypress.env(BACKBASE_URL))
    cy.title().should('eq', homePageTitle)
    return this
  }

  static navigateToSignIn () {
    cy.get('a[routerlink="/login"]', { timeout: TIMEOUT_MEDIUM }).click({ force: true })
  }

  static verifyNewArticleButton () {
    cy.get('a[routerlink="/editor"]').contains('New Article').should('be.visible')
    cy.log('Successfully Signed in!')
  }

  static navigateToCreateNewArticle () {
    cy.get('a[routerlink="/editor"]').contains('New Article').click()
    cy.get('button[type="button"]', { timeout: TIMEOUT_MEDIUM }).contains('Publish Article')
  }

  static navigateToHomePage () {
    cy.get('a[class="navbar-brand"]').click()
  }

  static navigateToGlobalFeed () {
    cy.get('[class="feed-toggle"]', { timeout: TIMEOUT_MEDIUM }).contains('Global Feed').click()
  }

  static verifyPostedArticleListedInGlobalFeed (title) {
    cy.get(articleTitleField).contains(title).should('be.visible')
  }

  static verifyArticleTitleIsNotPresent (title) {
    cy.get(articleTitleField).should('not.include.text', title)
  }

  static openArticleByATitle (title) {
    cy.get(articlePreview, { timeout: TIMEOUT_MEDIUM }).should('be.visible')
    cy.get(articleTitleField).contains(title).click({ force: true })
  }

  static navigateToSettings () {
    cy.get(settingsButton).click()
    cy.get('h1').contains('Your Settings').should('be.visible')
  }
}
