
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
    cy.get('#title').contains("Register")
    cy.get('[id=input-name]').type(userName)
    cy.get('[id=input-email]').type(userMail)
    cy.get('[id=input-password]').type(userPasword)
    cy.get('[id=input-re-password]').type(userPasword)
    cy.get('.custom-checkbox').click()
    cy.get('.appearance-filled').click()
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

  it('Visits the registration page and register a faulty user', () => {
    cy.intercept('POST', 'http://localhost:4200/api/auth/register').as('registerApi')
    cy.visit('/auth/register')
    cy.get('#title').contains("Register")
    cy.get('[id=input-name]').type("Jeff Nijs")
    cy.get('[id=input-email]').type("test@test.be")
    cy.get('[id=input-password]').type("SecurePassword2022!")
    cy.get('[id=input-re-password]').type("OtherPassword!")
    cy.get('.custom-checkbox').click()
    cy.get('.appearance-filled').click()
    cy.wait('@registerApi').then((interception) => {
      assert.equal(interception.response.statusCode, 400)
    })
    cy.get('.outline-danger').contains('Oh snap!')
  })
})
