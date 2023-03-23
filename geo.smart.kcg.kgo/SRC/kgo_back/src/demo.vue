<template>
  <div>
    <div style="margin-top: 10px" class="row">
      <div class="col-xs-6 form-group col-md-6">
        <label
          for="input_event"
          style="color: black; margin-top: 5px"
          class="col-sm-2 control-label"
        >
          流程名稱
        </label>
        <div class="col-sm-4" style="">
          <input
            type="text"
            class="form-control formItem_control"
            value="好孕專案輔助"
            placeholder=""
          />
        </div>
      </div>
    </div>
    <div style="margin-top: 10px" class="row">
      <div class="col-xs-6 form-group col-md-6">
        <label
          for="input_event"
          style="color: black; margin-top: 5px"
          class="col-sm-2 control-label"
        >
          流程說明
        </label>
        <div class="col-sm-4" style="">
          <input
            type="text"
            class="form-control formItem_control"
            value="好孕專案輔助流程"
            placeholder=""
          />
        </div>
      </div>
    </div>
    <div class="outside2">
      <div id="left1">
        <draggable
          v-model="stepList2"
          class=""
          style=""
          tag="ul"
          :group="{ name: 'people', pull: 'clone', put: false }"
          :clone="cloneDog"
          v-bind="dragOptions"
          @start="isDragging = true"
          @end="isDragging = false"
        >
          <transition-group type="transition" name="flip-list">
            <div
              v-for="element in stepList2"
              :key="element.order"
              style="
                text-align: center;
                box-shadow: 5px 5px 5px 6px #b7adad;
                border: 2px solid #505354;
                padding: 5px;
                margin: 5px;
                cursor: move;
              "
            >
              <img
                v-if="element.type == '2'"
                src="@/assets/img/usergroup.png"
                width="50px"
              />
              <img
                v-if="element.type == '1'"
                width="50px"
                src="@/assets/img/user123.png"
              />
              <p class="word">
                {{ element.type == '1' ? '簽核' : '會簽' }}
              </p>
            </div>
          </transition-group>
        </draggable>
      </div>
      <div id="right1">
        <div class="target">
          <!-- <button
            data-target="#add"
            data-toggle="modal"
            type="button"
            class="btn btn-blue btn-img btn-add"
          >
            <span>新增節點</span>
          </button> -->
          <draggable
            v-model="stepList"
            class=""
            tag="ul"
            group="people"
            v-bind="dragOptions"
            @start="isDragging = true"
            @end="isDragging = false"
          >
            <transition-group
              class="target-timeline"
              type="transition"
              name="flip-list"
            >
              <li
                v-for="(element, index) in stepList"
                :key="element.order"
                style="cursor: move"
              >
                <span class="rate-align"></span>
                <img
                  v-if="element.type == '2'"
                  src="@/assets/img/usergroup.png"
                  width="70px"
                />
                <img
                  v-if="element.type == '1'"
                  src="@/assets/img/user123.png"
                  width="70px"
                />
                <p class="rate-txt">節點名稱：{{ element.name }}</p>
                <p class="word">
                  簽核：{{
                    element.type == '1' ? element.pop : element.pops.join(',')
                  }}
                </p>
                <button
                  class="btn btn-primary"
                  type="button"
                  @click="edit(index)"
                >
                  編輯
                </button>
                <button class="icon btn-delete" @click="deleteItem(index)">
                  <img
                    src="@/assets/img/btn-delete.png"
                    class="img-responsive"
                  />
                </button>
              </li>
            </transition-group>
          </draggable>
          <div
            id="add"
            ref="add"
            class="modal fade modal-page"
            tabindex="-1"
            role="dialog"
            aria-labelledby="A02-01-07Title"
            aria-hidden="true"
          >
            <div
              class="modal-dialog modal-lg modal-dialog-centered"
              role="document"
            >
              <div class="modal-content">
                <!-- modal-header -->
                <div class="modal-header">
                  <h5 id="A02-01-07Title" class="modal-title">新增節點</h5>
                  <button
                    type="file"
                    class="close"
                    data-dismiss="modal"
                    aria-label="Close"
                  >
                    <span aria-hidden="true"></span>
                  </button>
                </div>
                <!-- modal-header end -->

                <!-- modal-body -->
                <div class="modal-body">
                  <div class="fsm-form">
                    <span>
                      <div class="row">
                        <div class="col-xs-6 form-group col-md-6">
                          <label
                            for="input_event"
                            class="col-sm-4 control-label"
                          >
                            節點名稱
                          </label>
                          <div class="col-sm-8" style="padding-top: 7px">
                            <input
                              id=""
                              v-model="newName"
                              type="text"
                              class="form-control formItem_control"
                              placeholder=""
                            />
                          </div>
                        </div>
                        <div class="fsm-form">
                          <table class="table table-unstyled">
                            <tbody>
                              <tr colspan="4" align="center">
                                <td>
                                  <button
                                    data-dismiss="modal"
                                    type="button"
                                    class="btn btn-fsm"
                                  >
                                    <i aria-hidden="true" class="fa"></i>
                                    取消
                                  </button>
                                  <button
                                    type="button"
                                    class="btn btn-fsm"
                                    @click="add"
                                  >
                                    <i
                                      aria-hidden="true"
                                      class="fa fa-save"
                                    ></i>
                                    新增
                                  </button>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </span>
                  </div>
                  <!-- <div class="form-group row formItem">
              <label
                for="inputPassword"
                class="col-xl-3 col-form-label label formItem_label label"
              >
                節點名稱
                <span class="color-red"></span>
              </label>
              <div class="col-xl-9">
                <input
                  type="text"
                  v-model="newName"
                  class="form-control formItem_control"
                  id=""
                  placeholder=""
                />
              </div>
            </div>
            <div class="form-group row formItem">
              <label
                for="inputPassword"
                class="col-xl-3 col-form-label label formItem_label label"
              >
                節點類型
                <span class="color-red"></span>
              </label>
              <div class="col-xl-9">
                <base-select
                  v-model="newType"
                  :select="newType"
                  required
                  :options="typeOptions"
                />
              </div>
            </div>
            <div class="form-group row formItem">
              <label
                for="inputPassword"
                class="col-xl-3 col-form-label label formItem_label label"
              >
                人員設定
                <span class="color-red"></span>
              </label>
              <div class="col-xl-9">
                <base-select
                  v-model="newDep"
                  :select="newDep"
                  required
                  :options="depOptions"
                />
                <base-select
                  v-if="newType == '1'"
                  v-model="newPopVal"
                  :select="newPopVal"
                  required
                  :options="pepOptions"
                />
                <div v-if="newType == '2'" class="form-check d-inline mr-4">
                  <input
                    :name="`c1`"
                    class="form-check-input"
                    :id="`c1`"
                    v-model="newPopsVal"
                    type="checkbox"
                    value="'張小明'"
                  />
                  <label class="ml-1" :for="`c1`">
                    張小明
                  </label>
                  <input
                    :name="`c2`"
                    class="form-check-input"
                    :id="`c2`"
                    v-model="newPopsVal"
                    type="checkbox"
                    value="'王小明'"
                  />
                  <label class="ml-1" :for="`c2`">
                    王小明
                  </label>
                  <input
                    :name="`c3`"
                    class="form-check-input"
                    :id="`c3`"
                    v-model="newPopsVal"
                    type="checkbox"
                    value="'王大明'"
                  />
                  <label class="ml-1" :for="`c3`">
                    王大明
                  </label>
                </div>
              </div>
            </div> -->

                  <!-- 按鈕群組 end -->
                </div>
                <!-- modal-body end -->
              </div>
            </div>
          </div>
          <div
            id="edit"
            ref="edit"
            class="modal fade modal-page"
            tabindex="-1"
            role="dialog"
            aria-labelledby="A02-01-07Title"
            aria-hidden="true"
          >
            <div
              class="modal-dialog modal-xl modal-dialog-centered"
              role="document"
            >
              <div class="modal-content">
                <!-- modal-header -->
                <div class="modal-header">
                  <h5 id="A02-01-07Title" class="modal-title">維護節點設定</h5>
                  <button
                    type="file"
                    class="close"
                    data-dismiss="modal"
                    aria-label="Close"
                  >
                    <span aria-hidden="true"></span>
                  </button>
                </div>
                <!-- modal-header end -->

                <!-- modal-body -->
                <div class="modal-body">
                  <!-- ===s== -->
                  <div class="fsm-form">
                    <span>
                      <div class="row">
                        <div class="col-xs-6 form-group col-md-6">
                          <label
                            for="input_event"
                            class="col-sm-4 control-label"
                          >
                            節點名稱
                          </label>
                          <div class="col-sm-8" style="padding-top: 7px">
                            <input
                              id=""
                              v-model="stepList[editIndex].name"
                              type="text"
                              class="form-control formItem_control"
                              placeholder=""
                            />
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-xs-6 form-group col-md-6">
                          <label
                            for="input_event"
                            class="col-sm-4 control-label"
                          >
                            節點類型
                          </label>
                          <div class="col-sm-8" style="padding-top: 7px">
                            <base-select
                              v-model="stepList[editIndex].type"
                              :select="stepList[editIndex].type"
                              required
                              :options="typeOptions"
                            />
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-xs-6 form-group col-md-6">
                          <label
                            for="input_event"
                            class="col-sm-4 control-label"
                          >
                            人員設定
                          </label>
                          <div class="col-sm-8" style="padding-top: 7px">
                            <base-select
                              v-model="newDep"
                              :select="newDep"
                              required
                              :options="depOptions"
                            />
                            <base-select
                              v-if="stepList[editIndex].type == '1'"
                              v-model="stepList[editIndex].pop"
                              :select="stepList[editIndex].pop"
                              required
                              :options="pepOptions"
                            />
                            <div
                              v-if="stepList[editIndex].type == '2'"
                              style="line-height: 1.42857"
                            >
                              <input
                                :id="`c1`"
                                v-model="stepList[editIndex].pops"
                                :name="`c1`"
                                class="checkbox-custom"
                                type="checkbox"
                                value="張小明"
                              />
                              <label class="checkbox-custom-label" :for="`c1`">
                                張小明
                              </label>
                              <input
                                :id="`c2`"
                                v-model="stepList[editIndex].pops"
                                :name="`c2`"
                                class="checkbox-custom"
                                type="checkbox"
                                value="王小明"
                              />
                              <label class="checkbox-custom-label" :for="`c2`">
                                王小明
                              </label>
                              <input
                                :id="`c3`"
                                v-model="stepList[editIndex].pops"
                                :name="`c3`"
                                class="checkbox-custom"
                                type="checkbox"
                                value="王大明"
                              />
                              <label class="checkbox-custom-label" :for="`c3`">
                                王大明
                              </label>
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
                                  data-dismiss="modal"
                                  type="button"
                                  class="btn btn-fsm"
                                >
                                  <i aria-hidden="true" class="fa"></i>
                                  取消
                                </button>
                                <button
                                  type="button"
                                  data-dismiss="modal"
                                  class="btn btn btn-primary"
                                >
                                  <i aria-hidden="true" class="fa fa-save"></i>
                                  儲存
                                </button>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </span>
                  </div>
                  <!-- ====end=== -->
                  <!-- modal-body end -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import draggable from 'vuedraggable'
export default {
  name: 'StepBar',
  components: {
    draggable
  },
  props: {
    currentStep: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      // stepList: [],
      depOptions: [],

      pepOptions: [],
      typeOptions: [],

      newName: '',
      newType: '',
      newDep: '',
      newPopVal: '',
      newPopsVal: [],
      editName: '',
      editIndex: 0,
      tempName: '',

      stepList: [
        {
          name: '主管一',
          type: '1',
          order: 1,
          dep: '民政局XX科',
          pop: '王小明',
          pops: ['王小明']
        },
        {
          name: '主管二',
          type: '2',
          order: 2,
          dep: '民政局XX科',
          pop: '王大明',
          pops: ['王大明']
        }
      ],
      stepList2: [
        {
          name: '',
          type: '1',
          order: 1,
          dep: '民政局XX科',
          pop: '',
          pops: []
        },
        {
          name: '',
          type: '2',
          order: 2,
          dep: '民政局XX科',
          pop: '',
          pops: []
        }
      ]
    }
  },
  computed: {
    dragOptions() {
      return {
        animation: 0,
        group: 'description',
        disabled: false,
        ghostClass: 'ghost'
      }
    }
  },
  mounted() {
    this.depOptions = [
      {
        label: '民政局XX科',
        value: '民政局XX科'
      }
    ]

    this.pepOptions = [
      {
        label: '張小明',
        value: '張小明'
      },
      {
        label: '王小明',
        value: '王小明'
      },
      {
        label: '王大明',
        value: '王大明'
      }
    ]
    this.typeOptions = [
      {
        label: '簽核',
        value: '1'
      },
      {
        label: '會簽',
        value: '2'
      }
    ]
  },
  methods: {
    add() {
      this.stepList.push({
        name: this.newName,
        order: this.stepList.length + 2,
        type: this.newType,
        dep: '民政局XX科',
        pops: this.newPopsVal,
        pop: this.newPopVal
      })
      this.newName = ''
      $(this.$refs.add).modal('hide')
    },
    cloneDog({ name, type }) {
      console.log(name)
      return {
        name: name,
        type: type,
        order: this.stepList.length + 2,
        dep: '民政局XX科',
        pop: '',
        pops: ['']
      }
    },
    edit(index) {
      this.editIndex = index
      $(this.$refs.edit).modal('show')
    },
    confirmEdit() {
      $(this.$refs.edit).modal('hide')
    },
    deleteItem(index) {
      if (this.stepList.length == 1) {
        return
      }
      this.stepList.splice(index, 1)
    }
  }
}
</script>
<style lang="scss" scoped>
.btn-blue {
  color: #fff !important;
  background-color: #007bff !important;
  border-color: #007bff !important;
}

.form-control {
  border-radius: 0px !important;
}

/deep/ .btn {
  border-radius: 5px !important;
}

.btn-blue:hover {
  color: #fff !important;
  background-color: #007bffaf !important;
  border-color: #007bffaf !important;
}

.btn-delete:hover {
  color: #fff !important;
  background-color: #f2f8feaf !important;
}
.flip-list-move {
  transition: transform 0.5s;
}
.no-move {
  transition: transform 0s;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.list-group {
  min-height: 20px;
}
.list-group-item {
  cursor: move;
}
// .list-group-item i {
//   cursor: pointer;
// }
.outside2 {
  //width: 100% !important;
  height: 100% !important;
  // background-color: #FF0;
  width: 510px;
  overflow: auto;
  margin: 5px;
  padding: 10px;
}
#left1 {
  height: 100% !important;
  padding: 10px;
  width: 150px;
  background-color: rgb(203, 197, 197);
  float: left;
  margin-right: 5px;
}
#right1 {
  height: 100% !important;
  width: 80% !important;
  // background-color: #6C6;
  float: left;
}

.icon {
  border: 0 !important;
  padding: 0 !important;
  background-color: transparent !important;
  cursor: pointer !important;
}

.icon:focus {
  outline: 0 !important;
}
.word {
  color: black;
  font-size: 18px;
  text-align: center;
  padding: 5px;
}
</style>
