<script setup lang="ts">
import PageHeader from '@/components/PageHeader.vue'
import AddMemberModal from '@/modals/AddMemberModal.vue'
import draggable from 'vuedraggable';
import { nextTick, ref, watch } from "vue";
import { useEventListener } from '@vueuse/core'
import cardListsService from '@/services/card-lists'
import type { CardList } from '@/interfaces/card-list/CardLists'
import { useRoute } from 'vue-router';
import boardService from '@/services/boards'
import type { Member } from '@/interfaces/board/member';

const board = ref({ id: 0, personal: false, name: '' });
const team = ref({ name: '' });
const members = ref<Member[]>([]);
const addListForm = ref({ open: false, name: '' })
const cardLists = ref<CardList[]>([/* {id, name, cards, cardForm} */])

const pageElement = ref(null);

const route = useRoute();

const showModal = ref(false);

watch(() => route.params.boardId, async (newBoardId, oldBoardId) => {
  if (newBoardId !== oldBoardId) {
    try {
      const data = await boardService.getBoard(parseInt(newBoardId as string));
      team.value.name = data.team ? data.team.name : '';
      board.value.id = data.board.id;
      board.value.personal = data.board.personal;
      board.value.name = data.board.name;

      data.members.forEach(member => {
        members.value.push({
          id: member.userId,
          shortName: member.shortName
        })
      })
      cardLists.value = data.cardLists.map(cardList => ({
        id: cardList.id,
        name: cardList.name,
        cards: [],
        cardForm: {
          open: false,
          title: ''
        }
      }));
    } catch (error) {
      console.log(error);
    }
  }
}, { immediate: true });

useEventListener(pageElement, 'click', dismissActiveForms);

function dismissActiveForms(event: any) {
  let dismissAddCardForm = true
  let dismissAddListForm = true
  if (event.target.closest('.add-card-form') || event.target.closest('.add-card-button')) {
    dismissAddCardForm = false
  }
  if (event.target.closest('.add-list-form') || event.target.closest('.add-list-button')) {
    dismissAddListForm = false
  }
  if (dismissAddCardForm) {
    cardLists.value.forEach((cardList: any) => { cardList.cardForm.open = false })
  }
  if (dismissAddListForm) {
    addListForm.value.open = false
  }
}

const focusCardTextArea = ref<HTMLTextAreaElement | null>(null)
const focusListInput = ref<HTMLInputElement | null>(null)

const openAddMember = () => {
  // TODO 멤버 추가 로직 구현
  showModal.value = true;
}

const boardModalClose = () => {
  showModal.value = false;
}

const onMemberAdded = (member: { id: number; shortName: string; }) => {
  members.value.push(member)
}

const openAddListForm = () => {
  addListForm.value.open = true
  nextTick().then(() => {
    focusListInput.value?.focus()
  })
}

const closeAddListForm = () => {
  addListForm.value.open = false
  addListForm.value.name = ''
}

const addCardList = () => {
  if (!addListForm.value.name) {
    return
  }

  const cardList = {
    boardId: board.value.id,
    name: addListForm.value.name,
    position: cardLists.value.length + 1
  }

  cardListsService.add(cardList).then((savedCardList) => {
    cardLists.value.push({
      id: savedCardList.id,
      name: savedCardList.name,
      cards: [],
      cardForm: {
        open: false,
        title: ''
      }
    })
    closeAddListForm()
  }).catch(error => {
    console.log(error);
  })
}

const openAddCardForm = (cardList: any) => {
  // 다른 카드 추가 창 닫기
  cardLists.value.forEach((cardList: any) => { cardList.cardForm.open = false })

  cardList.cardForm.open = true

  nextTick().then(() => {
    // TODO 카드 추가
    focusCardForm()
  })
}

const focusCardForm = () => {
  nextTick().then(() => {
    focusCardTextArea.value?.focus()
  })
}
const closeAddCardForm = (cardList: any) => {
  cardList.cardForm.open = false
  cardList.cardForm.name = ''
}

const addCard = () => {
  // TODO 카드 추가 로직
}
</script>


<template>
  <div class="page" ref="pageElement">
    <PageHeader />
    <div class="page-body">
      <div class="board-wrapper">
        <div class="board">
          <div class="board-header">
            <div class="board-name board-header-item">{{ board.name }}</div>
            <div class="board-header-divider"></div>
            <div class="team-name board-header-item">
              <span v-if="!board.personal">{{ team.name }}</span>
              <span v-if="board.personal">개인</span>
            </div>
            <div class="board-header-divider"></div>
            <div class="board-members board-header-item">
              <div class="member" v-for="member in members" :key="member.id">
                <span>{{ member.shortName }}</span>
              </div>
              <div class="member add-member-toggle" @click="openAddMember()">
                <span><font-awesome-icon :icon="['fas', 'user-plus']" class="home-icon" /></span>
              </div>
            </div>
          </div>
          <div class="board-body">
            <div class="list-container">
              <draggable style="display: flex;" v-model="cardLists" handle=".list-header" :animation=0
                :scrollSensitivity=100 :touchStartThreshold=20 itemKey="name">
                <template #item="{ element }">
                  <div class="draggable-wrapper">
                    <div class="list-wrapper">
                      <div class="list">
                        <div class="list-header">{{ element.name }}</div>
                        <draggable v-model="element.cards" class="cards" group="cards" draggable=".card-item"
                          ghostClass="ghost-card" :animation=0 :scrollSensitivity=100 :touchStartThreshold=20
                          itemKey="name">
                          <template #item="{ element }">
                            <div class="card-item">
                              <div class="card-title">{{ element.title }}</div>
                            </div>
                          </template>
                        </draggable>
                        <div class="add-card-button" @click="openAddCardForm(element)" v-show="!element.cardForm.open">+
                          카드
                          추가하기
                        </div>
                        <div class="add-card-form-wrapper" v-if="element.cardForm.open">
                          <form @submit.prevent="addCard()" class="add-card-form" autocomplete="off">
                            <div class="form-group">
                              <textarea ref="focusCardTextArea" class="form-control" v-model="element.cardForm.title"
                                v-bind:id="'cardTitle' + element.id" @keydown.enter.prevent="addCard()"
                                placeholder="카드 제목을 입력해주세요."></textarea>
                            </div>
                            <button type="submit" class="btn btn-sm btn-primary">추가</button>
                            <button type="button" class="btn btn-sm btn-link btn-cancel"
                              @click="closeAddCardForm(element)">취소</button>
                          </form>
                        </div>
                      </div>
                    </div>

                  </div>

                </template>
              </draggable>
              <div class="list-wrapper add-list">
                <div class="add-list-button" @click="openAddListForm()" v-show="!addListForm.open">+ 리스트 추가하기</div>
                <form @submit.prevent="addCardList()" v-show="addListForm.open" class="add-list-form"
                  autocomplete="off">
                  <div class="form-group">
                    <input ref="focusListInput" type="text" class="form-control" v-model="addListForm.name"
                      id="cardListName" placeholder="이름을 작성해주세요." />
                  </div>
                  <button type="submit" class="btn btn-sm btn-primary">추가</button>
                  <button type="button" class="btn btn-sm btn-link btn-cancel" @click="closeAddListForm()">취소</button>
                </form>
              </div>
            </div>

          </div>
        </div>
      </div>


    </div>
    <AddMemberModal :boardId="board.id" :dialog="showModal" @close="boardModalClose" @added="onMemberAdded" />
  </div>

</template>

<style lang="scss" scoped>
.page-body {
  flex-grow: 1;
  position: relative;
  overflow-y: auto;

  .board-wrapper {
    position: absolute;
    margin: 0;

    .board {
      height: 100%;
      display: flex;
      flex-direction: column;

      .board-header {
        position: relative;
        height: auto;
        overflow: hidden;
        flex: none;
        padding: 8px 4px 8px 8px;

        .board-header-divider {
          float: left;
          border-left: 1px solid #ddd;
          height: 16px;
          margin: 8px 10px;
        }

        .board-header-item {
          float: left;
          height: 32px;
          line-height: 32px;
          margin: 0 4px 0 0;
        }

        .board-name {
          font-size: 18px;
          line-height: 32px;
          padding-left: 4px;
          text-decoration: none;
        }

        .board-members {
          .member {
            display: block;
            float: left;
            height: 30px;
            width: 30px;
            margin: 0 0 0 2px;
            border-radius: 50%;
            background-color: #377EF6;
            position: relative;

            span {
              line-height: 30px;
              display: block;
              text-align: center;
              color: #fff;
            }
          }

          .add-member-toggle {
            margin-left: 5px;
            background-color: #eee;
            cursor: pointer;

            svg {
              font-size: 10px;
              position: absolute;
              top: 9px;
              left: 9px;
              color: #000;
            }
          }

          .add-member-toggle:hover {
            background-color: #666;

            svg {
              color: #fff;
            }
          }
        }
      }

      .board-body {
        position: relative;
        flex-grow: 1;

        .list-container {
          position: absolute;
          left: 8px;
          overflow-x: auto;
          overflow-y: hidden;
          white-space: nowrap;
          margin-bottom: 6px;
          padding-bottom: 6px;
          display: flex;

          .list-wrapper {
            margin: 0 4px;
            width: 272px;
            height: 100%;
            vertical-align: top;
            white-space: nowrap;
            box-sizing: border-box;
            display: inline-block;

            .list {
              background: #eee;
              border-radius: 3px;
              box-sizing: border-box;
              display: flex;
              flex-direction: column;
              max-height: 100%;
              white-space: normal;
              position: relative;

              .list-header {
                padding: .55rem .75rem;
                font-weight: 600;
                cursor: pointer;
              }

              .add-card-button {
                padding: 8px 10px;
                color: #888;
                cursor: pointer;
                border-bottom-left-radius: 3px;
                border-bottom-right-radius: 3px;
              }

              .add-card-button:hover {
                background: #dfdfdf;
                color: #333;
              }

              .add-card-form-wrapper {
                padding: 0 8px 8px;

                .form-group {
                  margin-bottom: 5px;

                  textarea {
                    resize: none;
                    padding: 0.30rem 0.50rem;
                    box-shadow: none;
                  }
                }
              }

              .cards {
                overflow-y: auto;
                min-height: 1px;

                .card-item {
                  overflow: hidden;
                  background: #fff;
                  padding: 5px 8px;
                  border-radius: 4px;
                  margin: 0 8px 8px;
                  box-shadow: 0 1px 0 #ccc;
                  cursor: pointer;

                  .card-title {
                    margin: 0;
                  }
                }

                .ghost-card {
                  opacity: 0.5;
                  background: #c8ebfb;
                }
              }
            }
          }

          .list-wrapper.add-list {
            background: #f4f4f4;
            border-radius: 3px;
            box-sizing: border-box;
            color: #888;
            margin-right: 8px;

            .add-list-button {
              padding: 8px 10px;
            }

            .add-list-button:hover {
              background: #ddd;
              cursor: pointer;
              border-radius: 3px;
              color: #333;
            }

            form {
              padding: 5px;

              .form-group {
                margin-bottom: 5px;

                .form-control {
                  height: calc(1.80rem + 2px);
                  padding: .375rem .3rem;
                }
              }
            }
          }
        }
      }

    }

  }
}
</style>