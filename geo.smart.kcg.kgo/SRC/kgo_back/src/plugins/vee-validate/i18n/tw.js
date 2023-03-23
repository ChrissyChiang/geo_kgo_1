import tw from 'vee-validate/dist/locale/zh_TW.json'
export const validateLang = {
  generalField: '此欄位',
  fields: {
    required: '此欄位為必填!',
    max: '字數不可超過{length}字',
    url_start: '網址開頭需為 {start}',
    num: '僅可輸入數字',
    file_name_max: '檔名＋副檔名限制：{max}字元',
    equal_value: '數值必需等於{digital}',
    full_year: '請輸入正確西元年格式',
    email: '請輸入正確email格式',
    id: '請輸入正確身分證字號格式',
    phone: '請輸入正確手機格式',
    taxId: '請輸入正確統一編號格式',
    telephone: '請輸入正確室內電話格式',
    input_date: '請選擇日期',
    must_select: '此項目為必選!',
    url: '請輸入正確網址'
  },
  validation: tw.messages
}
