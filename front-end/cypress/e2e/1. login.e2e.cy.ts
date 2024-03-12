import LoginPage from '../pages/LoginPage'
import HomePage from '../pages/HomePage'

const loginPage = new LoginPage()
const homePage = new HomePage()

describe('Login Page', () => {
  it('로그인 페이지 요소 렌더 확인', () => {
    loginPage.visit()
    loginPage.elements.getApp().should('be.visible')
    loginPage.elements.getLogoImage().should('be.visible')
    loginPage.elements.getUsernameInput().should('be.visible')
    loginPage.elements.getPasswordInput().should('be.visible')
    loginPage.elements.getSubmitButton().should('be.visible')
  })

  it('유효하지 않은 자격으로 로그인 시도', () => {
    loginPage.visit()
    cy.login('not-exist', 'badPassword')

    cy.wait(500)
    loginPage.elements.getFormError().should('be.visible')
    loginPage.elements.getFormError().should('have.text', '유효하지 않은 자격입니다.')
  })

  // it('유효한 아이디로 로그인 시도', () => {
  //   cy.fixture('user.json').then((user) => {
  //     loginPage.visit()
  //     cy.login(user.username, user.password)

  //     cy.wait(500)
  //     homePage.elements.getLogoImage().should('be.visible')
  //   })
  // })

  // it('유효한 이메일로 로그인 시도', () => {
  //   cy.fixture('user.json').then((user) => {
  //     loginPage.visit()
  //     cy.login(user.emailAddress, user.password)

  //     cy.wait(500)
  //     homePage.elements.getLogoImage().should('be.visible')
  //   })
  // })
})
