describe('login test', function () {
  before((browser) => {
    browser.init()
  })

  it('로그인 페이지에 로고를 렌더링 해야 합니다.', function () {
    browser.url(browser.launch_url + '/login')
      .waitForElementVisible('#app', 5000)
      .assert.attributeContains('.logo', 'src', '/static/images/logo.png')
      .end()
  })

  after((browser) => browser.end())
})
