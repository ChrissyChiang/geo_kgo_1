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
      <app-navigation page-title="網站導覽" />
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <div class="apply-item">
          <div class="sitemap">
            <div class="sitemap-text">
              <p>
                本網站依無障礙網頁設計原則建置，網站的主要內容分為三大區塊：
              </p>
              <p>1. 上方功能區塊、2. 中央內容區塊、3.下方功能區塊。</p>
              <p>本網站的快速鍵﹝Accesskey﹞設定如下：</p>
              <p>
                Alt+U：右上方功能區塊，包括回首頁、網站導覽、網站搜尋、字體選擇等。
              </p>
              <p>Alt+C：中央內容區塊，為本頁主要內容區。</p>
              <p>Alt+Z：下方功能區塊。</p>
              <p>
                如果您的瀏覽器是Firefox，快速鍵的使用方法是
                Shift+Alt+(快速鍵字母)，例如
                Shift+Alt+C會跳至網頁中央區塊，以此類推。
              </p>

              <p>
                ※
                當本網站項目頁籤無法以滑鼠點選時，您可利用以下鍵盤操作方式瀏覽資料
              </p>
              <p>← → or ↑↓：按左右鍵或上下鍵移動標籤順序。</p>
              <p>Home or End→：可直接跳至標籤第一項或者最後一項。</p>
              <p>
                Tab：停留於該標籤後,可利用Tab鍵跳至內容瀏覽該筆資料，遇到radio按鈕時請配合使←
                → or↑↓鍵移動項目順序。
              </p>
              <p>
                Tab + Shift：按Tab +
                Shift可往回跳至上一筆資料；當跳回至標籤項目時您可繼續利用← →
                or↑↓鍵移動標籤順序。
              </p>
            </div>

            <div class="sitemap-link">
              <ul>
                <li><a href="/">1.首頁</a></li>
                <li class="pl-3">
                  <span>1-1選擇機關</span>
                  <a
                    v-for="item in departmentMenu"
                    :key="item.value"
                    :title="item.name"
                    :href="`/apply/${item.value}?type=1`"
                  >
                    {{ item.name }}
                  </a>

                  <span>1-2選擇身分</span>
                  <a
                    v-for="item in roleTypeMenu"
                    :key="item.value"
                    :title="item.name"
                    :href="`/apply/${item.value}?type=2`"
                  >
                    {{ item.name }}
                  </a>
                  <span>1-3選擇服務</span>
                  <a
                    v-for="item in serviceMenu"
                    :key="item.value"
                    :title="item.name"
                    :href="`/apply/${item.value}?type=3`"
                  >
                    {{ item.name }}
                  </a>
                </li>
                <li>
                  <a href="/case/search" title="2.案件進度查詢">
                    2.案件進度查詢
                  </a>
                </li>
                <li><a href="/faq" title="3.常見問題">3.常見問題</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      departmentMenu: [],
      roleTypeMenu: [],
      serviceMenu: [],
      title: '網站導覽'
    }
  },
  head() {
    return {
      title: this.title
    }
  },

  mounted() {
    this.$nextTick(async () => {
      this.$nuxt.$loading.start()
      try {
        var departmentMenuRes = await this.$apiHome.getBidServiceMenu({
          mainType: 'Organ'
        }) //機關
        var roleTypeMenuRes = await this.$apiHome.getBidServiceMenu({
          mainType: 'Role'
        }) //角色
        var serviceMenuRes = await this.$apiHome.getBidServiceMenu({
          mainType: 'Service'
        }) //服務
        console.log('==================')
        this.departmentMenu = departmentMenuRes.data.grid || []
        this.roleTypeMenu = roleTypeMenuRes.data.grid || []
        this.serviceMenu = serviceMenuRes.data.grid || []
      } catch (error) {
        console.log(error)
      }
      this.$nuxt.$loading.finish()
    })
  }
}
</script>
