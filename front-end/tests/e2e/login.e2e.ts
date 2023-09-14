describe('login test', function () {
  before((browser) => {
    browser.init()
  })
  
  it('로그인 페이지에 "TaskAgile"를 렌더링 해야 합니다.', function () {
    browser.url(browser.launch_url + '/login')
      .waitForElementVisible('#app', 5000)
      .assert.textContains('h1', 'TaskAgile')
      .end()
  })

  after((browser) => browser.end())
})
