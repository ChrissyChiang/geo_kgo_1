import { $axios } from './axios'
import * as api from './api'
import * as utils from './utils'

const $api = { ...api, utils }

export { $axios, $api }
