class HomePage {
  visit() {
    cy.visit('/')
  }

  elements = {
    getLogoImage: () => cy.get('.logo img')
  }
}

export default HomePage
