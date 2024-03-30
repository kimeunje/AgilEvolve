// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi-svg'
import { mdiAccount, mdiMenuDownOutline, mdiMenuDown, mdiClose } from '@mdi/js';

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
      close: mdiClose,
    },
    sets: {
      mdi,
    },
  },
})

export { vuetify }