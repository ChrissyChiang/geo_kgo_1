export function unescapeHTML(escapedHTML) {
  return escapedHTML
    .replace(/&lt;/g, '<')
    .replace(/&gt;/g, '>')
    .replace(/&amp;/g, '&')
    .replace(/&quot;/g, '"')
}

export function clearHTML(html) {
  if (!html) return ''
  let output = ''
  if (typeof html == 'string') {
    output = html.replace(/(<([^>]+)>)/gi, '')
  }
  return output
}

export function subString(str, len = 10) {
  if (!str) return ''
  let allStr = str
  if (allStr && allStr.length > len) {
    allStr = allStr.substr(0, len) + '...'
  }
  return allStr
}
