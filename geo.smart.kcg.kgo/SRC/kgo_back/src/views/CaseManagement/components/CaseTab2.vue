<template>
  <div id="tab02" role="tabpanel" class="tab-pane">
    <div v-if="caseFlow != 'A'" class="panel panel-default defaultcollapse">
      <div id="heading7" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class
            role="button"
            aria-expanded="true"
            aria-controls="collapse7"
            href="#collapse7"
            data-toggle="collapse"
            data-parent="#accordion"
          >
            Step 1_服務宣告設定(必填)
          </a>
        </h4>
      </div>
      <div
        id="collapse7"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="heading7"
        aria-expanded="true"
      >
        <div class="panel-body">
          <base-check-list
            v-model="checkedServiceHtmlVal"
            :options="isServiceHtmlCheckBox"
            :selected-list="checkedServiceHtmlVal"
          />
          <app-editor
            v-model="serviceHtml"
            :total.sync="totalCount"
            :max-size="5000"
            :height="250"
          ></app-editor>
        </div>
      </div>
    </div>

    <div v-if="caseFlow != 'A'" class="panel panel-default defaultcollapse">
      <div id="headinga107" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-expanded="true"
            aria-controls="collapsea107"
            href="#collapsea107"
            data-toggle="collapse"
            data-parent="#onlinedata"
          >
            Step2_身分驗證設定(參照單一)
          </a>
        </h4>
      </div>
      <div
        id="collapsea107"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="headinga107"
      >
        <div class="panel-body">
          <table class="table table-unstyled">
            <tbody>
              <tr>
                <td colspan="4">
                  <!-- <base-check-list
                    ref="checkList"
                    :options="identityVerifyCheckBox"
                    v-model="selectedIdentityVerifyList"
                    @trigger-click="checkIdentityVerifyCheck"
                    :selected-list="selectedIdentityVerifyList"
                  />-->
                  <div style="line-height: 1.42857">
                    <span
                      v-for="(item, index) in identityVerifyCheckBox"
                      :key="index"
                    >
                      <input
                        :id="`_tmcaseparam_isverify_${index}`"
                        :name="`tmcaseparam.isverify_${index}`"
                        type="checkbox"
                        class="checkbox-custom identityVerifyCheckBox"
                        :checked="
                          selectedIdentityVerifyList.includes(item.value)
                        "
                        :value="item.value"
                        @change="verifyCheckBoxEvent"
                      />
                      <label
                        :for="`_tmcaseparam_isverify_${index}`"
                        class="checkbox-custom-label"
                      >
                        {{ item.label }}
                      </label>
                    </span>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <!-- 表單設定(A流程不顯示) -->
    <div v-if="caseFlow != 'A'" class="panel panel-default defaultcollapse">
      <div id="headinga108" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-controls="collapsea108"
            href="#collapsea108"
            data-toggle="collapse"
            data-parent="#onlinedata"
          >
            Step3_表單設定(必填)(欄位屬性需參考規範)
          </a>
        </h4>
      </div>
      <div
        id="collapsea108"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="headinga108"
      >
        <div class="panel-body">
          <div
            id="gridmain_tccaseonlineform"
            class="k-widget k-grid table table-fsm k-display-block k-editable"
            data-role="grid"
          >
            <div
              style="margin-bottom: 10px"
              class="k-header k-grid-toolbar k-grid-top"
            >
              <button
                type="button"
                class="btn btn-warning"
                @click="openFormGroupEdit"
              >
                <i class="fa fa-external-link" aria-hidden="true"></i>
                群組維護
              </button>
              <button
                type="button"
                class="btn btn-warning"
                @click="formUpdateVersion"
              >
                <i class="fa fa-external-link" aria-hidden="true"></i>
                表單儲存更版
              </button>
              <button
                v-if="isShowMyData"
                type="button"
                class="btn btn-warning"
                @click="openMyDataEdit"
              >
                <i class="fa fa-external-link" aria-hidden="true"></i>
                管理MyData資料集
              </button>
            </div>

            <div
              v-for="(group, index) in formSetHomeDataGrid"
              :key="index"
              class="panel panel-default defaultcollapse"
            >
              <div
                :id="'headingForm' + index"
                class="panel-heading"
                role="tab"
                :href="'#collapseForm' + index"
                data-toggle="collapse"
                aria-expanded="true"
                :aria-controls="'collapseForm' + index"
              >
                <h4
                  class="panel-title"
                  style="font-size: 1em; font-weight: 500"
                >
                  <div style="display: flex; justify-content: space-between">
                    <a role="button">{{ group.groupName }}</a>
                    <div class="text-right">
                      <button
                        v-if="group.groupName == '基本設定'"
                        type="button"
                        class="btn btn-warning"
                        @click.stop="showSaveTemplate"
                      >
                        儲存樣板
                      </button>
                      <button
                        v-if="group.groupName == '基本設定'"
                        type="button"
                        class="btn btn-warning"
                        @click.stop="addFormColumn(index)"
                      >
                        切換樣板
                      </button>
                      <button
                        type="button"
                        class="btn btn-warning"
                        @click.stop="editFormColumn(null, group.groupSeq, null)"
                      >
                        新增欄位
                      </button>
                    </div>
                  </div>
                </h4>
              </div>
              <div
                :id="'collapseForm' + index"
                class="panel-collapse collapse"
                role="tabpanel"
                :aria-labelledby="'headingForm' + index"
              >
                <div class="panel-body">
                  <app-table
                    :columns="formColumnTableColumns"
                    :data="group.columnData"
                    :draggable="true"
                    :show-pagination="false"
                    :group-name="'groupName' + group.groupSeq"
                    @drag-change="handleDragChange"
                  >
                    <template v-slot:operating="{ index, data }">
                      <button
                        type="button"
                        class="btn-line btn-warning"
                        @click="editFormColumn(data, group.groupSeq, index)"
                      >
                        <i class="fa fa-cog" aria-hidden="true"></i>
                        編輯
                      </button>
                      <button
                        v-if="
                          data.columnId != 'Email' &&
                            data.columnId != 'CellPhone' &&
                            data.columnId != 'Name' &&
                            data.columnId != 'ID'
                        "
                        type="button"
                        class="btn-line btn-danger"
                        @click="showDelFormColumnConfirm(group.groupSeq, index)"
                      >
                        <i class="fa fa-trash-o" aria-hidden="true"></i>
                        刪除
                      </button>
                    </template>
                  </app-table>
                </div>
              </div>
            </div>
          </div>
          <a href target="_blank"></a>
        </div>
        <a href target="_blank"></a>
      </div>
      <a href target="_blank"></a>
    </div>
    <!-- 受理機關(A流程不顯示) -->
    <div v-if="isShowAcceptSet" class="panel panel-default defaultcollapse">
      <div id="headinga101" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-controls="collapsea101"
            href="#collapsea101"
            data-toggle="collapse"
            data-parent="#onlinedata"
          >
            受理機關設定(必填)
          </a>
        </h4>
      </div>
      <div
        id="collapsea101"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="headinga101"
      >
        <div class="panel-body">
          <div class="panel-group"></div>

          <label
            class="control-label"
            style="width: 200px; vertical-align: middle !important"
          >
            指定機關分文或承辦人
          </label>
          <base-native-select
            v-model="acceptSetComboBox.selectedVal"
            :options="acceptSetComboBox.options"
            :select="acceptSetComboBox.selectedVal"
          />

          <table class="table table-unstyled">
            <tbody>
              <tr colspan="4">
                <td>
                  <button
                    v-if="acceptSetComboBox.selectedVal == 'UNIT'"
                    type="button"
                    class="btn btn-warning"
                    @click="openSelectOrganModal"
                  >
                    <i class="fa fa-external-link" aria-hidden="true"></i>
                    新增受理機關
                  </button>
                  <button
                    v-if="acceptSetComboBox.selectedVal == 'OFFICER'"
                    type="button"
                    class="btn btn-warning"
                    @click="openChoosePeople"
                  >
                    <i class="fa fa-external-link" aria-hidden="true"></i>
                    新增受理承辦人
                  </button>
                  <button
                    v-if="acceptSetComboBox.selectedVal == 'AREA'"
                    type="button"
                    class="btn btn-warning"
                    @click="openSelectAcceptSetAreaModal"
                  >
                    <i class="fa fa-external-link" aria-hidden="true"></i>
                    新增區受理機關
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
          <!-- 機關 start -->
          <app-table
            v-if="acceptSetComboBox.selectedVal == 'UNIT'"
            :columns="acceptSetUnitTableColumns"
            :data="acceptSetUnitGrid"
            :show-pagination="false"
          >
            <template v-slot:operating="{ index }">
              <button
                type="button"
                class="btn-line btn-danger"
                @click="showDelAcceptSetConfirm(index, 'UNIT')"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
          <!-- 機關 end -->
          <!-- 承辦人 start-->
          <app-table
            v-if="acceptSetComboBox.selectedVal == 'OFFICER'"
            :columns="acceptSetOfficerTableColumns"
            :data="acceptSetOfficerGrid"
            :show-pagination="false"
          >
            <template v-slot:operating="{ index }">
              <button
                type="button"
                class="btn-line btn-danger"
                @click="showDelAcceptSetConfirm(index, 'OFFICER')"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
          <!-- 承辦人 end -->
          <!-- 區機關 start-->
          <app-table
            v-if="acceptSetComboBox.selectedVal == 'AREA'"
            :columns="acceptSetAreaTableColumns"
            :data="acceptSetAreaGrid"
            :show-pagination="false"
          >
            <template v-slot:operating="{ index }">
              <button
                type="button"
                class="btn-line btn-danger"
                @click="showDelAcceptSetConfirm(index, 'AREA')"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
          <!-- 區機關 end -->
        </div>
      </div>
    </div>
    <!-- 受理期間設定(A流程不顯示) -->
    <div v-if="false" class="panel panel-default defaultcollapse">
      <div id="heading109" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-expanded="true"
            aria-controls="heading109"
            href="#collapsea109"
            data-toggle="collapse"
            data-parent="#accordion"
          >
            受理期間設定
          </a>
        </h4>
      </div>
      <div
        id="collapsea109"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="heading109"
      >
        <div class="panel-body">
          <table class="table table-unstyled">
            <tbody>
              <tr>
                <td>
                  <label class="control-label">受理期間</label>
                </td>
                <td class="form-inline"></td>
                <th width="100">每年</th>

                <td class="form-inline">
                  <base-month-day v-model="dateStart" :select="dateStart" />
                  ~
                  <base-month-day v-model="dateEnd" :select="dateEnd" />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <!-- 限辦期限設定(B3才顯示) -->
    <div v-if="limitPeriodRequired" class="panel panel-default defaultcollapse">
      <div id="heading111" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-expanded="true"
            aria-controls="collapse111"
            href="#collapse111"
            data-toggle="collapse"
            data-parent="#accordion"
          >
            限辦期限設定(必填)(到期前1天及當天通知)
          </a>
        </h4>
      </div>
      <div
        id="collapse111"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="heading111"
      >
        <div class="panel-body">
          <table class="table table-unstyled">
            <tbody>
              <tr>
                <th width="200">限辦期限</th>
                <td colspan="2" style="display: flex; width: 200px">
                  <input
                    v-model="limitPeriod"
                    type="number"
                    class="form-control formItem_control"
                    placeholder
                  />
                </td>
              </tr>
              <tr>
                <th width="200">逾期通知郵件</th>
                <td colspan="4">
                  <validation-observer
                    ref="observerMail"
                    style="display: flex; width: 450px"
                  >
                    <validate-container
                      style="display: flex; width: 450px"
                      rules="email"
                    >
                      <input
                        v-model="newMailInput"
                        type="text"
                        class="form-control formItem_control"
                        placeholder
                      />
                      <button
                        type="button"
                        class="btn btn-fsm"
                        @click="addMail"
                      >
                        <i class="fa fsm-icon-zoom-in" aria-hidden="true"></i>
                      </button>
                    </validate-container>
                  </validation-observer>
                  <div
                    v-for="(mail, index) in mailArray"
                    :key="index"
                    style="display: flex; width: 450px"
                  >
                    <p>{{ mail }}</p>
                    <button class="icon btn-delete" @click="deleteMail(index)">
                      <img
                        src="@/assets/img/btn-delete.png"
                        class="img-responsive"
                      />
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <!-- 網路申辦說明設定 -->
    <div class="panel panel-default defaultcollapse">
      <div id="heading112" class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            class="collapsed"
            role="button"
            aria-expanded="true"
            aria-controls="collapse112"
            href="#collapse112"
            data-toggle="collapse"
            data-parent="#accordion"
          >
            網路申辦說明設定
          </a>
        </h4>
      </div>
      <div
        id="collapse112"
        class="panel-collapse collapse in"
        role="tabpanel"
        aria-labelledby="heading112"
      >
        <div class="panel-body">
          <div class="panel-group">
            <button
              type="button"
              class="btn btn-warning"
              @click="editApplyDesc(null)"
            >
              <i class="fa fa-external-link" aria-hidden="true"></i>
              新增說明
            </button>
          </div>

          <app-table
            :columns="internetApplyDescTableColumns"
            :data="internetApplyDescHomeDataGrid"
            :draggable="false"
            :show-pagination="true"
          >
            <template v-slot:title="{ data: { contentHtml } }">
              {{ $f.subString($f.clearHTML(contentHtml), 100) }}
            </template>
            <template v-slot:operating="{ index, data: { seq } }">
              <button
                type="button"
                class="btn-line btn-warning"
                @click="editApplyDesc(index)"
              >
                <i class="fa fa-cog" aria-hidden="true"></i>
                編輯
              </button>
              <button
                type="button"
                class="btn-line btn-danger"
                @click="showDelApplyDescConfirm(seq)"
              >
                <i class="fa fa-trash-o" aria-hidden="true"></i>
                刪除
              </button>
            </template>
          </app-table>
        </div>
      </div>
    </div>
    <app-modal
      ref="formFieldEditModal"
      :is-scroll="true"
      :modal-title="'欄位設定'"
      @before-hide="closeFormFieldEditModal"
    >
      <form-field-edit
        ref="formFieldEdit"
        @open-subField="openSubField"
        @save-field="saveformField"
      ></form-field-edit>
      <template v-slot:button>
        <button data-dismiss="modal" type="button" class="btn btn-fsm">
          <i aria-hidden="true" class="fa"></i>
          取消
        </button>
        <button
          type="button"
          class="btn btn btn-primary"
          @click="triggerSaveFormField"
        >
          <i aria-hidden="true" class="fa fa-save"></i>
          儲存
        </button>
      </template>
    </app-modal>
    <!--表單新增 -->
    <app-modal
      ref="formGroupAddModal"
      :is-scroll="true"
      :modal-title="'切換樣板'"
      @before-hide="clearFormGroupAddModal"
    >
      <form-group-add
        ref="formGroupAdd"
        @close-modal="closeFormGroupAddModal"
        @add-form="addFormGroup"
      ></form-group-add>
    </app-modal>

    <app-modal
      ref="SubFieldEditModal"
      :modal-title="'子欄位維護'"
      :modal-class="'modal-dialog modal-xl'"
      @before-hide="closeSubFieldEditModal"
    >
      <sub-field-edit
        ref="subFieldEdit"
        @save-subfield="saveSubfield"
      ></sub-field-edit>
    </app-modal>
    <app-modal
      ref="formGroupEditModal"
      :modal-title="'群組維護'"
      @before-hide="closeFormGroupEditModal"
    >
      <form-group-edit
        ref="formGroupEdit"
        @update-group="updateGroupData"
      ></form-group-edit>
    </app-modal>
    <app-modal
      ref="myDataEditModal"
      :modal-title="'管理MyData資料集'"
      @before-hide="closeMyDataEditModal"
    >
      <my-data-edit ref="myDataEdit"></my-data-edit>
    </app-modal>
    <app-modal ref="choosePeopleModal" :modal-title="'請選擇承辦人'">
      <choose-officer
        ref="choosePeople"
        :organ-options="{}"
        :unit-options="{}"
        @close-modal="closeChoose"
        @decide-people="showDecidePeople"
      ></choose-officer>
    </app-modal>
    <app-modal
      ref="selectAcceptSetAreaModal"
      :modal-title="'新增區機關'"
      @before-hide="closeSelectAcceptSetAreaModal"
    >
      <select-accept-set-area
        ref="selectAcceptSetArea"
        @select-area="onSelectAcceptSetArea"
      ></select-accept-set-area>
    </app-modal>
    <app-modal
      ref="selectOrganModal"
      :modal-title="'新增機關'"
      @before-hide="closeSelectOrganModal"
    >
      <organ-select
        ref="selectOrgan"
        @select-organ="onSelectOrgan"
      ></organ-select>
    </app-modal>
    <app-modal
      ref="applyDescEditModal"
      :modal-title="modalTitleName"
      @before-hide="closeApplyDescEditModal"
    >
      <attachment-edit
        ref="applyDescEdit"
        @confirm-edit="confirmApplyDescEdit"
      ></attachment-edit>
    </app-modal>
    <app-modal
      ref="saveTemplateModal"
      :modal-title="'新增表單'"
      @before-hide="closeSaveTemplate"
    >
      <save-template
        ref="saveTemplate"
        @close-modal="closeTemplate"
        @add-template="saveTemplate"
      ></save-template>
    </app-modal>
    <modal-confirm
      ref="confirmAcceptSetDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delLocalAcceptSet"
    />
    <modal-confirm
      ref="confirmApplyDescDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delApplyDesc"
    />
    <modal-confirm
      ref="confirmFormColumnDelete"
      :close-text="'取消'"
      :modal-content="'是否確定刪除資料？'"
      :modal-warn="'確認刪除，系統將執行刪除'"
      @confirm="delFormColumn"
    />
    <modal-confirm
      ref="confirmFormChange"
      :close-text="'取消'"
      :modal-content="'是否確定更換樣板？'"
      :modal-warn="'確認更換，系統將覆蓋所有欄位'"
      @confirm="changeFormGroup"
    />
  </div>
</template>
<script>
import { page } from '@/mixins'
import FormFieldEdit from './FormFieldEdit'
import FormGroupEdit from './FormGroupEdit'
import FormGroupAdd from './FormGroupAdd'
import MyDataEdit from './MyDataEdit'
//import ChooseOfficer from './ChooseOfficer'
import SelectAcceptSetArea from './SelectAcceptSetArea'
import SubFieldEdit from './SubFieldEdit'
import AttachmentEdit from './AttachmentEdit'
import OrganSelect from './OrganSelect'
import SaveTemplate from './saveTemplate'
export default {
  name: 'CaseTab2',
  components: {
    FormFieldEdit,
    FormGroupEdit,
    FormGroupAdd,
    MyDataEdit,
    SelectAcceptSetArea,
    //ChooseOfficer,
    SubFieldEdit,
    AttachmentEdit,
    OrganSelect,
    SaveTemplate
  },
  mixins: [page],
  props: {
    organName: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      /** 案件代碼 */
      caseSetId: this.$route.params.id || null,
      /** 網路申辦設定-服務宣告設定 啟用服務宣告html文字 */
      serviceHtml: '',
      totalCount: 0,
      /** 啟用服務宣告CheckBox元件 */
      isServiceHtmlCheckBox: [
        {
          label: '啟用服務宣告',
          value: 'T',
          groupKey: '',
          selected: true
        }
      ],
      /** 啟用服務宣告的勾選值 */
      checkedServiceHtmlVal: [],
      /** 身分驗證設定CheckBox元件集合 */
      identityVerifyCheckBox: [],
      /** 已選身分驗證設定 */
      selectedIdentityVerifyList: [],
      /** 受理機關設定下拉式選單 */
      acceptSetComboBox: {
        selectedVal: '',
        options: []
      },
      /** 受理機關-機關-設定表單資料 */
      acceptSetUnitGrid: [],
      /** 受理機關-承辦人-設定表單資料 */
      acceptSetOfficerGrid: [],
      /** 受理機關-區機關-設定表單資料 */
      acceptSetAreaGrid: [],
      /** 要刪除的受理機關設定Index */
      delAcceptSetIndex: null,
      /** 要刪除的受理機關類別 */
      delAcceptSetType: '',
      /** 受理期間起日 */
      dateStart: '0101',
      /** 受理期間訖日 */
      dateEnd: '0202',
      /** 限辦期限天數 */
      limitPeriod: 0,
      /** 網路申辦設定-限辦期限設定:電子郵件 */
      mailArray: [],
      newMailInput: '',
      /** 網路申請說明表單資料 */
      internetApplyDescHomeDataGrid: [],
      delApplyDescSeq: '',
      /** 表單資料 */
      formSetHomeDataGrid: [],
      /** 要刪除的群組Seq */
      delFormColumnGroupSeq: null,
      /** 要刪除的欄位index */
      delFormColumnIndex: null,
      modalTitleName: '',
      isModifyForm: false,
      /** 新增表單的index */
      addFromGroupIndex: '',
      changeSeq: '',
      allChangeFormColumnId: [],
      saveTemplateList: []
    }
  },
  computed: {
    /** 受理機關設定欄位 */
    acceptSetUnitTableColumns() {
      return [
        {
          title: '項次',
          slot: 'order',
          tdClass: 'text-center'
        },
        {
          title: '業管機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    },
    acceptSetOfficerTableColumns() {
      return [
        {
          title: '項次',
          slot: 'order',
          tdClass: 'text-center'
        },
        {
          title: '業管機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '科室',
          dataIndex: 'unitName',
          tdClass: 'text-center'
        },
        {
          title: '承辦人',
          dataIndex: 'userName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    },
    acceptSetAreaTableColumns() {
      return [
        {
          title: '項次',
          slot: 'order',
          tdClass: 'text-center'
        },
        {
          title: '業管機關',
          dataIndex: 'organName',
          tdClass: 'text-center'
        },
        {
          title: '隸屬區',
          dataIndex: 'zipName',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    },
    internetApplyDescTableColumns() {
      return [
        {
          title: '標題',
          dataIndex: 'title',
          width: '20%',
          tdClass: 'text-center'
        },
        {
          title: '說明內容',
          slot: 'title',
          tdClass: 'text-left'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          width: '20%',
          slot: 'operating'
        }
      ]
    },
    formColumnTableColumns() {
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
          title: '欄位長度',
          dataIndex: 'length',
          tdClass: 'text-center'
        },
        {
          title: '欄位別名',
          dataIndex: 'columnId',
          tdClass: 'text-center'
        },
        {
          title: '必填',
          dataIndex: 'isMustKeyStr',
          tdClass: 'text-center'
        },
        {
          title: '維護',
          tdClass: 'text-center',
          slot: 'operating'
        }
      ]
    },
    isShowMyData() {
      const showMyData = ['B1', 'B2', 'B3']
      return showMyData.includes(this.caseFlow)
    },
    acceptSetRequired() {
      const required = ['B1', 'B2', 'B3']
      return required.includes(this.caseFlow)
    },
    limitPeriodRequired() {
      const required = ['B3']
      return required.includes(this.caseFlow)
    },
    /** 案件是否為Flow流程 */
    isFlowCaseType() {
      return !['SA', 'SCA', 'URA'].includes(this.caseType)
    },
    /** 是否顯示受理機關選項 */
    isShowAcceptSet() {
      // 如果是案件是Flow流程不顯示
      if (this.isFlowCaseType) {
        return !this.isFlowCaseType
      } else {
        // A流程不顯示
        return this.caseFlow != 'A'
      }
    }
  },
  watch: {
    formSetHomeDataGrid: {
      handler(val) {
        this.isModifyForm = true
      },
      deep: true
    }
  },
  async mounted() {
    this.caseSetId = this.$route.params.id || null
    if (this.caseSetId != null) {
      await this.loadingContainer(async () => {
        await this.getInternetApplyServiceHtmlHome()
        await this.getInternetApplyIdentityVerifyHome()
        await this.getInternetApplyAcceptDateHome()
        await this.getInternetApplyLimitPeriodHome()
        await this.getApplyDescriptionHome()
        await this.getFormSetHome()
        await this.getAcceptSetHome()
      })
    }
  },
  methods: {
    /** 啟用服務對象start */
    async getInternetApplyServiceHtmlHome() {
      const res = await this.$api.management.getInternetApplyServiceHtml({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.serviceHtml = data.serviceHtml
      if (data.isServiceHtmlCheckBox) {
        this.isServiceHtmlCheckBox = [data.isServiceHtmlCheckBox]
        if (data.isServiceHtmlCheckBox.selected == true) {
          this.checkedServiceHtmlVal = [data.isServiceHtmlCheckBox.value]
        }
      }
    },
    /** 啟用服務對象end */

    /**   Step2_身分驗證設定 start */
    async getInternetApplyIdentityVerifyHome() {
      const res = await this.$api.management.getInternetApplyIdentityVerifyHome(
        {
          caseSetId: this.caseSetId
        }
      )
      const data = res.data.data
      if (
        data.identityVerifyCheckBox &&
        Array.isArray(data.identityVerifyCheckBox)
      ) {
        this.identityVerifyCheckBox = data.identityVerifyCheckBox
        this.selectedIdentityVerifyList = this.identityVerifyCheckBox
          .filter(x => x.selected == true)
          .map(item => item.value)
      }
    },
    /** 檢查不選並清空的類型 */
    verifyCheckBoxEvent(ev) {
      const ckList = document.getElementsByClassName('identityVerifyCheckBox')
      const needClear1 = ['BASIC']
      const needClear2 = ['NAN']
      let hasNeedClear1 = false
      let hasNeedClear2 = false
      ckList.forEach(item => {
        if (needClear1.includes(item.value) && item.checked) {
          hasNeedClear1 = true
        }
      })
      ckList.forEach(item => {
        if (needClear2.includes(item.value) && item.checked) {
          hasNeedClear2 = true
        }
      })

      // 如果勾選的是needClear，則清除其他的checkbox
      if (needClear1.includes(ev.target.value) && ev.target.checked) {
        ckList.forEach(item => {
          if (item.value != ev.target.value) {
            item.checked = false
          }
        })
      } else if (needClear2.includes(ev.target.value) && ev.target.checked) {
        ckList.forEach(item => {
          if (item.value != ev.target.value) {
            item.checked = false
          }
        })
      } else {
        if (hasNeedClear1) {
          ev.target.checked = false
          this.notifySuccess('只能選擇「市府員工」為唯一驗證方式')
        } else if (hasNeedClear2) {
          ev.target.checked = false
          this.notifySuccess('只能選擇「免驗證」為唯一驗證方式')
        }
      }
    },
    /**   Step2_身分驗證設定 end */

    ////////Step3_表單設定 start/////////////////
    async getFormSetHome() {
      const res = await this.$api.management.getInternetApplyFormSetHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.formSetHomeDataGrid = data.grid || []

      await this.delay(200)
      this.isModifyForm = false
    },
    async formUpdateVersion() {
      await this.loadingContainer(async () => {
        await this.saveForm()
      })
    },
    async saveForm() {
      this.resetFormSetColumnDataSort()
      const updateColumns = this.formSetHomeDataGrid.map(item => ({
        groupName: item.groupName,
        orderNum: item.orderNum,
        columnData: item.columnData.map(column => ({
          columnId: column.columnId,
          columnName: column.columnName,
          columnType: column.columnType,
          columnValue: column.columnValue,
          orderNum: column.orderNum,
          length: column.length,
          isMustKey: column.isMustKey,
          myDataId: column.myDataId,
          myDataColumn: column.myDataColumn,
          myDataCheckType: column.myDataCheckType,
          myDataCheckValue: column.myDataCheckValue,
          memo: column.memo,
          fileType: column.fileType,
          complex:
            column.complex && column.complex.length > 0
              ? column.complex
              : // column.complex.reduce((prev, curr) => {
                //     return prev.concat(curr)
                //   })
                []
        }))
      }))
      const res = await this.$api.management.saveInternetApplyApplyFormSetGroupColumn(
        {
          caseSetId: this.caseSetId,
          groupColumnSet: updateColumns
        }
      )
      if (this.checkResSuccess(res, false)) {
        await this.getFormSetHome()
      }
    },
    handleDragChange({ data, moved }) {
      // // 排序全部重算
      data.forEach((item, index) => {
        item.orderNum = index + 1
      })
    },
    resetFormSetColumnDataSort() {
      this.formSetHomeDataGrid.forEach((item, index) => {
        item.columnData.forEach((subItem, subIndex) => {
          subItem.orderNum = subIndex + 1
        })
      })
    },
    closeFormGroupEditModal() {
      this.$refs.formGroupEdit.clear()
    },
    openFormGroupEdit() {
      const groupData = this.formSetHomeDataGrid.map(item => ({
        groupSeq: item.groupSeq,
        orderNum: item.orderNum,
        groupName: item.groupName
      }))
      this.$refs.formGroupEdit.initData(groupData)
      this.$refs.formGroupEditModal.show()
    },
    addFormColumn(index) {
      this.$refs.formGroupAdd.getHomeData()
      this.$refs.formGroupAddModal.show()
      this.addFromGroupIndex = index
    },
    editFormColumn(data, groupSeq, editIndex) {
      /*
      let columnIds = []
      if (groupSeq != null) {
        const findGroup = this.formSetHomeDataGrid.find(
          x => x.groupSeq == groupSeq
        )
        if (findGroup != null) {
          // 過濾掉除了自己編輯欄位所有的columnId(要檢核不能有重覆的columnId)
          columnIds = findGroup.columnData
            .filter((x, index) => {
              if (editIndex == null) {
                return true
              } else {
                return index == editIndex ? false : true
              }
            })
            .map(item => item.columnId)
        }
      }
      */
      let excludeColumnId = null
      if (groupSeq != null) {
        const findGroup = this.formSetHomeDataGrid.find(
          x => x.groupSeq == groupSeq
        )
        if (findGroup != null && editIndex != null) {
          excludeColumnId = findGroup.columnData[editIndex].columnId
        }
      }
      let allColumnIds = []
      this.formSetHomeDataGrid.forEach(item => {
        item.columnData.forEach(subItem => {
          if (excludeColumnId != subItem.columnId) {
            allColumnIds.push(subItem.columnId)
          }
        })
      })
      this.$refs.formFieldEdit.initData(
        data,
        groupSeq,
        editIndex,
        allColumnIds,
        this.organName
      )
      this.$refs.formFieldEditModal.show()
    },
    saveformField(data) {
      if (data.editGroupSeq != null) {
        const findGroup = this.formSetHomeDataGrid.find(
          x => x.groupSeq == data.editGroupSeq
        )
        if (findGroup) {
          if (data.editIndex != null) {
            const findColumn = findGroup.columnData[data.editIndex]
            if (findColumn) {
              findColumn.columnId = data.columnId
              findColumn.columnName = data.columnName
              findColumn.columnType = data.columnType
              findColumn.columnValue = data.columnValue || ''
              findColumn.columnTypeName = data.columnTypeName
              findColumn.length = data.length || 0
              findColumn.isMustKey = data.isMustKey
              findColumn.isMustKeyStr = data.isMustKey == '0' ? '否' : '是'
              findColumn.memo = data.memo || ''
              findColumn.myDataId = data.myDataId || ''
              findColumn.myDataColumn = data.myDataColumn || ''
              findColumn.myDataCheckType = data.myDataCheckType || ''
              findColumn.myDataCheckValue = data.myDataCheckValue || ''
              findColumn.complex = data.complex
              findColumn.fileType = data.fileType || ''
            }
          } else {
            findGroup.columnData.push({
              columnId: data.columnId,
              columnName: data.columnName,
              columnType: data.columnType,
              columnTypeName: data.columnTypeName,
              columnValue: data.columnValue || '',
              length: data.length || 0,
              isMustKey: data.isMustKey,
              isMustKeyStr: data.isMustKey == '0' ? '否' : '是',
              memo: data.memo || '',
              myDataId: data.myDataId || '',
              myDataColumn: data.myDataColumn || '',
              myDataCheckType: data.myDataCheckType || '',
              myDataCheckValue: data.myDataCheckValue || '',
              orderNum: findGroup.columnData.length + 1,
              complex: data.complex,
              fileType: data.fileType || ''
            })
          }
        }
      }
      this.$refs.formFieldEditModal.hide()
    },
    triggerSaveFormField() {
      this.$refs.formFieldEdit.saveField()
    },
    showDelFormColumnConfirm(groupSeq, index) {
      this.delFormColumnGroupSeq = groupSeq
      this.delFormColumnIndex = index
      this.$refs.confirmFormColumnDelete.show()
    },
    delFormColumn() {
      const findDelFormGroup = this.formSetHomeDataGrid.find(
        x => x.groupSeq == this.delFormColumnGroupSeq
      )
      if (findDelFormGroup) {
        findDelFormGroup.columnData.splice(this.delFormColumnIndex, 1)
      }
      this.$refs.confirmFormColumnDelete.hide()
    },
    updateGroupData(groupData) {
      let tempTableData = []
      for (let index = 0; index < groupData.length; index++) {
        const groupItem = groupData[index]
        const findGroupData = this.formSetHomeDataGrid.find(
          x => x.groupSeq == groupItem.groupSeq
        )
        // 如果有找到變更資料
        if (findGroupData) {
          tempTableData.push({
            groupSeq: findGroupData.groupSeq,
            orderNum: index + 1,
            groupName: findGroupData.groupName,
            columnData: [...findGroupData.columnData]
          })
        } else {
          tempTableData.push({
            groupSeq: groupItem.groupSeq,
            orderNum: index + 1,
            groupName: groupItem.groupName,
            columnData: []
          })
        }
      }
      this.formSetHomeDataGrid = tempTableData
    },
    //新增表單
    async addFormGroup(seq, allColumnIds) {
      this.$refs.confirmFormChange.show()
      this.changeSeq = seq
      this.allChangeFormColumnIds = allColumnIds
    },
    async changeFormGroup() {
      this.$refs.confirmFormChange.hide()
      let formData = []
      let goOn = true
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementDetail({
          seq: this.changeSeq
        })
        formData = res.data.data.grid
        formData = formData.sort(function(a, b) {
          return a.orderNum > b.orderNum ? 1 : -1
        })
      })
      let temIndex = 0
      //檢查匯入樣板之欄位型態是否有和其他群族重複
      this.formSetHomeDataGrid.forEach(item => {
        if (temIndex != this.addFromGroupIndex) {
          item.columnData.forEach(subItem => {
            if (this.allChangeFormColumnIds.indexOf(subItem.columnId) >= 0) {
              this.notifySuccess(
                '已有相同欄位別名存在於其他群組，故無法切換此樣板，請重新選擇！！'
              )
              goOn = false
              return
            }
          })
        }
        temIndex++
      })
      //服務機關不是稅捐處時不行匯入包含有AddressTextBox的樣板
      formData.forEach(item => {
        if (item.columnType == 'AddressTextBox' && this.organName != '稅捐處') {
          this.notifySuccess(
            '此樣板包含地址(分處)為稅捐處專屬欄位型別，故無法切換此樣板，請重新選擇！！'
          )
          goOn = false
          return
        }
      })
      if (goOn) {
        this.formSetHomeDataGrid[this.addFromGroupIndex].columnData = []
        formData.forEach(item => {
          this.formSetHomeDataGrid[this.addFromGroupIndex].columnData.push(item)
        })
      }
      this.addFromGroupIndex = ''
      this.changeSeq = ''
      this.allChangeFormColumnId = []
    },
    showSaveTemplate() {
      this.saveTemplateList = []
      let colData = this.formSetHomeDataGrid.filter(
        item => item.groupName == '基本設定'
      )[0].columnData
      let noMydata = []
      let count = 0
      let tem = {}
      for (let i = 0; i < colData.length; i++) {
        //if (colData[i].myDataId == '' || colData[i].myDataId === undefined) {
        tem = {
          seq: '',
          columnId: colData[i].columnId,
          columnName: colData[i].columnName,
          columnType: colData[i].columnType,
          columnValue: colData[i].columnValue,
          complex: colData[i].complex === undefined ? [] : colData[i].complex,
          fileType: colData[i].fileType,
          isMustKey: colData[i].isMustKey,
          memo: colData[i].memo,
          length: colData[i].length,
          myDataCheckType: '',
          myDataCheckValue: '',
          myDataClientId: '',
          myDataColumn: '',
          myDataId: '',
          myDataType: '',
          orderNum: count
        }
        count++
        noMydata.push(tem)
        //}
      }
      this.saveTemplateList = noMydata
      this.$refs.saveTemplateModal.show()
    },
    closeTemplate() {
      this.$refs.saveTemplateModal.hide()
    },
    async saveTemplate(name) {
      let rtnCode
      let request = {
        columnData: this.saveTemplateList,
        name: name,
        seq: ''
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getTemplateManagementAdd(request)
        rtnCode = res.data.rtnCode
      })
      if (rtnCode == '0000') {
        this.notifySuccess('新增表單成功！')
      }
    },
    closeSaveTemplate() {
      this.$refs.saveTemplate.clear()
    },
    closeFormFieldEditModal() {
      this.$refs.formFieldEdit.clear()
    },
    closeFormGroupAddModal() {
      this.$refs.formGroupAddModal.hide()
    },
    clearFormGroupAddModal() {
      this.$refs.formGroupAdd.clear()
    },
    openMyDataEdit() {
      this.$refs.myDataEditModal.show()
    },
    closeMyDataEditModal() {
      this.$refs.myDataEdit.clear()
    },

    closeSubFieldEditModal() {
      this.$refs.subFieldEdit.clear()
    },
    saveSubfield(data) {
      this.$refs.formFieldEdit.saveSubfield(data)
      this.$refs.SubFieldEditModal.hide()
    },
    openSubField(data) {
      this.$refs.subFieldEdit.initData(data)
      this.$refs.SubFieldEditModal.show()
    },

    /////////Step3_表單設定 end///////////////////////

    ////////受理機關設定 start////////////////////////
    async getAcceptSetHome() {
      const res = await this.$api.management.getInternetApplyAcceptSetHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      if (data.acceptSetComboBox) {
        this.acceptSetComboBox = data.acceptSetComboBox
      }
      this.acceptSetUnitGrid = data.grid || []

      const resOfficer = await this.$api.management.queryInternetApplyAcceptSetOfficer(
        {
          caseSetId: this.caseSetId
        }
      )
      const officerData = resOfficer.data.data

      this.acceptSetOfficerGrid = officerData.grid || []
      const resArea = await this.$api.management.queryInternetApplyAcceptSetArea(
        {
          caseSetId: this.caseSetId
        }
      )
      const areaData = resArea.data.data
      this.acceptSetAreaGrid = areaData.gird || []
    },
    openSelectAcceptSetAreaModal() {
      this.$refs.selectAcceptSetAreaModal.show()
    },
    closeSelectAcceptSetAreaModal() {
      this.$refs.selectAcceptSetArea.clear()
    },
    onSelectAcceptSetArea(SelectAreaTable) {
      SelectAreaTable.forEach(data => {
        const findUnit = this.acceptSetAreaGrid.find(
          x => x.organ == data.organId
        )
        if (findUnit != null) {
          findUnit.zip = data.zip
          findUnit.zipName = data.zipName
        } else {
          this.acceptSetAreaGrid.push({
            organ: data.organId,
            organName: data.organName,
            zip: data.zip,
            zipName: data.zipName
          })
        }
      })
    },
    closeChoose() {
      this.$refs.choosePeopleModal.hide()
    },
    openChoosePeople() {
      this.$refs.choosePeopleModal.show()
      this.$refs.choosePeople.getHomeData()
    },

    showDecidePeople(selectData) {
      console.log(selectData)
      this.$refs.choosePeopleModal.hide()
      selectData.selectedTable.forEach(item => {
        const findOfficer = this.acceptSetOfficerGrid.find(
          x => x.userId == item.userId
        )
        if (!findOfficer) {
          this.acceptSetOfficerGrid.push({
            userId: item.userId,
            userName: item.name,
            organName: item.organName,
            unitName: item.unitName
          })
        }
      })
    },
    showDelAcceptSetConfirm(delIndex, delType) {
      this.delAcceptSetIndex = delIndex
      this.delAcceptSetType = delType
      this.$refs.confirmAcceptSetDelete.show()
    },
    delLocalAcceptSet() {
      this.$refs.confirmAcceptSetDelete.hide()
      switch (this.delAcceptSetType) {
        case 'UNIT':
          this.acceptSetUnitGrid.splice(this.delAcceptSetIndex, 1)
          break
        case 'OFFICER':
          this.acceptSetOfficerGrid.splice(this.delAcceptSetIndex, 1)
          break
        case 'AREA':
          this.acceptSetAreaGrid.splice(this.delAcceptSetIndex, 1)
          break
      }
    },
    async saveApplyAcceptSetUnit(setGrid) {
      await this.$api.management.saveInternetApplyAcceptSetUnit({
        caseSetId: this.caseSetId,
        grid: setGrid
      })
    },
    async saveInternetApplyAcceptSetOfficer(setGrid) {
      await this.$api.management.saveInternetApplyAcceptSetOfficer({
        caseSetId: this.caseSetId,
        grid: setGrid
      })
    },
    async saveInternetApplyAcceptSetArea(setGrid) {
      await this.$api.management.saveInternetApplyAcceptSetArea({
        caseSetId: this.caseSetId,
        grid: setGrid
      })
    },
    async saveInternetApplyAcceptSet() {
      switch (this.acceptSetComboBox.selectedVal) {
        case 'UNIT':
          await this.saveApplyAcceptSetUnit(
            this.acceptSetUnitGrid.map(x => x.organ)
          )
          await this.saveInternetApplyAcceptSetOfficer([])
          await this.saveInternetApplyAcceptSetArea([])
          break
        case 'OFFICER':
          await this.saveApplyAcceptSetUnit([])
          await this.saveInternetApplyAcceptSetOfficer(
            this.acceptSetOfficerGrid.map(x => x.userId)
          )
          await this.saveInternetApplyAcceptSetArea([])
          break
        case 'AREA':
          await this.saveApplyAcceptSetUnit([])
          await this.saveInternetApplyAcceptSetOfficer([])
          await this.saveInternetApplyAcceptSetArea(
            this.acceptSetAreaGrid.map(item => ({
              organ: item.organ,
              zip: item.zipName
            }))
          )
          break
      }
    },
    openSelectOrganModal() {
      this.$refs.selectOrganModal.show()
    },
    closeSelectOrganModal() {
      this.$refs.selectOrgan.clear()
    },
    onSelectOrgan(selectTable) {
      this.$refs.selectOrganModal.hide()
      selectTable.forEach(item => {
        const findOrgan = this.acceptSetUnitGrid.find(
          x => x.organ == item.organId
        )
        if (!findOrgan) {
          this.acceptSetUnitGrid.push({
            organ: item.organId,
            organName: item.organName
          })
        }
      })
    },

    ////////////受理機關設定 end//////////////////////

    ////////////受理期間設定 start///////////////////
    async getInternetApplyAcceptDateHome() {
      const res = await this.$api.management.getInternetApplyAcceptDateHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data

      this.dateStart = data.dateStart || '0101'
      this.dateEnd = data.dateEnd || '0202'
    },
    ///////////受理期間設定 end////////////////////

    ///////////限辦期限設定 start//////////////////
    async getInternetApplyLimitPeriodHome() {
      const res = await this.$api.management.getInternetApplyLimitPeriodHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      if (data.limitPeriod) {
        this.limitPeriod = +data.limitPeriod
      }

      if (data.mail) {
        this.mailArray = data.mail.split(',')
      }
    },
    deleteMail(index) {
      this.mailArray.splice(index, 1)
    },
    async addMail() {
      if (!this.newMailInput) return
      if (!(await this.$refs.observerMail.validate())) {
        return
      }
      this.mailArray.push(this.newMailInput)
      this.newMailInput = ''
    },
    ///////////限辦期限設定 end///////////////////////

    ///////////網路申辦說明設定 start///////////////////
    async getApplyDescriptionHome() {
      const res = await this.$api.management.getInternetApplyDescriptionHome({
        caseSetId: this.caseSetId
      })
      const data = res.data.data
      this.internetApplyDescHomeDataGrid = data.grid || []
    },
    showDelApplyDescConfirm(seq) {
      this.delApplyDescSeq = seq
      this.$refs.confirmApplyDescDelete.show()
    },
    async delApplyDesc() {
      await this.loadingContainer(async () => {
        const findData = this.internetApplyDescHomeDataGrid.find(
          x => x.seq == this.delApplyDescSeq
        )
        if (findData) {
          const res = await this.$api.management.deleteInternetApplyDescription(
            {
              caseSetId: this.caseSetId,
              seq: this.delApplyDescSeq,
              fileName: findData.fileName
            }
          )
          if (this.checkResSuccess(res, false)) {
            this.$refs.confirmApplyDescDelete.hide()
            await this.getApplyDescriptionHome()
          }
        }
      })
    },
    editApplyDesc(index) {
      if (index != null) {
        this.modalTitleName = '編輯網路申請說明'
        const editData = this.internetApplyDescHomeDataGrid[index]
        this.$refs.applyDescEdit.initData({ ...editData, index })
      } else {
        this.modalTitleName = '新增網路申請說明'
      }
      this.$refs.applyDescEditModal.show()
    },
    async confirmApplyDescEdit(data) {
      await this.loadingContainer(async () => {
        let uploadFileName = ''

        if (data.uploadFile != null) {
          const res = await this.$api.management.uploadInternetApplyDescriptionAttachment(
            {
              caseSetId: this.caseSetId,
              file: data.uploadFile
            }
          )
          if (this.checkResSuccess(res, false)) {
            const data = res.data.data
            uploadFileName = data.fileName
          }
        } else {
          uploadFileName = data.fileName
        }
        if (uploadFileName == '' && data.uploadFile != null) {
          return
        }
        const resSave = await this.$api.management.saveInternetApplyDescription(
          {
            caseSetId: this.caseSetId,
            title: data.title,
            contentHtml: data.contentHtml,
            fileName: uploadFileName,
            seq: data.seq
          }
        )
        this.$refs.applyDescEditModal.hide()
        if (this.checkResSuccess(resSave, false)) {
          await this.getApplyDescriptionHome()
        }
      })
    },
    closeApplyDescEditModal() {
      this.$refs.applyDescEdit.clear()
    },
    ///////////網路申辦說明設定 end/////////////////

    /** 取得檢核流程的必填欄位錯誤訊息陣列 */
    getCheckFlowRequiredMsgArray() {
      let isPass = true
      let emptyMsg = []
      if (this.caseFlow != 'A') {
        if (!this.serviceHtml) {
          emptyMsg.push('請輸入服務宣告')
        }
      }
      if (this.isShowAcceptSet) {
        if (this.acceptSetComboBox.selectedVal == 'UNIT') {
          if (this.acceptSetUnitGrid.length == 0) {
            emptyMsg.push('請新增受理機關')
          }
        }
        if (this.acceptSetComboBox.selectedVal == 'OFFICER') {
          if (this.acceptSetOfficerGrid.length == 0) {
            emptyMsg.push('請新增受理承辦人員')
          }
        }

        if (this.acceptSetComboBox.selectedVal == 'AREA') {
          if (this.acceptSetAreaGrid.length == 0) {
            emptyMsg.push('請新增區受理機關')
          }
        }
      }

      if (this.caseFlow == 'B3') {
        if (this.limitPeriod == 0) {
          emptyMsg.push('請設定限辦期限')
        }
        if (this.mailArray.length == 0) {
          emptyMsg.push('請設定逾期通知郵件')
        }
      }

      return emptyMsg
    },
    getVerifyCheckBoxValue() {
      let checkedList = []
      const ckList = document.getElementsByClassName('identityVerifyCheckBox')
      ckList.forEach(item => {
        if (item.checked) {
          checkedList.push(item.value)
        }
      })
      return checkedList.join(',')
    },
    async saveAllData() {
      let pass = true
      try {
        await this.$api.management.saveInternetApply({
          caseSetId: this.caseSetId,
          isServiceHtml: this.checkedServiceHtmlVal.length > 0,
          serviceHtml: this.serviceHtml,
          // 如果是動態流程就走受理機關類型就走承辦人
          acceptSet: this.isFlowCaseType
            ? 'OFFICER'
            : this.acceptSetComboBox.selectedVal,
          dataStart: this.dateStart,
          dataEnd: this.dateEnd,
          limitedPeriod: this.limitPeriod,
          mail: this.mailArray.join(','),
          //checkType: this.selectedIdentityVerifyList.join(',')
          checkType: this.getVerifyCheckBoxValue()
        })
        /** 不等於A才存 */
        if (this.caseFlow != 'A') {
          // 儲存受理機關
          await this.saveInternetApplyAcceptSet()
        }
      } catch (error) {
        pass = false
      }
      return pass
    }
  }
}
</script>
<style lang="scss" scoped>
.btn-blue:hover {
  color: #fff !important;
  background-color: #007bffaf !important;
  border-color: #007bffaf !important;
}

.btn-delete:hover {
  color: #fff !important;
  background-color: #f2f8feaf !important;
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
</style>
