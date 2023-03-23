# vue開發環境建置步驟


## 先決條件

在開始之前，請確保你的開發環境已經包含了 Node.js® 和 npm 包管理器。
* * *

## Node.js

Vue CLI 需要 Node.js 8.9 或更高版本 (推荐 8.11.0+)。

想要檢查你的版本，請在終端/控制台窗口中運行 node -v 命令。

要想安裝 Node.js，請到Node.js官網下載[安裝程式](https://nodejs.org/en/download/) 。
* * *

## 安裝Vue Cli

請打開終端/控制台窗口，並輸入下列命令：

`npm install -g @vue/cli`
* * *

## 安裝專案相關模組 

請打開終端/控制台窗口，並到專案路徑底下，輸入下列命令：

`npm i`
* * *

## Api路徑設定

development設定檔：`.env.development`

local設定檔：`.env.local`

sit設定檔：`.env.sit`

uat設定檔：`.env.uat`


參數說明：

api路徑設定：`VUE_APP_API`

網站標題：`VUE_APP_TITLE`

* * *

## 開發環境執行

請打開終端/控制台窗口，並到專案路徑底下，輸入下列命令：

dev環境指令：`npm run serve`

local環境指令：`npm run local`
* * *

## 佈版程式編譯

請打開終端/控制台窗口，並到專案路徑底下，輸入下列命令：

`npm run build`
* * *

## 程式規範自動修正語法

請打開終端/控制台窗口，並到專案路徑底下，輸入下列命令：

`npm run lint`
* * *
* * *


# 一次GO專案開發規範


## 應該使用let或const來宣告變數或常數

變數應使用let來宣告，不可變動常數應使用const來宣告

```
let flag = 'open' 
flag = 'close'

const userName = 'test'
```
* * *

## 程式每段結尾不需要分號


```
let flag = 'open' 
```
* * *

## views資料夾命名規則與使用

每頁router功能應建立一個views資料夾，命名規則為開頭大寫駝峰式命名，進入點應為index.vue

> views
>> Login
>>> index.vue
* * *

## view功能太過龐大時，應該抽離為組件

當功能太龐大時，html與js會過長不好維護，應抽離為組件，放於components的資料夾中，組件命名方式為開頭大寫駝峰式命名

> views
>> CaseList
>>> components
>>>> CaseSearch.vue
>>
>> index.vue
* * *

## Vue組件全域註冊或全域js組件import應該放於plugins資料夾中，以小寫命名

> plugins
>> multi-select.js

>> bootstrap.js

* * *

## 組件的 data 必須是一個函數

```
data() {
    return {
      tableData: []
    }
}, 
```
* * *

## 組件的 props 定義應描述詳細，並應該註解描述說明

```
props: {
    /** 案件編號 */
    caseId: {
      type: String,
      default: null
    },
    /** 案件類型 */
    caseType: {
      type: String,
      default: ''
    }
},
```
* * *

## v-for用法，除了template tag，其餘都應綁定屬性:key並設定唯一值

```
<p v-for="(item, index) in companyList" :key="index">
    {{ item }}
</p>
```
* * *

## 絕對不能把 v-if或v-show 和 v-for 同時用在同一個元素上

v-for 比 v-if 有更高的優先級，即使只顯示一筆資料時，也會將整個列表重新渲染，會造成效能上的影響。

應使用computed來處理，篩選應顯示的資料來更新，才是比較好的做法。

```
<p v-for="(item, index) in tabList" :key="index">
    {{ item.tabName }}
</p>

computed: {
    tabList() {
        return this.tabs.filter(tab => tab.isShow)
    }     
}
```
* * *

## 組件樣式應該設置作用域

組件樣式若不增加作用域，就為全域樣式宣告，容易汙染全局。

```
<style lang="scss" scoped>
.full-width {
  width: 100%;
}
</style>
```

## 組件component命名規則應為開頭大寫駝峰式命名方式

> components
>> CompanyCode.vue

* * *

## 模板 template 中的組件命名規則

在Html的template模板中應使用kebab-case規則。

```
<company-code :company-list="companyList" />
```
* * *

## 組件 props 命名規則

在模板 template 中應使用 kebab-case命名規則。

在宣告props應使用camelCase命名規則。


```
<template>
.
.
<company-code :company-list="companyList" />
.
.
</template>
```

```
export default {
.
.
props: {
    /** 公司代碼列表 */
    companyList: {
      type: Array,
      default: () => []
    }
  }
.
.
}
```
* * *

## 避免混用指令縮寫 (: 表示 v-bind: 和 @ 表示 v-on:)

```
不好的寫法

<button
    v-on:click"onClick"
    @focus="onFocus">
</button>

```

```
應該使用同樣的縮寫方式

<button
    @click"onClick"
    @focus="onFocus">
</button>

```
* * *

## router載入頁面方式應使用懶加載

當打包構建應用時，JavaScript 包會變得非常大，影響頁面加載。如果我們能把不同路由對應的組件分割成不同的代碼塊，然後當路由被訪問的時候才加載對應組件，這樣就能更加提高效能了。

```
  {
    path: '',
    name: 'Home',
    meta: {
      keepAlive: false
    },
    component: () =>
      import(/* webpackChunkName: "Home" */ '../views/Home/index.vue')
  },

```
## router對於有主頁面跟次頁面功能，要有關聯結構

```
案件主功能頁
/case

案件編輯頁
/case/edit/:id

```

* * *


## 不應隨意更動assets/css/_styles.scss樣式，應該提出與設計師討論，或是用複寫的方式來更動


```
<style lang="scss" scoped>
.btn-primary {
    background-color: #1138a3 !important;
    border-width: 0px;
    transition: 0.3s;
}
</style> 
```
* * *


