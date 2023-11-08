<template>
  <div class="page-header d-flex align-content-center">
    <div class="logo" @click="goHome()">
      <font-awesome-icon :icon="['fas', 'house']" class="home-icon" />
      <img src="/static/images/logo.png">
    </div>
    <div class="boards-menu-toggle">
      <div class="dropdown">
        <button class="btn dropdown-toggle" type="button" id="boardsMenu" data-bs-toggle="dropdown" aria-haspopup="true"
          aria-expanded="false">
          Boards
        </button>
        <div class="dropdown-menu" aria-labelledby="boardsMenu">
          <div v-show="!hasBoards" class="dropdown-item no-board">No boards</div>
          <div v-show="hasBoards">
            <h6 class="dropdown-header" v-show="personalBoards.length">Personal Boards</h6>
            <button v-for="board in personalBoards" v-bind:key="board.id" @click="openBoard(board)"
              class="dropdown-item personal-board" type="button">{{ board.name }}</button>
            <div v-for="team in teamBoards" v-bind:key="'t' + team.id">
              <h6 class="dropdown-header">{{ team.name }}</h6>
              <button v-for="board in team.boards" v-bind:key="board.id" @click="openBoard(board)" class="dropdown-item team-board"
                type="button">{{ board.name }}</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="search-box flex-fill">
      <div class="search-wrapper">
        <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="search-icon" />
        <input type="text" placeholder="Search" class="form-control form-control-sm" />
      </div>
    </div>
    <div class="profile-menu-toggle">
      <div class="dropdown">
        <button class="btn dropdown-toggle" type="button" id="profileMenu" data-bs-toggle="dropdown" aria-haspopup="true"
          aria-expanded="false">
          {{ getUser.name }}
        </button>
        <div class="dropdown-menu" aria-labelledby="profileMenu">
          <button class="dropdown-item" type="button">Profile</button>
          <button class="dropdown-item" type="button">Sign Out</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';

import { useBoardUserStore } from '@/stores/useBoardUserStore'

const { getUser, hasBoards, personalBoards, teamBoards } = storeToRefs(useBoardUserStore())
const { getMyData } = useBoardUserStore()
const router = useRouter()

onMounted(() => {
  getMyData()
});

const goHome = () => {
  router.push({ name: 'home' })
}

const openBoard = (board: { id: number }) => {
  router.push({ name: 'board', params: { boardId: board.id } })
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
