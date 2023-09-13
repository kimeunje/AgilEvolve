describe('login test', function () {
  before((browser) => {
    browser.init()
  })
  
  it('올바른 콘텐츠를 렌더링해야 합니다.', function () {
    browser.url(browser.launch_url + '/login')
      .waitForElementVisible('#app', 5000)
      .assert.textContains('h1', 'TaskAgile')
      .end()
  })

  after((browser) => browser.end())
})
