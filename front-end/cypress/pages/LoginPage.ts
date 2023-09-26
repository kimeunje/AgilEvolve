class LoginPage {
  visit() {
    cy.visit('/login')
  }

  elements = {
    getApp: () => cy.get('#app', { timeout: 500 }),

    getLogoImage: () => cy.get('img.logo'),

    getUsernameInput: () => cy.get('#username'),

    getPasswordInput: () => cy.get('#password'),

    getSubmitButton: () => cy.get('button[type=submit]'),

    getFormError: () => cy.get('.failed')
  }
}

export default LoginPage
