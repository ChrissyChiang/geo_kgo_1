<template>
  <div class="demo-container">
    <h1
      style="text-align: center;margin-top:20px;margin-bottom:10px;font-size:24px;font-weight:500"
    >
      <i class="fa fa-bullhorn"></i>
      &nbsp;系統公告
    </h1>
    <div
      v-for="(item, index) in dataList"
      :key="index"
      class="panel panel-default defaultcollapse"
    >
      <div
        id="headingOne"
        class="panel-heading"
        role="tab"
        :href="'#collapseOne' + index"
        data-toggle="collapse"
        aria-expanded="true"
        :aria-controls="'collapseOne' + index"
      >
        <h4 class="panel-title" style="font-size: 1em;font-weight:500">
          <a role="button">{{ item.name }}</a>
        </h4>
      </div>
      <div
        :id="'collapseOne' + index"
        class="panel-collapse collapse"
        role="tabpanel"
        aria-labelledby="headingOne"
      >
        <div class="panel-body">
          <label class="html-content" v-html="item.content"></label>
          <div v-if="item.files !== undefined">
            <div
              v-for="(fileItem, index) in item.files"
              :key="index"
              style="display: flex; width: 450px"
            >
              <a
                style="cursor: pointer; text-decoration: underline;color:blue;padding-top: 7px"
                @click="downloadFile(fileItem.key, item.seq, fileItem.name)"
              >
                {{ fileItem.name }}
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { page } from '@/mixins'
export default {
  mixins: [page],
  data() {
    return {
      announceData: [{ name: '', content: '' }],
      fileList: []
    }
  },
  computed: {
    dataList() {
      return this.announceData
    }
  },
  async mounted() {
    this.getHomeData()
  },
  methods: {
    async getHomeData() {
      const res = await this.$api.bulletin.getAnnouncementHomeData()
      this.announceData = res.data.data.grid
    },
    async downloadFile(key, seq, name) {
      let request = {
        fileKey: key,
        functionCode: 'AnnounceM',
        releaseObject: 'B',
        seq: seq
      }
      await this.loadingContainer(async () => {
        const res = await this.$api.management.getAnnouncementManagementDownloadFile(
          request
        )
        if (name.toLocaleLowerCase().indexOf('pdf') >= 0) {
          this.$f.toPdf(res, name)
        } else if (name.toLocaleLowerCase().indexOf('png') >= 0) {
          this.$f.toPng(res, name)
        } else if (
          name.toLocaleLowerCase().indexOf('jpg') >= 0 ||
          name.toLocaleLowerCase().indexOf('jpeg') >= 0
        ) {
          this.$f.toJpg(res, name)
        } else {
          this.$f.toOdt(res, name)
        }
      })
    }
  }
}
</script>
<style lang="scss">
.html-content {
  p {
    color: black;
  }
  a:hover {
    color: #bbb8be;
  }
}
</style>
