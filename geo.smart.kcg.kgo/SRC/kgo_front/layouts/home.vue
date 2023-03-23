<template>
<div>
   <app-header />
    <main>
      <div class="container-fluid">
        <Nuxt />
      </div>
    </main>
    <div class="tips"></div>
    <app-footer />
</div>
</template>
<script>
export default {
  head() {
    return {
      // link: [
      //   { rel: 'stylesheet', href: '/css/LayoutA/app.min.css' }
      // ],
      bodyAttrs: {
        class: 'background-index'
      }
    }
  }
}
</script>
<style>
</style>
