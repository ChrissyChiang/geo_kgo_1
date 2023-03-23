<template>
  <validate-container :rules="validrule" :name="id">
    <div class="form-check-inline">
      <span v-if="fText != ''">{{ fText }}</span>
      <div class="custom-control custom-checkbox">
        <input
          type="checkbox"
          :id="id"
          class="custom-control-input"
          v-model="valueV"
          :title="bText"
          :name="'chk' + parent == '' ? id : parent"
          :disabled="disabled"
          @change="checkedchange"
          @othercheckchange="othercheckchange"
        />
        <label class="custom-control-label" :for="id">&zwnj;</label>
      </div>
      <span class v-if="bText != ''">{{ bText }}</span>
    </div>
  </validate-container>
</template>

<script>
export default {
  watch: {
    valueV() {
      if (this.valueV && this.parent && this.parent != '') {//子欄位選取時把父欄位一起選取
        document
          .getElementById(this.parent)
          .dispatchEvent(
            new CustomEvent('change', { detail: { isChildrenFire: true } })
          )
        //$('#' + this.parent).prop('checked', true)
      }
      else if(!this.valueV && this.parent && this.parent != ''){//子欄位取消時若子欄位被勾選的值小於等於0時父欄位的必選取消
        var all1 = $('input[name="' + this.parent + '"]')//取得其他同父欄位的子欄位
        var chk1 = $.grep(all1, (item, index) => item.checked).length <= 0//檢查其他子欄位的值是否有被勾選
        if(chk1){//若已無其他子欄位被勾選，則將父欄位的必填取消
          document
          .getElementById(this.parent)
          .dispatchEvent(
            new CustomEvent('change', { detail: { isChildrenFire: false } })
          )
        }
      }
      this.$emit('update:datavalue', this.valueV ? 'Y' : '')
      this.$emit('update:showvalue', this.valueV ? 'Y' : '')
    }
  },
  name: 'checkBox',
  data() {
    var v = ''
    if (this.datavalue && this.datavalue != '') {
      v = 'Y'
    }

    var rule = ''
    if (this.parent && this.parent != '' && this.required) {
      rule = 'required_parent:@' + this.parent
    } else {
      rule = this.required ? 'required' : ''
    }
    return {
      valueV: v,
      select: '',
      validrule: rule
    }
  },
  async mounted() {
    //若父驗證欄位有值則不用檢核是否有值->父驗證欄位有值代表為第4部帶回
    if (this.parent != '') {
      if ($('#' + this.parent).prop('checked')) {
        this.validrule = ''
      }
    }
  },
  methods: {
    checkedchange(e) {
      if (e.detail && e.detail.isChildrenFire) {
        this.valueV = true //把父欄位勾起
        this.validrule = 'required'//父欄位設為必填
      } else if (e.detail && !e.detail.isChildrenFire) {
        this.validrule = ''
      }
    },
    othercheckchange(e) {
      if (this.parent && this.parent != '' && this.required) {
        this.validrule = e.detail.fireCheckbox
          ? ''
          : 'required_parent:@' + this.parent
      }
    }
  },
  props: {
    parent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    value: {
      type: String,
      default: ''
    },
    selectedValue: {
      type: String,
      default: ''
    },
    required: {
      type: Boolean,
      default: true
    },
    fText: {
      type: String,
      default: ''
    },
    bText: {
      type: String,
      default: ''
    },
    datavalue: {
      type: [String, Number],
      default: ''
    },
    showvalue: {
      type: [String, Number],
      default: ''
    },
    id: {
      type: String,
      default: ''
    }
  }
}
</script>
