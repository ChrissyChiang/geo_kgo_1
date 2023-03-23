import { auth } from './auth'
import { notify } from './notify'
import { vuexStore } from './vuex-store'
import { base } from './base'

export const page = {
  mixins: [auth, notify, vuexStore, base]
}
