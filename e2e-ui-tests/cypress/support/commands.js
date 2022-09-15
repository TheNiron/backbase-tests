// Custom cypress method that will handle login in API level
// Generate JWT access token with api request and store the token in local storage of the browser
Cypress.Commands.add(
  'loginByAuth0Api',
  (username, password) => {
    cy.log(`Logging in as ${username}`)
    const baseAuth = Cypress.env('base64_encode_credentials')
    cy.request({
      method: 'POST',
      url: Cypress.env('backbase_host') + '/api/users/login',
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Basic ' + baseAuth
      },
      body: {
        user: { email: '' + username + '', password: '' + password + '' }
      }
    }).then(({ body }) => {
      const token = body.user.token
      window.localStorage.setItem('jwtToken', token)
      cy.log('Stored the token in localStorage successfully!')
    })
  }
)
