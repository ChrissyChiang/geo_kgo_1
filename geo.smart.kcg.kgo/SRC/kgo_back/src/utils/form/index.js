export function jsonToFormData(data, filesKey = []) {
  const formData = new FormData()

  for (const key in data) {
    if (filesKey.includes(key) && data[key] && data[key].length > 0) {
      const files = data[key]
      for (let i = 0; i < files.length; i++) {
        formData.append(`${key}`, files[i])
      }
    } else if (data[key] !== null) {
      formData.append(key, data[key])
    }
  }

  return formData
}
