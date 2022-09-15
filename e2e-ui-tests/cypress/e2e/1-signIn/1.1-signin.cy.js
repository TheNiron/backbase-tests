/* eslint-disable no-undef */
/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page'
import { SigninPage } from '../../support/pages/signin-page'
import { CONDUIT_USERNAME1, CONDUIT_PASSWORD1 } from '../../support/constants'

describe('Test the basic login flow from UI', () => {
  beforeEach(() => {
    HomePage.navigateToBackBase()
  })

  it('Verify user is able to signIn successfully', () => {
    HomePage.navigateToSignIn()
    SigninPage.signIn(Cypress.env(CONDUIT_USERNAME1), Cypress.env(CONDUIT_PASSWORD1))
  })
})
