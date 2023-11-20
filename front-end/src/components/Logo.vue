<template>
  <div class="logo-wrapper">
    <img class="logo" src="/images/logo.png" alt="로고">
    <div class="tagline">{{ t('logo.tagLine') }}</div>
  </div>
</template>

<script setup lang="ts">
import { useI18n } from 'vue-i18n';

const { t } = useI18n()
</script>

<style lang="scss" scoped>
.logo-wrapper {
  text-align: center;
  margin-bottom: 40px;

  .tagline {
    line-height: 180%;
    color: #666;
  }

  .logo {
    max-width: 150px;
    margin: 0 auto;
  }
}
</style>
