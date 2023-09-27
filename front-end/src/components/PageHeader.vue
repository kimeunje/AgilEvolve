<template>
  <div class="page-header d-flex align-content-center">
    <div class="logo">
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
          <h6 class="dropdown-header">Personal Boards</h6>
          <button class="dropdown-item" type="button">vuejs.spring-boot.mariadb</button>
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
          k.eunseo
        </button>
        <div class="dropdown-menu" aria-labelledby="profileMenu">
          <button class="dropdown-item" type="button">Profile</button>
          <button class="dropdown-item" type="button">Sign Out</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.page-header {
  padding: 9px 10px 8px;
  border-bottom: 1px solid #eee;

  .logo {
    color: #444;
    height: 25px;
    width: 115px;
    margin-top: 2px;
    white-space: nowrap;

    .home-icon {
      font-size: 25px;
      vertical-align: middle;
    }

    img {
      margin-left: 5px;
      width: 80px;
      height: 20px;
      vertical-align: bottom;
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
    }

    input:focus {
      border: 1px solid #377EF6;
    }
  }
}

.dropdown-toggle {
  padding: 2px 5px !important;
}
</style>
