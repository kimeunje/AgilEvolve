import RegisterPage from '../pages/RegisterPage'

const registerPage = new RegisterPage()

describe('Register Page', () => {
  before(() => {
    cy.generateRandomUser()
  })

  it('회원가입 페이지 요소 렌더 확인', () => {
    registerPage.visit()
    registerPage.elements.getApp().should('be.visible')
    registerPage.elements.getLogoImage().should('be.visible')
    registerPage.elements.getUsernameInput().should('be.visible')
    registerPage.elements.getEmailAddressInput().should('be.visible')
    registerPage.elements.getPasswordInput().should('be.visible')
    registerPage.elements.getSubmitButton().should('be.visible')
  })

  it('유효하지 않은 데이터로 회원가입 시도', () => {
    cy.fixture('user.json').then((user) => {
      registerPage.visit()
      cy.register('t', 't@test', 'bad')

      cy.wait(3000)
      cy.url().should('eq', Cypress.config().baseUrl + '/register')
    })
  })

  // it('유효한 데이터로 회원가입 시도', () => {
  //   cy.fixture('user.json').then((user) => {
  //     registerPage.visit()
  //     cy.register(user.username, user.emailAddress, user.password)

  //     cy.wait(3000)
  //     cy.url().should('eq', Cypress.config().baseUrl + '/login')
  //   })
  // })
})
