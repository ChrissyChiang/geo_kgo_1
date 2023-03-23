<template>
  <div class="demo-container">
    <div
      v-for="(item, index) in dataList"
      :key="index"
      class="panel panel-default defaultcollapse"
    >
      <div
        id="headingOne"
        class="panel-heading"
        role="tab"
        :href="'#collapseOne' + index"
        data-toggle="collapse"
        aria-expanded="true"
        :aria-controls="'collapseOne' + index"
      >
        <h4 class="panel-title" style="font-size: 1em;font-weight:500">
          <a role="button">
            {{ item.title }}
          </a>
        </h4>
      </div>
      <div
        :id="'collapseOne' + index"
        class="panel-collapse collapse "
        role="tabpanel"
        aria-labelledby="headingOne"
      >
        <div class="panel-body">
          <table class="table">
            <tr
              v-for="(dataItem, dataIndex) in item['columnDataSet']"
              :key="dataIndex"
            >
              <td style="padding: 16px;width: 250px">
                <span v-if="dataItem.isMustKey" style="color:red;size:18px">
                  *
                </span>
                <label class="control-label">{{ dataItem.columnName }}</label>
              </td>
              <td>
                <component
                  :is="dataItem.columnType"
                  :ref="'cardList' + index"
                  :value="dataItem.columnValue"
                  :required="dataItem.isMustKey"
                ></component>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import DropDowm from './extraComponents/dropDown.vue'
import TextId from './extraComponents/textId.vue'
import TextTaxId from './extraComponents/textTaxId.vue'
import TextEmail from './extraComponents/textEmail.vue'
import TextPhone from './extraComponents/textPhone.vue'
import TextTel from './extraComponents/textTel.vue'
import TextBox from './extraComponents/textBox.vue'
import AddressInput from './extraComponents/address.vue'
import TextArea from './extraComponents/textArea.vue'
import Num from './extraComponents/textNum.vue'
import Radio from './extraComponents/radio.vue'
import Date from './extraComponents/date.vue'
import DateSE from './extraComponents/dateRange.vue'
import Time from './extraComponents/dateTime.vue'
import TimeSE from './extraComponents/dateRangeTime.vue'
import Pic from './extraComponents/fileImage.vue'
import Fil from './extraComponents/file.vue'
import LandNum from './extraComponents/landNum.vue'

import { page } from '@/mixins'
export default {
  components: {
    DropDowm,
    TextId,
    TextTaxId,
    TextEmail,
    TextPhone,
    TextTel,
    TextBox,
    AddressInput,
    TextArea,
    Num,
    Radio,
    Date,
    DateSE,
    Time,
    TimeSE,
    Pic,
    Fil,
    LandNum
  },
  mixins: [page],
  data() {
    return {
      extraData: []
    }
  },
  computed: {
    dataList() {
      return this.extraData
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      this.extraData = [
        {
          groupName: '測試群組1',
          groupSeq: 68,
          columnDataSet: [
            {
              columnName: '身分證',
              columnType: 'TextId',
              isMustKey: true,
              columnId: 'id',
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '下拉選單',
              columnType: 'DropDowm',
              columnId: 'name',
              isMustKey: true,
              version: 'V1.2',
              columnValue: 'Y-是,N-否'
            },
            {
              columnName: 'Email',
              columnType: 'TextEmail',
              columnId: 'email',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '手機',
              columnType: 'TextPhone',
              columnId: 'phone',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '室內電話',
              columnType: 'TextTel',
              columnId: 'phone',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '測試單行文字',
              columnType: 'TextBox',
              columnId: 'phone',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '卡控只能數字文字',
              columnType: 'Num',
              columnId: 'Num',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            }
          ]
        },
        {
          groupName: '測試群組2',
          groupSeq: 69,
          columnDataSet: [
            {
              columnName: '地址',
              columnType: 'AddressInput',
              columnId: 'id1',
              isMustKey: false,
              version: 'V1.2',
              columnValue: 'ttttt'
            },
            {
              columnName: '多行文字',
              columnType: 'TextArea',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '單選',
              columnType: 'Radio',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: 'Y-是,N-否'
            },
            {
              columnName: '選擇日期',
              columnType: 'Date',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: '' //2020/12/12
            },
            {
              columnName: '選擇日期區間',
              columnType: 'DateSE',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: '' //2020/12/01-2020/12/31
            },
            {
              columnName: '選擇日期＆時間',
              columnType: 'Time',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: '' //2020/10/15 11:14
            },
            {
              columnName: '選擇日期＆時間區間',
              columnType: 'TimeSE',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: '' //2020/12/01 11:14-2020/12/31 12:12
            },
            {
              columnName: '附件(圖片)',
              columnType: 'Pic',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '附件(文件)',
              columnType: 'Fil',
              columnId: 'name1',
              isMustKey: true,
              version: 'V1.2',
              columnValue: ''
            },
            {
              columnName: '段小段',
              columnType: 'LandNum',
              columnId: 'name1',
              isMustKey: false,
              version: 'V1.2',
              columnValue: ''
            }
          ]
        }
      ]
    },
    getData() {
      for (let i = 0; i < this.extraData.length; i++) {
        for (let m = 0; m < this.extraData[i].columnDataSet.length; m++) {
          //console.log(this.$refs["cardList"+i][m].valueV)
        }
      }
    }
  }
}
</script>
