/// <reference types="cypress" />

import { HomePage } from '../../support/pages/home-page';
import {TIMEOUT_MEDIUM } from '../constants';

let titleField = '[class="container"]>h1';
let deleteButton = 'button[class="btn btn-sm btn-outline-danger"]';
let articlePreview = 'a[class="preview-link"]>p';
let articleBody = 'p';
let errorText = '[class="error-message"]>';
let tagList = '[class="tag-list"]>li';

export class Article{

  static verifyArticlePublishedSuccessfully(articleTitle){
    this.verifyPublishedArticleTitle(articleTitle);
    cy.get('button').contains('Delete Article').should('be.visible');
  }

  static verifyPublishedArticleTitle(articleTitle){
 cy.get(titleField).contains(articleTitle).should('be.visible');
  }

  static clickDeleteArticle(){
    cy.get(deleteButton).contains('Delete Article').click();
  }
  
 static deleteArticleByTitle(title){
    HomePage.navigateToHomePage();
    HomePage.navigateToGlobalFeed();
    HomePage.openArticleByATitle(title);
    Article.clickDeleteArticle(title);
  }

  static verifyArticleAbout(about){
    cy.get(articlePreview).contains(about).should('be.visible');  
  }

  static verifyArticleBodyContent(body){
    // cy.log(cy.get(articleBody));
    cy.get(articleBody).then(function($elem) {
      cy.log($elem.text())
 })
    cy.get(articleBody).contains(body).should('be.visible');
  }

  static verifyErrorMessage(message){
    cy.get(errorText).contains(message).should('be.visible');  
  }

  static verifyArticleContainsTags(tags){
    for (var i=0; i < tags.length; i++) {
      cy.get(tagList).contains(tags[i]).should('be.visible');
      }
  }

  static assertNumberOfTags(noOftags){
    cy.get(tagList).should('have.length', noOftags)
  }


}