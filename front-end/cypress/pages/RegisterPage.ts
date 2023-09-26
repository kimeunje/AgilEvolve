class RegisterPage {
  visit() {
    cy.visit('/register')
  }

  elements = {
    getApp: () => cy.get('#app', { timeout: 500 }),

    getLogoImage: () => cy.get('img.logo'),

    getUsernameInput: () => cy.get('#username'),

    getEmailAddressInput: () => cy.get('#emailAddress'),

    getPasswordInput: () => cy.get('#password'),

    getSubmitButton: () => cy.get('button[type=submit]'),

    getFormError: () => cy.get('.failed')
  }
}

export default RegisterPage
