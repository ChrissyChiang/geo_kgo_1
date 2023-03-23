/**
 * 確認 response 資料是否正確
 * @param {any} response response 資料
 */
export function checkResponse(response) {
  return response && response.status === 200 && response.data.rtnCode == '0000'
}

/**
 * 如果 responses 資料都正確，則綁定資料到指定的 component 上
 * @param {any} response response 資料
 **/
export function bindResponses(component, list, responses) {
  if (Array.isArray(list) && Array.isArray(responses)) {
    list.forEach((item, index) => {
      if (checkResponse(responses[index])) {
        component[item] = responses[index].data.data
      }
    })
  } else {
    if (checkResponse(responses)) {
      component[list] = responses.data.data
    }
  }
}

export function check(response) {
  if (checkResponse(response)) {
    return response.data.data
  }
}
