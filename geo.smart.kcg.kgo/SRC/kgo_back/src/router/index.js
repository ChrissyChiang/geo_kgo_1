import Vue from 'vue'
import VueRouter from 'vue-router'
import AppMain from '@/AppMain.vue'
import Login from '@/Login.vue'
import store from '@/store'
import * as types from '@/store/mutations_types.js'
import { _ } from 'lodash'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    // name: 'AppMain',
    component: AppMain,
    meta: {
      keepAlive: false
    },
    children: [
      {
        path: '',
        name: 'announcement',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "announcement" */ '../views/Announcement/index.vue'
          )
      },
      {
        path: 'classifyManagement',
        name: 'classifyManagement',
        meta: {
          keepAlive: false
          //catheTo: ['addClassify']
        },
        component: () =>
          import(
            /* webpackChunkName: "classifyManagement" */ '../views/ClassifyManagement/index.vue'
          )
      },
      {
        path: 'classifyManagement/addClassify/:id',
        name: 'addClassify',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "addClassify" */ '../views/ClassifyManagement/addClassify.vue'
          )
      },
      {
        path: 'accountManagement',
        name: 'accountManagement',
        meta: {
          keepAlive: true,
          catheTo: ['addAccount']
        },
        component: () =>
          import(
            /* webpackChunkName: "accountManagement" */ '../views/AccountManagement/index.vue'
          )
      },
      {
        path: 'accountManagement/addAccount/:id',
        name: 'addAccount',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "addAccount" */ '../views/AccountManagement/addAccount.vue'
          )
      },
      /*{
        path: 'Announcement',
        name: 'Announcement',
        meta: {
          keepAlive: false
        },
        component: () =>
          import( '../views/Announcement/index.vue')
      },*/
      {
        path: 'questionManagement',
        name: 'questionManagement',
        meta: {
          keepAlive: false
          //catheTo: ['addQuestion']
        },
        component: () =>
          import(
            /* webpackChunkName: "questionManagement" */ '../views/QuestionManagement/index.vue'
          )
      },
      {
        path: 'questionManagement/addQuestion/:id',
        name: 'addQuestion',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "addQuestion" */ '../views/QuestionManagement/addQuestion.vue'
          )
      },
      {
        path: 'vocabularySetting',
        name: 'vocabularySetting',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "vocabularySetting" */ '../views/VocabularySetting/index.vue'
          )
      },
      {
        path: 'vocabularySetting/addVocabulary/:id',
        name: 'addVocabulary',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "addVocabulary" */ '../views/VocabularySetting/addVocabulary.vue'
          )
      },
      {
        //機關科室管理
        path: 'organUnitManagement',
        name: 'organUnitManagement',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "organUnitManagement" */ '../views/OrganUnitManagement/index.vue'
          )
      },
      {
        //機關科室管理新增維護
        path: 'organUnitManagement/addOrganUnit/:id',
        name: 'addOrganUnit',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "addOrganUnit" */ '../views/OrganUnitManagement/addOrganUnit.vue'
          )
      },
      {
        //任務及公告管理
        path: 'announcementManagement',
        name: 'announcementManagement',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "announcementManagement" */ '../views/AnnouncementManagement/index.vue'
          )
      },
      {
        //任務及公告管理-新增任務
        path: 'announcementManagement/addTask/:id/:release',
        name: 'addTask',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "addTask" */ '../views/AnnouncementManagement/addTask.vue'
          )
      },
      {
        //任務及公告管理-新增公告
        path: 'announcementManagement/addAnnouncement/:id/:release',
        name: 'addAnnouncement',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "addAnnouncement" */ '../views/AnnouncementManagement/addAnnouncement.vue'
          )
      },
      {
        //服務申請
        path: 'serviceApplication',
        name: 'serviceApplication',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "serviceApplication" */ '../views/ServiceApplication/index.vue'
          )
      },
      {
        //服務申請-申請權限
        path: 'serviceApplication/addAuthority/',
        name: 'addAuthority',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "addAuthority" */ '../views/ServiceApplication/addAuthority.vue'
          )
      },
      {
        //案件管理
        path: 'caseManagement',
        name: 'caseManagement',
        meta: {
          keepAlive: true
        },
        component: () =>
          import(
            /* webpackChunkName: "caseManagement" */ '../views/CaseManagement/index.vue'
          )
      },
      {
        //案件管理-編輯頁
        path: 'caseManagement/caseAdd',
        name: 'caseManagement_add',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "caseManagement_add" */ '../views/CaseManagement/caseAdd.vue'
          )
      },
      {
        //案件管理-編輯頁
        path: 'caseManagement/caseEdit/:id',
        name: 'caseManagement_edit',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "caseManagement_edit" */ '../views/CaseManagement/caseEdit.vue'
          )
      },
      {
        //熱門搜尋
        path: 'popularSearchManagement',
        name: 'popularSearchManagement',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "popularSearchManagement" */ '../views/PopularSearchManagement/index.vue'
          )
      },
      {
        //待簽收匣
        path: 'signWaiting',
        name: 'signWaiting',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "signWaiting" */ '../views/SignWaiting/index.vue'
          )
      },
      {
        //待簽收匣-檢視服務申辦
        path: 'signWaiting/viewSignCase/:id',
        name: 'viewSignCase',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "viewSignCase" */ '../views/SignWaiting/viewSignCase.vue'
          )
      },
      {
        //待簽收匣-檢視權限申請
        path: 'signWaiting/viewSignAuthority/:id',
        name: 'viewSignAuthority',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "viewSignAuthority" */ '../views/SignWaiting/viewSignAuthority.vue'
          )
      },
      {
        //待審核匣
        path: 'reviewWaiting',
        name: 'reviewWaiting',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "reviewWaiting" */ '../views/ReviewWaiting/index.vue'
          )
      },
      {
        //待審核匣-審核服務案件申辦
        path: 'reviewWaiting/reviewCase/:id/:taskId/:acceptSet',
        name: 'reviewCase',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "reviewCase" */ '../views/ReviewWaiting/reviewCase.vue'
          )
      },
      {
        //待審核匣-權限申請
        path: 'reviewWaiting/reviewAuthority/:id/:taskId',
        name: 'reviewAuthority',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "reviewAuthority" */ '../views/ReviewWaiting/reviewAuthority.vue'
          )
      },
      {
        //已審核匣
        path: 'reviewAlready',
        name: 'reviewAlready',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "reviewAlready" */ '../views/ReviewAlready/index.vue'
          )
      },
      {
        //已審核匣-檢視服務申辦
        path: 'reviewAlready/showReviewedCase/:id',
        name: 'showReviewedCase',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "showReviewedCase" */ '../views/ReviewAlready/showReviewedCase.vue'
          )
      },
      {
        //已審核匣-檢視權限申請
        path: 'reviewAlready/showReviewedAuthority/:id',
        name: 'showReviewedAuthority',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "showReviewedAuthority" */ '../views/ReviewAlready/showReviewedAuthority.vue'
          )
      },
      {
        //案件檢視
        path: 'caseView',
        name: 'caseView',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "caseView" */ '../views/CaseView/index.vue'
          )
      },
      {
        //案件檢視-檢視服務申辦
        path: 'caseView/viewCase/:id/:type',
        name: 'viewCase',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "viewCase" */ '../views/CaseView/viewCase.vue'
          )
      },
      {
        //案件檢視-檢視權限申請
        path: 'caseView/viewAuthority/:id',
        name: 'viewAuthority',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "viewAuthority" */ '../views/CaseView/viewAuthority.vue'
          )
      },
      {
        //案件異動管理
        path: 'caseChangeManagement',
        name: 'caseChangeManagement',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "caseChangeManagement" */ '../views/CaseChangeManagement/index.vue'
          )
      },
      {
        //參數設定
        path: 'parameterSetting',
        name: 'parameterSetting',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "parameterSetting" */ '../views/ParameterSetting/index.vue'
          )
      },
      {
        //報表管理-前台軌跡
        path: 'reportManagement/front',
        name: 'frontUseTrack',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "frontUseTrack" */ '../views/ReportManagement/front.vue'
          )
      },
      {
        //報表管理- 後台軌跡
        path: 'reportManagement/back',
        name: 'backUseTrack',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "backUseTrack" */ '../views/ReportManagement/back.vue'
          )
      },
      {
        //報表管理- 案件軌跡
        path: 'reportManagement/case',
        name: 'caseUseTrack',
        meta: {
          keepAlive: false
          //catheTo: ['addVocabulary']
        },
        component: () =>
          import(
            /* webpackChunkName: "caseUseTrack" */ '../views/ReportManagement/case.vue'
          )
      },
      {
        // 動態流程管理
        path: 'actFlowManager',
        name: 'actFlowManager',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "actFlowManager" */ '../views/ActFlowManager/index.vue'
          )
      },
      {
        // 動態流程管理-編輯
        path: 'actFlowManager/editFlow',
        name: 'actFlowManagerEdit',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "actFlowManagerEdit" */ '../views/ActFlowManager/editFlowV2.vue'
          )
      },
      {
        //推播訊息清單頁
        path: 'messageManagement',
        name: 'messageManagement',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "MessageManagement" */ '../views/MessageManagement/index.vue'
          )
      },
      {
        // 推播訊息清單頁-編輯
        path: 'messageManagement/editMessage/:caseType/:userId/:organId',
        name: 'editMessage',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "editMessage" */ '../views/MessageManagement/editMessage.vue'
          )
      },
      {
        // 表單管理
        path: 'templateManagement',
        name: 'templateManagement',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "templateManagement" */ '../views/TemplateManagement/index.vue'
          )
      },
      {
        // 表單管理-新增表單
        path: 'templateManagement/addTemplate/:id/:name/:type',
        name: 'addTemplate',
        meta: {
          keepAlive: false
        },
        component: () =>
          import(
            /* webpackChunkName: "templateManagement" */ '../views/TemplateManagement/addTemplate.vue'
          )
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    meta: {
      keepAlive: false
    },
    component: Login
  },
  {
    path: '/mockLogin',
    name: 'mockLogin',
    meta: {
      keepAlive: false
    },
    component: () =>
      import(/* webpackChunkName: "mockLogin" */ '../MockLogin.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// const keepAliveRouterName = ['accountManagement']
router.beforeEach(async (to, from, next) => {
  const toPathName = to.path
  const toRouterName = to.name

  const ENV = process.env.NODE_ENV

  if (to.matched.length === 0) {
    return
  }

  if (ENV === 'production' && toRouterName == 'mockLogin') {
    // todo 之後要關閉production
    next()
    return
  }

  if (toPathName != '/mockLogin' && toPathName != '/login') {
    let isUserPass = await store.dispatch('getLoginUser')
    const { isPass } = await store.dispatch('getMenu')
    if (isUserPass && isPass) {
      store.state.menuList.forEach(menu => {
        if (toPathName == '/') {
          store.commit(types.SET_MENU_ROOT_NAME, menu.name)
        } else {
          menu.childs.forEach(itemSub => {
            if (itemSub.url != '' && toPathName.indexOf(itemSub.url) >= 0) {
              store.commit(types.SET_MENU_ROOT_NAME, menu.name)
              store.commit(types.SET_MENU_ROOT_ID, menu.functionId)
              store.commit(types.SET_MENU_SUB_NAME, itemSub.name)
              store.commit(types.SET_MENU_SUB_ID, itemSub.seq)
            }
          })
        }
      })
    }
  }
  next()
})

/** 清除指定頁面快取 */
export function clearKeepAliveRouter(clearKeepAliveName) {
  routes.forEach(item => {
    if (item.path == '/') {
      item.children.forEach(subitem => {
        if (subitem.name && clearKeepAliveName == subitem.name) {
          subitem.meta.keepAlive = false
        }
      })
    }
  })

  const newRouter = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
  router.matcher = newRouter.matcher // the relevant part
}

/** 重置所有Router快取狀態 */
export function resetRouter() {
  routes.forEach(item => {
    if (item.path == '/') {
      item.children.forEach(subitem => {
        if (subitem.name) {
          subitem.meta.keepAlive = true
        }
      })
    }
  })

  const newRouter = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
  router.matcher = newRouter.matcher // the relevant part
}

export default router
