<template>
  <div v-if="error.statusCode == 404" class="container">
    找不到頁面,轉跳至首頁
  </div>
  <div v-else>系統異常請洽管理人員 狀態代碼:{{ error.statusCode }}</div>
</template>

<script>
export default {
  props: ['error'],
  beforeMount() {
    if (this.error.statusCode === 404) {
      this.$router.push('/')
    }
  }
}
</script>
