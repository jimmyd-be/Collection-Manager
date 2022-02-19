Cypress.Commands.add('registerUser', (email: string, password: string, userName: string) => {

  const user = {
    fullName: userName,
    email: email,
    password: password,
    confirmPassword: password
  }
  cy.request({
    url: '/api/auth/register',
    method: 'POST',
    body: user,
    failOnStatusCode: false
  })
})

Cypress.Commands.add("loginUser", (userType:string) => {

  const types = {
    admin: {
      name: 'admin@admin.com',
      password: 'AdminPassword!',
    },
    user: {
      name: 'user@user.com',
      password: 'UserPassword!'
    },
  }

  // grab the user
  const user = types[userType]

  cy.intercept('POST', 'http://localhost:4200/api/auth/login').as('loginApi')
  cy.visit('/auth/login')
  cy.get('#title').contains("Login")
  cy.get('[id=input-email]').type(user.name)
  cy.get('[id=input-password]').type(user.password)
  cy.get('.appearance-filled').click()
  cy.wait('@loginApi').then((interception) => {
    assert.equal(interception.response.statusCode, 200)
  })
})

before(() => {
  cy.registerUser('admin@admin.com', 'AdminPassword!', 'admin')
  cy.registerUser('user@user.com', 'UserPassword!', 'user')
})
