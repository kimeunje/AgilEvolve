/// <reference types="cypress" />
import Chance from 'chance'

const chance = new Chance()

/**
 * 로그인 커맨드
 */
Cypress.Commands.add('login', (username, password) => {
  cy.get('#username').type(username)
  cy.get('#password').type(password)
  cy.get('button[type=submit]').click()
})

/**
 * 회원가입 커맨드
 */
Cypress.Commands.add('register', (username, emailAddress, password) => {
  cy.get('#username').type(username)
  cy.get('#emailAddress').type(emailAddress)
  cy.get('#password').type(password)
  cy.get('button[type=submit]').click()
})

/**
 * 회원가입 데이터 생성 커맨드
 */
Cypress.Commands.add('generateRandomUser', () => {
  const user = {
    firstName: chance.first(),
    lastName: chance.last(),
    username: chance.first().toLowerCase() + chance.integer({ min: 0, max: 1000000 }),
    emailAddress: chance.email(),
    password: 'test1234'
  }

  const filePath = 'cypress/fixtures/user.json'

  cy.writeFile(filePath, user, { encoding: 'utf-8' }).then(() => {
    return cy.wrap(user)
  })
})

declare global {
  namespace Cypress {
    interface Chainable {
      login(username: string, password: string): Chainable<void>
      register(username: string, email: string, password: string): Chainable<void>
      generateRandomUser(): Chainable<User>
      dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
      // visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
    }
    interface User {
      firstName: string
      lastName: string
      username: string
      emailAddress: string
      password: string
    }
  }
}

export {}
