<template>
  <form @submit.prevent="saveBoard">
    <div class="modal" tabindex="-1" role="dialog" data-bs-backdrop="static" id="createBoardModal" ref="modalEle">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Create Board</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group">
              <input type="text" class="form-control" id="boardNameInput" v-model="board.name" placeholder="Board name"
                maxlength="128" ref="boardNameInput">
              <div class="field-error" v-if="v$.board.name.$dirty">
                <div class="error" v-if="v$.board.name.required.$invalid">Name is required</div>
              </div>
            </div>
            <div class="form-group">
              <textarea class="form-control" v-model="board.description"
                placeholder="Add board description here"></textarea>
              <div class="field-error" v-if="v$.board.description.$dirty">
                <div class="error" v-if="v$.board.description.required.$invalid">Description is required</div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">Create</button>
            <button type="button" class="btn btn-default btn-cancel" @click="close">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useBoardUserStore } from '@/stores/useBoardUserStore';
import { required } from '@vuelidate/validators';
import useVuelidate from '@vuelidate/core';

import boardService from '@/services/boards';


const props = defineProps({ teamId: Number });

const modalEle = ref<HTMLInputElement | null>(null);
const boardNameInput = ref<HTMLInputElement | null>(null);
const errorMessage = ref<string>('');
const board = ref({ name: '', description: '' });
const teamId = ref(props.teamId);

const emit = defineEmits(['close', 'created'])
const boardUserStore = useBoardUserStore()

const validations = { board: { name: { required }, description: { required } } };
const v$ = useVuelidate(validations, { board });

watch(() => props.teamId, (newValue) => {
  teamId.value = newValue;
});

onMounted(() => {
  modalEle?.value?.addEventListener('shown.bs.modal', () => {
    boardNameInput?.value?.focus();
  });
});

const saveBoard = () => {
  v$.value.$touch();
  if (v$.value.$invalid) {
    return;
  }

  const newBoard = {
    teamId: teamId.value,
    name: board.value.name,
    description: board.value.description
  };

  boardService.create(newBoard)
    .then((createdBoard: any) => {
      boardUserStore.addBoard(createdBoard)
      emit('created', createdBoard.id);
      close();
    })
    .catch((error) => {
      errorMessage.value = error.message;
    });
};

const close = () => {
  v$.value.$reset();
  board.value.name = '';
  board.value.description = '';
  errorMessage.value = '';
  emit('close');
};
</script>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 400px;
  }
}
</style>
