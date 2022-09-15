export class CommonUtils {

  static validateCurrentPageUrl(url) {
    cy.url().should('contain', url)
  }

  static generateRandomNumber(){
    var max = 9999999;
    var min = 999999;
    var randomnumber = Math.floor(Math.random() * (max - min + 1)) + min;
  return randomnumber;
  }

}