export class CommonUtils {
  static validateCurrentPageUrl (url) {
    cy.url().should('contain', url)
  }

  static generateRandomNumber () {
    const max = 9999999
    const min = 999999
    const randomnumber = Math.floor(Math.random() * (max - min + 1)) + min
    return randomnumber
  }

  static loginWithDifferentUser (username, password) {
    cy.loginByAuth0Api(username, password)
    cy.reload()
  }
}
