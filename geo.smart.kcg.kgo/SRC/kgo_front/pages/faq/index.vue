<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ title }}</h2>
        <a
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          class="accesskey"
          accesskey="C"
          name="C"
        >
          :::
        </a>
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <app-navigation page-title="常見問題" />
      <!-- breadcrumb end -->

      <div class="faq-item">
        <div id="accordion-faq" class="accordion">
          <!-- Accordion item 1 -->
          <div
            v-for="(item, index) in dataList"
            :key="index"
            class="card"
            style="padding-bottom: 10px"
          >
            <div id="heading1" class="card-header rounded">
              <h3>
                <a
                  href="#"
                  data-toggle="collapse"
                  :data-target="'#collapse' + index"
                  :aria-expanded="index == 0 ? true : false"
                  :aria-controls="'collapse' + index"
                  :class="
                    index == 0
                      ? 'd-block position-relative text-uppercase collapsible-link p-2'
                      : 'd-block position-relative collapsed text-uppercase collapsible-link p-2'
                  "
                >
                  {{ item.question }}
                </a>
              </h3>
            </div>
            <div
              :id="'collapse' + index"
              :aria-labelledby="'heading' + index"
              data-parent="#accordion-faq"
              :class="index == 0 ? 'collapse show' : 'collapse'"
            >
              <div class="card-body">
                {{ item.content }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import page from '~/mixins/page.js'
export default {
  layout: 'popularQuestion',
  mixins: [page],
  async asyncData({ app, params, query, store, $cookies }) {
    const res = await app.$apiCommonQuestion.getCommonQuestion()
    let list = res.data.grid
    return { dataList: list }
  },
  mounted() {
  },
  head() {
    return {
      title: this.title
    }
  },
  data() {
    return {
      dataList: [],
      title: '常見問題'
    }
  }
}
</script>
