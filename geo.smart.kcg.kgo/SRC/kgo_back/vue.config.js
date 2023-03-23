const webpack = require('webpack')
const CopyWebpackPlugin = require('copy-webpack-plugin')

const fs = require('fs')
const moment = require('moment')

if (process.env.NODE_ENV !== 'production') buildVersion()
console.log('NODE_ENV:' + process.env.NODE_ENV)

/**
 * 產生寫入版號
 */
function buildVersion() {
  const now = new Date()
  const buildNum = versionNumber(now, 'YYYY/MM/DD')
  const version = versionNumber(now, 'YYYYMMDDHHmm')
  fs.writeFile(
    'src/assets/version.json',
    WriteVersion(buildNum, version),
    function(err) {
      if (err) {
        console.log(err)
      } else {
        console.log('\nversion Number:' + buildNum)
      }
    }
  )
}

/**
 * 版號
 * @param {*} date 日期
 * @param {*} format 格式
 */
function versionNumber(date, format) {
  const now = date ? date : new Date()
  return moment(now).format(format)
}

/**
 * 版號 產生 ts 可讀資料
 * @param {*} buildNum
 * @param {*} version
 */
function WriteVersion(buildNum, version) {
  return `{"buildNum": "${buildNum}","version": ${version}}`
}

module.exports = {
  pages: {
    index: {
      entry: 'src/main.js',
      title: `${process.env.VUE_APP_TITLE}`
    }
  },
  lintOnSave: process.env.NODE_ENV !== 'production',
  productionSourceMap: process.env.NODE_ENV !== 'production',
  configureWebpack: {
    devtool: process.env.NODE_ENV !== 'production' ? 'source-map' : false,
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        moment: 'moment'
      }),
      new CopyWebpackPlugin([
        { from: './src/assets/WEB-INF', to: 'WEB-INF', toType: 'dir' }
      ])
    ]
  },
  devServer: {
    proxy: {
      '/kgo': {
        target: 'http://10.20.30.104:8080/kgo',
        //target: 'https://kgobe.kcg.gov.tw/kgo',
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/kgo': ''
        }
      }
    }
  }
}
