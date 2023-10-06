<template>
  <form @submit.prevent="saveTeam">
    <div class="modal" tabindex="-1" role="dialog" data-bs-backdrop="static" id="createTeamModal" ref="modalEle">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Create Team</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group">
              <input type="text" class="form-control" id="teamNameInput" v-model="team.name" placeholder="Team name"
                maxlength="128" ref="teamNameInput">
              <div class="field-error" v-if="v$.team.name.$dirty">
                <div class="error" v-if="v$.team.name.required.$invalid">Name is required</div>
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
import { onMounted, ref } from 'vue';
import { useBoardUserStore } from '@/stores/useBoardUserStore';
import { required } from '@vuelidate/validators';
import useVuelidate from '@vuelidate/core';

import teamService from '@/services/teams';


const modalEle = ref<HTMLInputElement | null>(null);
const teamNameInput = ref<HTMLInputElement | null>(null);
const errorMessage = ref<string>('');
const team = ref({ name: '' });

const emit = defineEmits(['close'])
const boardUserStore = useBoardUserStore()

const validations = { team: { name: { required } } };
const v$ = useVuelidate(validations, { team });

onMounted(() => {
  modalEle?.value?.addEventListener('shown.bs.modal', () => {
    teamNameInput?.value?.focus();
  });
});

const saveTeam = () => {
  v$.value.$touch();
  if (v$.value.$invalid) {
    return;
  }

  const newBoard = {
    name: team.value.name,
  };

  teamService.create(newBoard)
    .then((createdTeam: any) => {
      boardUserStore.addTeam(createdTeam)
      close();
    })
    .catch((error) => {
      errorMessage.value = error.message;
    });
};

const close = () => {
  v$.value.$reset();
  team.value.name = '';
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
