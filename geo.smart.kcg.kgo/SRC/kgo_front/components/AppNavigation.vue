<template>
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
            <a
              href="/"
              alt="回首頁"
              title="回首頁"
            >
              回首頁
            </a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">{{pageTitle}}</li>
      </ol>
    </nav>
</template>
<script>
export default {
    props: {
        pageTitle: {
            type: String,
            default: ''
        },
    },
}
</script>