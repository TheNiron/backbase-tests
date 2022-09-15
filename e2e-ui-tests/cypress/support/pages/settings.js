/// <reference types="cypress" />

const logOutButton = 'button'

export class Settings {
  static clickLogout () {
    cy.get(logOutButton).contains('Or click here to logout.').click()
  }
}
