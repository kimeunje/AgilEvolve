<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useVuelidate } from '@vuelidate/core'
import { required } from '@vuelidate/validators'
import boardService from '@/services/boards';

const initialState = {
  usernameOrEmailAddress: '',
}

const state = reactive({
  ...initialState,
})

const rules = {
  usernameOrEmailAddress: { required }
}

const v$ = useVuelidate(rules, state)

const props = defineProps({
  dialog: {
    type: Boolean,
    default: false
  },
  boardId: {
    type: Number
  }
})

const modalEle = ref<HTMLInputElement | null>(null);
const addMemberInput = ref<HTMLInputElement | null>(null);

const errorMessage = ref("");
const emit = defineEmits(['close', 'added'])

onMounted(() => {
  modalEle?.value?.addEventListener('shown.bs.modal', () => {
    addMemberInput?.value?.focus();
  });
});

const addMember = () => {
  v$.value.$touch();
  if (v$.value.$invalid) {
    return;
  }

  boardService.addMember(props.boardId, state.usernameOrEmailAddress).then((member) => {
    emit('added', member)
    close()
  }).catch(error => {
    errorMessage.value = error.message;
  })
}

const close = () => {
  v$.value.$reset()
  state.usernameOrEmailAddress = "";
  errorMessage.value = "";
  emit('close');
}

</script>

<template>
  <form @submit.prevent="addMember">
    <div class="modal" tabindex="-1" role="dialog" data-bs-backdrop="static" id="addMemberModal" ref="modalEle">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">멤버 초대</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body mb-2">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group mb-2">
              <input type="text" class="form-control" id="addMemberInput" v-model="state.usernameOrEmailAddress"
                placeholder="아이디 또는 이메일" maxlength="128" ref="addMemberInput">
              <div class="field-error" v-if="v$.usernameOrEmailAddress.$dirty">
                <div class="error" v-if="v$.usernameOrEmailAddress.required.$invalid">필수로 입력해주세요.</div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">생성하기</button>
            <button type="button" class="btn btn-default btn-cancel" @click="close">취소하기</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 300px;
  }
}
</style>
