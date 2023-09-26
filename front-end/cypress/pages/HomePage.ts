class HomePage {
  visit() {
    cy.visit('/')
  }

  elements = {
    getPageTitle: () => cy.get('h1.page-title')
  }
}

export default HomePage
