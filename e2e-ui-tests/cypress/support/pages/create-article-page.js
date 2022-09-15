/// <reference types="cypress" />

import { TIMEOUT_MEDIUM } from '../constants'

const titleField = 'input[formcontrolname="title"]'
const aboutField = 'input[formcontrolname="description"]'
const articleContent = 'textarea[formcontrolname="body"]'
const publishbutton = 'button[type="button"]'
const tagsField = 'input[placeholder="Enter tags"]'

export class CreateArticle {
  static writeNewArticle (title, description, body) {
    cy.get(titleField).type(title)
    cy.get(aboutField).type(description)
    cy.get(articleContent).type(body)
  }

  static clickPublishArticle () {
    cy.get(publishbutton).contains('Publish Article').click()
  }

  static addTag (tags) {
    for (let i = 0; i < tags.length; i++) {
      cy.get(tagsField).type(tags[i])
        .type('{enter}')
    }
  }
}
