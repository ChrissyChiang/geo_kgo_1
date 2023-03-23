import { saveAs } from 'file-saver'

/**
 * 轉換成 pdf
 */
export function toPdf(res, setFileName) {
  const file = res.data
  const fileName = getFilename(res) || setFileName
  const blob = new Blob([file], { type: 'application/pdf' })
  if (isIOSPlatform()) {
    toiOSFile(file, fileName, 'application/pdf')
  } else {
    saveAs(blob, fileName)
  }
}
/**
 * 轉換成 Png
 */
export function toPng(res, setFileName) {
  const file = res
  const blob = new Blob([file], { type: 'image/png' })
  saveAs(blob, setFileName)
}
/**
 * 轉換成 Jpg
 */
export function toJpg(res, setFileName) {
  const file = res
  const blob = new Blob([file], { type: 'image/jpg' })
  saveAs(blob, setFileName)
}
/**
 * 轉換成 Odf
 */
export function toOdt(res, setFileName) {
  const file = res
  const blob = new Blob([file], {
    type: 'application/vnd.oasis.opendocument.text'
  })
  saveAs(blob, setFileName)
}

export function toZip(file, fileName) {
  const blob = new Blob([file])
  saveAs(blob, fileName)
}

export function toExcel(res, setFileName) {
  const file = res.data
  const fileName = getFilename(res) || setFileName
  if (isIOSPlatform()) {
    toiOSFile(
      file,
      fileName,
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    )
  } else {
    const blob = new Blob([file])
    saveAs(blob, fileName)
  }
}

export function toBase64ToFile(base64File, setFileName, contentType) {
  const fileName = setFileName
  const bstr = atob(base64File)
  let n = bstr.length
  let fileArray = new Uint8Array(n)

  while (n--) {
    fileArray[n] = bstr.charCodeAt(n)
  }
  const blob = new Blob([fileArray], { type: contentType })
  saveAs(blob, fileName)
}

/** 取得檔案名稱 */
export function getFilename(response) {
  const content = response.headers['content-disposition'].match(
    /filename="(.*)"/
  )
  const fileName = content ? content[1] : ''
  return fileName ? decodeURI(fileName) : ''
}

/** 是否為iOS手機載具 **/
export function isIOSPlatform() {
  return !!navigator.platform && /iPad|iPhone|iPod/.test(navigator.platform)
}

export function toiOSFile(file, fileName, contentType) {
  const blob = new Blob([file], { type: contentType })
  const URL = window.URL || window.webkitURL
  const downloadUrl = URL.createObjectURL(blob)
  window.location = downloadUrl
}
