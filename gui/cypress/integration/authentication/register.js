describe('Authentication', () => {
  it('Visits the registration page and register a new user', () => {
    cy.intercept('POST', 'http://localhost:8080/api/auth/register', {
      statusCode: 200,
    }).as('registerApi')

    //
    cy.visit('/auth/login')
    cy.get('a[href*="/auth/register"]').click()
    cy.get('#title').contains("Register")
    cy.get('[id=input-name]').type("Jeff Nijs")
    cy.get('[id=input-email]').type("test@test.be")
    cy.get('[id=input-password]').type("SecurePassword2022!")
    cy.get('[id=input-re-password]').type("SecurePassword2022!")
    cy.get('.custom-checkbox').click()
    cy.get('.appearance-filled').click()
    cy.wait('@registerApi')
    cy.url().should('eq', 'http://localhost:4200/auth/login')
  })

  it('Visits the registration page and register a faulty user', () => {
    cy.intercept('POST', 'http://localhost:8080/api/auth/register', {
      statusCode: 500,
    }).as('registerApi')

    //
    cy.visit('/auth/login')
    cy.get('a[href*="/auth/register"]').click()
    cy.get('#title').contains("Register")
    cy.get('[id=input-name]').type("Jeff Nijs")
    cy.get('[id=input-email]').type("test@test.be")
    cy.get('[id=input-password]').type("SecurePassword2022!")
    cy.get('[id=input-re-password]').type("OtherPassword!")
    cy.get('.custom-checkbox').click()
    cy.get('.appearance-filled').click()
    cy.wait('@registerApi')
    cy.get('.outline-danger').contains('Oh snap!')
  })
})
