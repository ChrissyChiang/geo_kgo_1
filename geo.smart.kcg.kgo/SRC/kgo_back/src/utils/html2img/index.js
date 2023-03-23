import html2Canvas from 'html2canvas'
export async function html2Img(
  id,
  addClassName,
  removeClassName,
  widthNumber,
  fontNumber
) {
  const targetDom = $(`#${id}`)
  const copyDom = targetDom.clone()
  copyDom.css('position', 'fixed')
  copyDom.css('z-index', -1)
  if (fontNumber) {
    copyDom.css('font-size', fontNumber)
  } else {
    copyDom.css('font-size', '20px')
  }
  copyDom.css('left', 0)
  copyDom.css('top', 0)

  if (widthNumber) {
    copyDom.css('width', widthNumber)
    copyDom.css('max-width', widthNumber)
  }

  if (addClassName) {
    copyDom.addClass(addClassName)
  }
  if (removeClassName) {
    copyDom.removeClass(removeClassName)
  }

  $('body').append(copyDom)
  const canvas = await html2Canvas(copyDom[0], {
    allowTaint: true,
    tainTest: true,
    useCORS: true,
    scale: 2,
    scrollY: 0
  })
  this.imgbase = canvas.toDataURL('image/jpeg', 0.7)
  copyDom.remove()
  return this.imgbase
}
