<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <validation-observer ref="observer">
            <div class="row">
              <div class="col-xs-6 form-group col-md-12">
                <label for="input_event" class="col-sm-2 control-label">
                  結案狀態
                </label>
                <div class="col-sm-8" style="padding-top:7px">
                  <select
                    v-model="caseStatusOptions.selectedVal"
                    :options="caseStatusOptions.options"
                    class="form-control"
                  >
                    <option
                      v-for="item in caseStatusOptions.options"
                      :key="item.value"
                      :value="item.value"
                    >
                      {{ item.label }}
                    </option>
                  </select>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <div class="row">
                <div class="col-xs-6 form-group col-md-12">
                  <label for="input_event" class="col-sm-2 control-label">
                    結案說明
                  </label>
                  <div class="col-sm-8">
                    <base-textarea
                      v-model="finishExplain"
                      :placeholder="'請輸入結案說明'"
                      :row="5"
                      input-length="500"
                    ></base-textarea>
                  </div>
                </div>
              </div>
            </div>
            <div class="fsm-form">
              <table class="table table-unstyled ">
                <tbody>
                  <tr colspan="4" align="center">
                    <td>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="close()"
                      >
                        <i class="fa " aria-hidden="true"></i>
                        取消
                      </button>
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="saveFinish()"
                      >
                        <i class="fa fa-save" aria-hidden="true"></i>
                        儲存
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </validation-observer>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'FinishCase',
  mixins: [page],
  props: {
    caseStatusOptions: {
      type: Object,
      default: () => {
        return {
          options: []
        }
      }
    }
  },
  data() {
    return {
      caseStatus: '',
      finishExplain: ''
    }
  },
  methods: {
    close() {
      this.$emit('close-modal')
    },
    saveFinish() {
      this.$emit(
        'savefinish',
        this.caseStatusOptions.selectedVal,
        this.finishExplain
      )
    }
  }
}
</script>
