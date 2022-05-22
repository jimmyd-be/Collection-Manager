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

  it('Create collection with Books', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Books').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with Comics', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Comics').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with Diskcs', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Disks').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with Games', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Games').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with Magazines', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Magazines').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })

  it('Create collection with Movies', () => {
    cy.intercept('POST', '/api/collection/add').as('collectionApi')

    cy.visit('/pages/collection/add')
    cy.get('.p-card-title').contains("Add new Collections")
    cy.get('[id=name]').type(randomText(5))
    cy.get('[id=type]').click()
    cy.get('[id=type]').contains('Movies').click({ force: true })
    cy.get('[type=submit]').click()
    cy.wait('@collectionApi').then((interception) => {
      assert.equal(interception.response.statusCode, 200)
    })
  })


})
