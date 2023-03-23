export function findOptionValue(options, isArray = false) {
  if (!options) return ''
  if (!Array.isArray(options)) return ''
  if (isArray) {
    return options
      .filter(x => x.selected && x.selected == true)
      .map(item => item.value)
  } else {
    const findSelectData = options.find(x => x.selected && x.selected == true)
    return findSelectData ? findSelectData.value : ''
  }
}
