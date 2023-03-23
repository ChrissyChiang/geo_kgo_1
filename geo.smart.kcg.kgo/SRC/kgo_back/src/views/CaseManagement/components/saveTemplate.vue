<template>
  <div class="demo-container">
    <div class="panel-body">
      <div class="tab-content">
        <div class="fsm-form">
          <div class="row">
            <div class="col-xs-12 form-group col-md-12">
              <label for="input_event" class="col-sm-2 control-label">
                表單名稱
              </label>
              <div class="col-sm-8">
                <base-input
                  v-model="name"
                  :placeholder="'輸入表單名稱'"
                  input-length="20"
                ></base-input>
              </div>
            </div>
          </div>
        </div>
        <div class="fsm-form">
          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4" align="center">
                <td>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                    @click="cancel"
                  >
                    <i class="fa" aria-hidden="true"></i>
                    取消
                  </button>
                  <button
                    type="button"
                    class="btn btn-fsm"
                    data-dismiss="modal"
                    @click="addTemplate"
                  >
                    <i class="fa fa-save" aria-hidden="true"></i>
                    確定
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'SortEdit',
  mixins: [page],
  data() {
    return {
      name: ''
    }
  },
  computed: {
    tableColumns() {
      return [
        {
          title: '欄位名稱',
          dataIndex: 'columnName',
          tdClass: 'text-center'
        },
        {
          title: '欄位型態',
          dataIndex: 'columnTypeName',
          tdClass: 'text-center'
        },
        {
          title: '欄位別名',
          dataIndex: 'columnId',
          tdClass: 'text-center'
        }
      ]
    }
  },
  methods: {
    cancel() {
      this.$emit('close-modal')
    },
    addTemplate() {
      this.$emit('add-template', this.name)
    },
    clear() {
      this.name = ''
    }
  }
}
</script>
