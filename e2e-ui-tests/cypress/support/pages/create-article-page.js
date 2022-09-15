/// <reference types="cypress" />

import { TIMEOUT_MEDIUM } from '../constants';

let titleField = 'input[formcontrolname="title"]';
let aboutField = 'input[formcontrolname="description"]';
let articleContent = 'textarea[formcontrolname="body"]';
let publishbutton = 'button[type="button"]';
let tagsField = 'input[placeholder="Enter tags"]';

export class CreateArticle {

  static writeNewArticle(title, description, body) {
    cy.get(titleField).type(title);
    cy.get(aboutField).type(description);
    cy.get(articleContent).type(body);
  }

  static clickPublishArticle() {
    cy.get(publishbutton).contains("Publish Article").click();
  }

  static addTag(tags) {
    for (var i=0; i < tags.length; i++) {
    cy.get(tagsField).type(tags[i])
    .type('{enter}')
    }
  }


}