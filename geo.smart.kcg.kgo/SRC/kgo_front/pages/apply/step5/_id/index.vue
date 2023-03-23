<template>
  <div>
    <div class="title">
      <div class="container">
        <h2>{{ caseName }}</h2>
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
      </div>
    </div>
    <div class="container">
      <!-- breadcrumb -->
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item">
            <a href="/" title="回首頁">回首頁</a>
          </li>
          <li class="breadcrumb-item">
            <button
              class="btn-gray-outline"
              @click="goCaseList()"
              :title="rootTitle || '標題名稱'"
            >
              {{ rootTitle || '標題名稱' }}
            </button>
          </li>
          <li class="breadcrumb-item">
            <a
              :href="'/apply_form/?casesetid=' + id"
              :title="caseName || '案件名稱'"
            >
              {{ caseName || '案件名稱' }}
            </a>
          </li>
          <li class="breadcrumb-item active" aria-current="page">網路申請</li>
        </ol>
      </nav>
      <!-- breadcrumb end -->

      <div class="apply-inner">
        <div class="step">
          <ul>
            <li class="done">
              網路申請
              <br />
              同意書
            </li>
            <li class="done">
              身分
              <br />
              驗證
            </li>
            <li class="done">
              填寫
              <br />
              申請表
            </li>
            <li class="done">
              確認
              <br />
              申請內容
            </li>
            <li class="done">
              步驟
              <br />
              完成
            </li>
          </ul>
        </div>
        <form>
          <div class="apply-item">
            <h3>
              <img
                class="title-img"
                src="/img/done-img.png"
                alt="您已完成申請作業"
              />
              {{ applyUserName }} 您好!
              <strong>您已完成{{ caseName }}</strong>
            </h3>
            <div class="form-group row">
              <label class="col-sm-3 col-form-label">申請時間</label>
              <div class="col-sm-9 v-center">{{ today }}</div>
            </div>
            <div class="form-group row">
              <label class="col-sm-3 col-form-label">申請編號</label>
              <div class="col-sm-9 v-center">{{ caseId }}</div>
            </div>
          </div>
          <div class="form-group text-center btn-even">
            <h4>請妥善保存申請編號</h4>
            <a href="/" class="btn btn-lg btn-gray-light" title="回首頁按鈕">
              回首頁
            </a>
            <a :href="href" class="btn btn-lg btn-org" title="案件查詢按鈕">
              案件查詢
            </a>
            <a
              data-toggle="modal"
              href="#apply-stub"
              id="autoclick"
              class="btn btn-lg btn-org"
              title="案號下載按鈕"
            >
              案號下載
            </a>
          </div>
        </form>
      </div>
    </div>
    <div
      class="modal fade"
      id="apply-stub"
      tabindex="-1"
      role="dialog"
      aria-labelledby="apply-stubLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content p-3">
          <div class="modal-header border-0 text-center">
            <div class="modal-title w-100" id="apply-stubLabel">線上申辦</div>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <i class="fa fa-times fa-lg"></i>
            </button>
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-8">
                <table
                  class="table apply-stub-table"
                  summary="此線上申辦表格，
              第一直欄是申請人姓名，第二直欄是申請編號，第三直欄是受理機關"
                >
                  <caption>申請項目-{{ caseName }} {{ today }}</caption>
                  <tr>
                    <td>申請人姓名</td>
                    <td>{{ applyUserName }}</td>
                  </tr>
                  <tr>
                    <td>申請編號</td>
                    <td>{{ caseId }}</td>
                  </tr>
                  <tr v-if="isShowCaseOrgan">
                    <td>受理機關</td>
                    <td>{{ caseOrganName }}</td>
                  </tr>
                </table>
              </div>
              <div class="col-md-4 qrcode-item">
                <div>案件QRcode</div>

                <VueQrcode :value="href" />
                <p>
                  您可以掃描QRcode連結至案件查詢頁面輸入案件密碼後進行案件進度查詢，或連結至便民一路通首頁點選案件進度查詢進入。
                </p>
              </div>
            </div>
          </div>
          <div class="modal-footer justify-content-center">
            <button
              type="button"
              class="btn btn-gray-light"
              data-dismiss="modal"
            >
              關閉
            </button>
            <!-- <button type="button" class="btn btn-gray">列印帳單</button> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment' //format套件
import VueQrcode from '@chenfengyuan/vue-qrcode'
export default {
  components: { VueQrcode },
  head() {
    return {
      title: '步驟完成'
    }
  },
  data() {
    var params = this.$route.params

    return {
      id: params.id,
      rootTitle: params.rootTitle,
      href: '',
      today: '',
      caseId: '',
      // user: '',
      caseOrganName: '',
      isShowCaseOrgan: false,
      caseName: params.caseName,
      rootType: params.rootType,
      rootId: params.rootId,
      applyUserName: ''
    }
  },
  async asyncData({ app, store, redirect, query, params }) {
    if (!params.data) redirect('/')
    var date = params.data.data.applyDate
    var userName = params.data.data.applyUserName
    var id = params.data.data.caseId
    var formatData = moment(date).format('YYYY年MM月DD日(HH:mm)')
    var caseOrganName = params.caseOrganName
    var http = window.location.href.indexOf('https') != -1 ? 'https' : 'http'
    var href = http + '://' + window.location.host + '/?caseid=' + id
    var isShow = params.isShowCaseOrgan
    return {
      today: formatData,
      caseId: id,
      caseOrganName: caseOrganName,
      href: href,
      applyUserName: userName,
      isShowCaseOrgan: isShow
    }
  },
  mounted() {
    // var today = new Date()
    // this.today =
    //   today.getFullYear() +
    //   '年' +
    //   (today.getMonth() + 1) +
    //   '月' +
    //   today.getDate() +
    //   '日(' +
    //   today.getHours() +
    //   ':' +
    //   today.getMinutes() +
    //   ')'

    // this.user = sessionStorage.getItem('Name')

    this.$nextTick(() => {
      $('.toggle-password').click(function () {
        $(this).toggleClass('fa-eye fa-eye-slash')
        var input = $($(this).attr('toggle'))
        if (input.attr('type') == 'password') {
          input.attr('type', 'text')
        } else {
          input.attr('type', 'password')
        }
      })
    })
  },
  methods: {
    // clearSession() {
    //   sessionStorage.clear()
    // },
    goCaseList() {
      this.$router.push({
        path: '/apply/' + this.rootId,
        query: { type: this.rootType }
      })
    }
  }
}
</script>
