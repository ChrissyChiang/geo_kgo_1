<template>
  <div class="row">
    <div class="col-lg-6 index-search">
      <div class="wrap">
        <a
          class="accesskey"
          href="#accesskey_c"
          title="中央內容區塊"
          id="accesskey_c"
          accesskey="C"
          name="C"
        >
          :::
        </a>
        <div class="text-center">
          <h2>便民一路通</h2>

          <div class="form-group search-ber">
            <label for="txt_keyword" style="display: none" />
            <input
              id="txt_keyword"
              type="text"
              v-model="keyword"
              placeholder="請輸入關鍵字"
              class="form-control form-control-lg"
              title="請輸入關鍵字搜尋"
              @keyup.enter.prevent="searchKeywords"
            />
            <button
              @click="searchKeywords"
              type="button"
              class="btn search-btn"
            >
              搜尋
            </button>
          </div>
          <div class="hot-search">
            熱門關鍵字
            <nuxt-link
              v-for="item in hotKeywords"
              :key="item"
              :to="{ name: 'search', params: { keywords: item } }"
              :alt="item"
              :title="item"
            >
              {{ item }}
            </nuxt-link>

            <!-- <a href="/apply/xx" title="搜尋戶政">戶政</a>
              <a href="/apply/xx" title="搜尋地政">地政</a> -->
          </div>

          <ul class="row index-btn nav nav-tabs" id="inTab" role="tablist">
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                class="active"
                id="tab-1-tab"
                data-toggle="tab"
                href="#tab-1"
                role="tab"
                aria-controls="tab-1"
                aria-selected="true"
              >
                <span>
                  <img class="svg" src="/img/icon-1.svg" alt="" />
                </span>
                <strong>依機關別</strong>
              </a>
            </li>
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                id="tab-2-tab"
                data-toggle="tab"
                href="#tab-2"
                role="tab"
                aria-controls="tab-2"
                aria-selected="false"
              >
                <span>
                  <img class="svg" src="/img/icon-2.svg" alt="" />
                </span>
                <strong>依身份別</strong>
              </a>
            </li>
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                id="tab-3-tab"
                data-toggle="tab"
                href="#tab-3"
                role="tab"
                aria-controls="tab-3"
                aria-selected="false"
              >
                <span>
                  <img class="svg" src="/img/icon-3.svg" alt="" />
                </span>
                <strong>依服務別</strong>
              </a>
            </li>
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                id="tab-4-tab"
                data-toggle="tab"
                href="#tab-4"
                role="tab"
                aria-controls="tab-4"
                aria-selected="false"
              >
                <span>
                  <img class="svg" src="/img/icon-4.svg" alt="" />
                </span>
                <strong>智能客服</strong>
              </a>
            </li>
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                id="tab-5-tab"
                data-toggle="tab"
                href="#tab-5"
                role="tab"
                aria-controls="tab-5"
                aria-selected="false"
              >
                <span>
                  <img class="svg" src="/img/icon-5.svg" alt="" />
                </span>
                <strong>活動訊息</strong>
              </a>
            </li>
            <li class="col-4 col-sm-2 col-lg-4 items">
              <a
                id="tab-6-tab"
                data-toggle="tab"
                href="#tab-6"
                role="tab"
                aria-controls="tab-6"
                aria-selected="false"
              >
                <span>
                  <img class="svg" src="/img/icon-6.svg" alt="" />
                </span>
                <strong>案件進度</strong>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-lg-6 content-area" id="inTabContent">
      <div class="frame">
        <div
          class="tab-pane fade show active"
          id="tab-1"
          role="tabpanel"
          aria-labelledby="tab-1-tab"
        >
          <department
            ref="department"
            :dep-data="departmentMenu"
            :menu-type="'1'"
          ></department>
          <!--依機關別-->
        </div>
        <div
          class="tab-pane fade"
          id="tab-2"
          role="tabpanel"
          aria-labelledby="tab-2-tab"
        >
          <department
            ref="roleType"
            :dep-data="roleTypeMenu"
            :menu-type="'2'"
          ></department>
          <!--依身份別-->
        </div>
        <div
          class="tab-pane fade"
          id="tab-3"
          role="tabpanel"
          aria-labelledby="tab-3-tab"
        >
          <department
            ref="service"
            :dep-data="serviceMenu"
            :menu-type="'3'"
          ></department>
          <!--依服務別-->
        </div>
        <div
          class="tab-pane tab-chatbot fade"
          id="tab-4"
          role="tabpanel"
          aria-labelledby="tab-4-tab"
        >
          <!-- 智能客服 -->
          <iframe
            src="https://ttc.kcg.gov.tw/ChatWeb/chat?modules=kaohsiung#"
            frameborder="0"
            title="智能客服"
            class="smart_iframe"
          ></iframe>
          <!-- 智能客服 end-->
        </div>
        <div
          class="tab-pane fade"
          id="tab-5"
          role="tabpanel"
          aria-labelledby="tab-5-tab"
        >
          <activity-message
            ref="activityMessage"
            :message-list="messageList"
          ></activity-message>
        </div>
        <div
          class="tab-pane fade"
          id="tab-6"
          role="tabpanel"
          aria-labelledby="tab-6-tab"
        >
          <progress-query></progress-query>
        </div>
      </div>
    </div>

    <system-confirm :msg="msg" ref="modal" />
  </div>
</template>

<script>
import { ValidationProvider, extend } from 'vee-validate'
import page from '~/mixins/page.js'
import SystemConfirm from '../components/SystemConfirm.vue'
export default {
  components: { SystemConfirm },
  layout: 'home',
  mixins: [page],
  data() {
    return {
      keyword: '',
      msg: '',
      departmentMenu: [],
      roleTypeMenu: [],
      serviceMenu: [],
      messageList: [], //活動訊息
      hotKeywords: [],
      /** 是否要預選智能客服 */
      enableSmart: false
    }
  },
  async asyncData({ app, store, redirect }) {
    try {
      const res1 = await app.$apiHome.getBidServiceMenu({ mainType: 'Organ' }) //機關
      const res2 = await app.$apiHome.getBidServiceMenu({ mainType: 'Role' }) //角色
      const res3 = await app.$apiHome.getBidServiceMenu({ mainType: 'Service' }) //服務
      const resC = await app.$apiHome.getActivityMessage() //活動訊息
      let supEnable = false
      const sup = await store.dispatch('getParam', 'SUP')
      supEnable = sup === '1'
      const list1 = res1.data.grid || []
      const list2 = res2.data.grid || []
      const list3 = res3.data.grid || []
      const messageList = resC.data.grid || []
      return {
        departmentMenu: list1,
        roleTypeMenu: list2,
        serviceMenu: list3,
        messageList: messageList,
        enableSmart: supEnable
      }
    } catch (error) {
      console.log(error)
      return {
        departmentMenu: [],
        roleTypeMenu: [],
        serviceMenu: [],
        messageList: [],
        enableSmart: '0'
      }
    }
  },
  async mounted() {
    this.$nextTick(async () => {
      $(document).ready(() => {
        $('.department').slick({
          infinite: true,
          arrows: true,
          slidesToShow: 2,
          slidesToScroll: 2,
          responsive: [
            {
              breakpoint: 1251,
              settings: {
                slidesToShow: 1,
                slidesToScroll: 1
              }
            },
            {
              breakpoint: 992,
              settings: {
                slidesToShow: 2,
                slidesToScroll: 2
              }
            },
            {
              breakpoint: 641,
              settings: {
                slidesToShow: 1,
                slidesToScroll: 1
              }
            }
          ]
        })

        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
          $('.department').slick('setPosition')
        })
        if (this.$route.query.caseid) {
          setTimeout(() => {
            $('#tab-6-tab').trigger('click')
          }, 500)
        } else {
          if (this.enableSmart) {
            setTimeout(() => {
              $('#tab-4-tab').trigger('click')
            }, 500)
          }
        }
      })

      try {
        var res = await this.$apiHome.getHotSearch()
        if (res.data && res.data.keywords) {
          this.hotKeywords = res.data.keywords
        }
      } catch (error) {
        console.log(error)
      }
    })
  },
  methods: {
    async searchKeywords() {
      if (this.keyword.trim() == '') {
        this.msg = '請輸入關鍵字'
        this.$refs.modal.show()
        return
      }
      this.$router.push({
        name: 'search',
        params: {
          keywords: this.keyword
        }
      })
    }
  }
}
</script>

