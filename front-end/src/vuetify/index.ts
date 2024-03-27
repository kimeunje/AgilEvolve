// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi-svg'
import { mdiAccount, mdiMenuDownOutline, mdiMenuDown } from '@mdi/js';

const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi',
    aliases: {
      ...aliases,
      account: mdiAccount,
      menuDownOutline: mdiMenuDownOutline,
      menuDown: mdiMenuDown,
    },
    sets: {
      mdi,
    },
  },
})

export { vuetify }