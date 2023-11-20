<template>
  <footer class="footer pt-4 my-md-5 pt-md-5 border-top">
    <div class="row">
      <div class="col-6 col-md">
        <span class="copyright">&copy; 2023 TaskAgile.com</span>
        <ul class="footer-links list-unstyled text-small list-inline float-md-end">
          <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">{{ $t('pageFooter.about') }}</a></li>
          <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">{{ $t("pageFooter.termOfService") }}</a>
          </li>
          <li class="mb-1 list-inline-item"><a class="link-secondary" href="#">{{ $t("pageFooter.privacyPolicy") }}</a>
          </li>
          <li class="mb-1 list-inline-item"><a class="link-secondary"
              href="https://github.com/kimeunje/vuejs.spring-boot.mariadb">GitHub</a></li>
        </ul>
      </div>
    </div>
  </footer>
</template>

<style lang="scss" scoped>
.footer {
  width: 100%;
  font-size: 13px;
  color: #666;
  line-height: 40px;
  border-top: 1px solid #ddd;
  margin-top: 50px;

  .list-inline-item {
    margin-right: 10px;
  }

  a {
    color: #666;
  }
}</style>
