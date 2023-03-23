// 是否具有該線
export function hasLine(data, from, to) {
  for (let i = 0; i < data.lineList.length; i++) {
    let line = data.lineList[i]
    if (line.from === from && line.to === to) {
      return true
    }
  }
  return false
}

// 是否含有相反的線
export function hashOppositeLine(data, from, to) {
  return hasLine(data, to, from)
}

// 獲取連線
export function getConnector(jsp, from, to) {
  let connection = jsp.getConnections({
    source: from,
    target: to
  })[0]
  return connection
}

export function getNodeTypeName(nodeType) {
  switch (nodeType) {
    case '1':
      return '會簽'
      break
    case 'D':
      return '分文'
      break
    case 'A':
      return '陳核'
      break
    case 'F':
      return '結案'
      break
    default:
      return '流程狀態'
      break
  }
}
