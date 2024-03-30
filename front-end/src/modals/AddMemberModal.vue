<script setup lang="ts">
import { reactive, ref } from 'vue';
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

const errorMessage = ref("");
const emit = defineEmits(['close', 'added'])

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
  <div class="pa-4 text-center">
    <v-dialog v-model="props.dialog" activator="target" max-width="500" persistent>
      <v-form @submit.prevent="addMember">
        <v-card rounded="lg">
          <v-card-title class="d-flex justify-space-between align-center">
            <div class="text-h5 text-medium-emphasis ps-2">
              멤버 초대
            </div>

            <v-btn icon="$close" variant="text" @click="close"></v-btn>
          </v-card-title>


          <v-card-text class="mx-auto">
            <div class="text-medium mb-5">
              추가할 멤버의 아이디 또는 이메일을 입력해주세요.
            </div>
            <v-sheet class="mx-auto" width="300">
              <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
              <v-text-field v-model="state.usernameOrEmailAddress" autocomplete="text" label="Username or email address"
                hide-details required></v-text-field>
              <div class="field-error" v-if="v$.usernameOrEmailAddress.$dirty">
                <div class="error" v-if="v$.usernameOrEmailAddress.required.$invalid">필수로 입력해주세요.</div>
              </div>
            </v-sheet>

          </v-card-text>

          <v-divider class="mt-2"></v-divider>

          <v-card-actions class="my-2 d-flex justify-end">
            <v-btn class="text-none" color="primary" rounded="xl" text="Add" variant="flat" type="submit"></v-btn>
            <v-btn class="text-none" rounded="xl" text="Cancel" @click="close()"></v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>
  </div>
</template>