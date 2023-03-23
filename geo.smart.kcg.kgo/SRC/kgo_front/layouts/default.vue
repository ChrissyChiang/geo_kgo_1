<template>
<div>
   <app-header />
    <main class="background-a">
        <Nuxt />
    </main>
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
        // class: 'background-index'
      }
    }
  }
}
</script>
<style>
</style>
