
function randomText(length) {
  var result           = '';
  var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  var charactersLength = characters.length;
  for ( var i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

var userMail = randomText(5) + '@gmail.com'
var userPasword = randomText(8)
var userName = randomText(10)

describe('Authentication', () => {
  it('Visits the registration page and register a new user', () => {
    cy.intercept('POST', 'http://localhost:4200/api/auth/register').as('registerApi')

    cy.visit('/auth/register')
    cy.get('#title').contains("Register a new user")
    cy.get('[id=fullName]').type(userName)
    cy.get('[id=email]').type(userMail)
    cy.get('[id=password]').type(userPasword)
    cy.get('[id=confirmPassword]').type(userPasword)
    cy.get('[type=submit]').click()
    cy.wait('@registerApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
    cy.wait(500)
    cy.url().should('eq', 'http://localhost:4200/auth/login')
  })

  it('Login with admin account', () => {

    cy.loginUser('admin')
    cy.wait(500)
    cy.url().should('eq', 'http://localhost:4200/pages/dashboard')
  })

  it('Login with faulty user', () => {

    cy.intercept('POST', 'http://localhost:4200/api/auth/login').as('loginApi')
    cy.visit('/auth/login')
    cy.get('[id=title').contains("Welcome Back")
    cy.get('[id=email]').type('user@user.com')
    cy.get('[id=password]').type("WrongPassword")
    cy.get('[type=submit]').click()
    cy.wait('@loginApi').then((interception) => {
      assert.equal(interception.response.statusCode, 401)
    })
    cy.get('#p-message-detail').contains('Credentials are not valid')
  })

  it('Visits the registration page and register a faulty user', () => {
    cy.intercept('POST', 'http://localhost:4200/api/auth/register').as('registerApi')
    cy.visit('/auth/register')
    cy.get('#title').contains("Register a new user")
    cy.get('[id=fullName]').type("Jeff Nijs")
    cy.get('[id=email]').type("test@test.be")
    cy.get('[id=password]').type("SecurePassword2022!")
    cy.get('[id=confirmPassword]').type("OtherPassword!")
    cy.get('[type=submit]').click()
    cy.wait('@registerApi').then((interception) => {
      assert.equal(interception.response.statusCode, 400)
    })
    cy.get('#p-message-detail').contains('Something went wrong')
  })
})
