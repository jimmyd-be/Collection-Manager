function randomText(length) {
  var result = '';
  var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  var charactersLength = characters.length;
  for (var i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }
  return result;
}

describe('Collection', () => {

  beforeEach(() => {
    cy.loginUser('user')
  })

  it('Create collection with type 1', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-0').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with type 2', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-1').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with type 3', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-2').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with type 4', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-3').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with type 5', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-4').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with type 6', () => {
    cy.intercept('POST', 'http://localhost:4200/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('nb-card-header').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('#nb-option-5').click()
    cy.get('nb-card-footer > .status-primary').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })


})
