import en from 'vee-validate/dist/locale/en.json'
export const validateLang = {
  generalField: 'current',
  fields: {
    required: 'This field is required!',
    max: 'Need less than or equal to {length} words definition!',
    url_start: 'URL must start with {start}.',
    num: 'Only enter numbers.',
    file_name_max: 'File name + file extension limitation: {max} characters.',
    equal_value: 'The value must be equal to {digital}',
    full_year: 'Please enter the correct year format'
  },
  validation: en.messages
}
