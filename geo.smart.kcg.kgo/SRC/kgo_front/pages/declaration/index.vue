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
      <app-navigation page-title="政府網站資料開放宣言" />
      <!-- breadcrumb end -->

      <div class="faq-item">
        <div id="accordion-faq" class="accordion">
          <!-- Accordion item 1 -->
          <h5>政府網站資料開放宣告</h5>
          <br />
          <h5>一、授權方式及範圍：</h5>
          <br />
          <h6>
            為利各界廣為利用網站資料，高雄市政府全球資訊網上刊載之所有資料與素材，其得受著作權保護之範圍，以無償、非專屬，得再授權之方式提供公眾使用，使用者得不限時間及地域，重製、改作、編輯、公開傳輸或為其他方式之利用，開發各種產品或服務（簡稱加值衍生物），此一授權行為不會嗣後撤回，使用者亦無須取得本機關之書面或其他方式授權；然使用時，應註明出處。
          </h6>
          <br />
          <h5>二、相關事項說明：</h5>
          <br />
          <ul style="list-style-type: disc">
            <li>
              本授權範圍僅及於著作權保護之範圍，不及於其他智慧財產權利，包括但不限於專利、商標、及機關標誌之提供。
            </li>
            <li>
              當事人自行公開或依法令公開之個人資料是否得被蒐集、處理及利用，使用者須自行依照個人資料保護法之相關規定，規劃並執行法律要求之相應措施。
            </li>
            <li>
              部分的影音、圖像、樂譜、專人專案撰文或其他著作，經機關特別聲明須經同意方可使用者。
            </li>
          </ul>
          <h5>
            三、應注意不得侵害第三人之著作人格權(包括姓名表示權及禁止不當變更權)
          </h5>
          <br />
          <h5>
            四、使用本授權提供之資料與素材，不得惡意變更其相關資訊，若利用後所展示之資訊與原資料與素材不符，使用者須自負民事、
            刑事上之法律責任。
          </h5>
          <br />
          <h5>
            五、本網站之授權，並不授予使用者代表本機關建議、認可或贊同其加值衍生物之地位。
          </h5>
          <br />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import page from '~/mixins/page.js'
export default {
  mixins: [page],
  data() {
    return {
      title: '政府網站資料開放宣言'
    }
  },
  head() {
    return {
      title: this.title
    }
  }
}
</script>
