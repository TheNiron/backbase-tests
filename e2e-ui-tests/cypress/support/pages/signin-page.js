/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'

export class SigninPage {
  static signIn (username, password) {
    cy.get('[formcontrolname="email"]').type(username)
    cy.get('[formcontrolname="password"]').type(password)
    cy.get('button[type="submit"]').contains('Sign in').click()
    HomePage.verifyNewArticleButton()
    cy.log('User signed in successfully!')
  }
}
