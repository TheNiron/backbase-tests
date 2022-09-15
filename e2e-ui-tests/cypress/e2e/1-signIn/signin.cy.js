/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page';
import { SigninPage } from '../../support/pages/signin-page';

describe('Test the basic login flow from UI', () => {
  
    beforeEach(() => {
    HomePage.navigateToBackBase();
  });

  it('Verify user is able to signIn successfully', () => {
      let userName = "niron@gmail.com";
      let password = "rasanjana29";
    HomePage.navigateToSignIn();
    SigninPage.signIn(userName,password);
  });

});