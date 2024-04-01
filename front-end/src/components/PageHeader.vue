<template>
  <div class="page-header d-flex align-content-center">
    <div class="logo" @click="goHome()">
      <font-awesome-icon :icon="['fas', 'house']" class="home-icon" />
      <img src="/images/logo.png">
    </div>
    <div class="boards-menu-toggle">
      <v-menu class="text-body-2">
        <template v-slot:activator="{ props }">
          <v-btn variant="text" append-icon="$menuDown" v-bind="props">
            {{ t('header.boardsMenu.label') }}
          </v-btn>
        </template>
        <v-card class="px-2 rounded-lg">
          <v-list density="compact" v-show="!hasBoards">{{ t('header.boardsMenu.noBoard') }}</v-list>
          <v-list density="compact" v-show="hasBoards">
            <v-list-subheader v-show="personalBoards.length">
              {{ t('header.boardsMenu.personalBoards') }}
            </v-list-subheader>
            <v-list-item v-for="board in personalBoards" v-bind:key="board.id" @click="openBoard(board)" type="button">
              {{ board.name }}
            </v-list-item>
          </v-list>
          <v-list density="compact" v-show="hasBoards" v-for="team in teamBoards" v-bind:key="'t' + team.id">
            <v-list-subheader>{{ team.name }}</v-list-subheader>
            <v-list-item density="compact" v-show="team.boards.length" v-for="board in team.boards"
              v-bind:key="board.id" @click="openBoard(board)" type="button">
              {{ board.name }}
            </v-list-item>
            <v-list-item :disabled="true" v-show="!team.boards.length">
              {{ "<비어 있음>" }}
            </v-list-item>
          </v-list>
        </v-card>
      </v-menu>
    </div>

    <div class="search-box flex-fill">
      <div class="search-wrapper">
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="search-icon" />
        <input type="text" v-bind:placeholder="t('header.search')" class="form-control form-control-sm" />
      </div>
    </div>

    <v-menu class="text-body-2">
      <template v-slot:activator="{ props }">
        <v-btn variant="text" append-icon="$menuDown" id="profileMenu" v-bind="props">
          {{ getUser.name }}
        </v-btn>
      </template>
      <v-list density="compact">
        <v-list-item>{{ t('header.profile') }}</v-list-item>
        <v-list-item color="#033" type="button" @click="signOut()">{{ t('header.signOut') }}</v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useBoardUserStore } from '@/stores/useBoardUserStore'
import { useI18n } from 'vue-i18n';
import meService from '@/services/me'
import notify from '@/utils/notify'

const { getUser, hasBoards, personalBoards, teamBoards } = storeToRefs(useBoardUserStore())
const { getMyData } = useBoardUserStore()
const router = useRouter()

const { t } = useI18n();

onMounted(() => {
  getMyData()
});

const goHome = () => {
  router.push({ name: 'home' })
}

const openBoard = (board: { id: number }) => {
  router.push({ name: 'board', params: { boardId: board.id } })
}

const signOut = () => {
  meService.signOut().then(() => {
    router.push({ name: 'login' })
  }).catch(error => {
    notify.error(error.message)
  })

}

</script>

<style lang="scss" scoped>
.page-header {
  padding: 9px 10px 8px;
  border-bottom: 1px solid #eee;

  .logo {
    color: #444;
    height: 25px;
    width: 115px;
    margin-top: 2px;
    cursor: pointer;
    white-space: nowrap;

    .home-icon {
      font-size: 20px;
      vertical-align: middle;
    }

    img {
      margin-left: 5px;
      margin-top: 6px;
      width: 80px;
      height: 20px;
    }
  }

  .boards-menu-toggle {
    padding-left: 20px;
    width: 100px;
  }

  .profile-menu-toggle {
    width: 215px;
    text-align: right;
  }

  .search-box {
    .search-wrapper {
      width: 400px;
      margin: 0 auto;
      position: relative;
    }

    .search-icon {
      position: absolute;
      top: 8px;
      left: 8px;
      color: #666;
    }

    input {
      padding-left: 30px;
      height: calc(1.8125rem + 5px);
      font-size: 1rem;
      border: 1px solid #eee;
      border-radius: 5px;
    }
  }
}

.dropdown-toggle {
  padding: 2px 5px !important;
}
</style>
