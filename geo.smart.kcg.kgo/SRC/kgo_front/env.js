//build dist --> npm run build:dev
module.exports = {
  /** 公司測試機(開發者模式) */
  dev: {
    MODE: 'development',
    ENV_API: 'http://192.168.10.168:8080/kgo/frontend',
    //ENV_API: 'https://www.jgallop.com/kgo/frontend',
    //ENV_API: 'http://10.20.30.104/kgo/frontend',
    PORT: 3000,
    //PORT: 8080,
    APP_PUBLIC_ID: 'KCG_5462',
    GO_INDEX_URL: 'https://ttc-test.kcg.gov.tw'
  },
  /** 正式機(開發者模式) */
  dev_kgo: {
    MODE: 'development',
    ENV_API: 'https://kgobe.kcg.gov.tw/kgo/frontend',
    PORT: 8080,
    APP_PUBLIC_ID: 'KCG_6410',
    GO_INDEX_URL: 'https://ttc.kcg.gov.tw'
  },
  /** 公司測試機(上版用) */
  tp: {
    MODE: 'production',
    ENV_API: 'http://10.20.30.104/kgo/frontend',
    PORT: 8000,
    APP_PUBLIC_ID: 'KCG_5462',
    GO_INDEX_URL: 'https://ttc-test.kcg.gov.tw'
  },
  tp8090: {
    MODE: 'production',
    ENV_API: 'http://10.20.30.104:8100/kgo/frontend',
    PORT: 8200,
    APP_PUBLIC_ID: 'KCG_5462',
    GO_INDEX_URL: 'https://ttc-test.kcg.gov.tw'
  },
  /** 市府正式機 */
  kgo: {
    MODE: 'production',
    //ENV_API: 'https://kgobe.kcg.gov.tw/kgo/frontend',
    ENV_API: 'https://kgo.kcg.gov.tw/kgo/frontend',
    PORT: 8000,
    APP_PUBLIC_ID: 'KCG_6410',
    GO_INDEX_URL: 'https://ttc.kcg.gov.tw'
  },
  /** 市府測試機 */
  kgo_test: {
    MODE: 'production',
    ENV_API: '/kgo/frontend',
    PORT: 8000,
    APP_PUBLIC_ID: 'KCG_5462',
    GO_INDEX_URL: 'https://ttc-test.kcg.gov.tw'
  }
}
