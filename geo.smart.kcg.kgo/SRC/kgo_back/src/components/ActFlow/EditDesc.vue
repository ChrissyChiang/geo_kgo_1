<template>
  <div class="fsm-form">
    <validation-observer ref="observer">
      <div class="fsm-form">
        <div class="row">
          <div class="col-xs-6 form-group col-md-12">
            <label
              for="input_event"
              class="col-sm-2 control-label"
              style="padding-left: 16px;"
            >
              流程說明
            </label>
            <div class="col-sm-8">
              <validate-container :rules="'required|max:200'">
                <base-textarea
                  v-model="flowDesc"
                  :placeholder="'請輸入流程說明'"
                  :row="5"
                ></base-textarea>
              </validate-container>
            </div>
          </div>
        </div>
      </div>
      <div class="fsm-form">
        <table class="table table-unstyled">
          <tbody>
            <tr colspan="4" align="center">
              <td>
                <button type="button" class="btn btn-fsm" data-dismiss="modal">
                  <i class="fa" aria-hidden="true"></i>
                  取消
                </button>
                <button type="button" class="btn btn-fsm" @click="saveDesc">
                  <i class="fa fa-save" aria-hidden="true"></i>
                  確認
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </validation-observer>
  </div>
</template>
<script>
import { cloneDeep } from 'lodash'
export default {
  name: 'EditDesc',
  data() {
    return {
      flowDesc: '',
      editNodeId: '',
      // 頁面節點與連線資料
      data: {}
    }
  },
  methods: {
    initData(data) {
      this.data = data.data
      this.editNodeId = data.editNodeId
      data.data.nodeList.filter(node => {
        if (node.id == this.editNodeId) {
          this.flowDesc = node.name
        }
      })

      //   this.editNodeId = nodeId
      //   this.flowDesc = desc
    },
    async saveDesc() {
      if (!(await this.$refs.observer.validate())) {
        return
      }
      this.data.nodeList.forEach(node => {
        if (node.id == this.editNodeId) {
          node.name = this.flowDesc
        }
      })
      this.$emit('confirm-save-desc')
    },
    clear() {
      this.flowDesc = ''
    }
  }
}
</script>
