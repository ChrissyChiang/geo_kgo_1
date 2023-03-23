<template>
  <edit-flow
    ref="editFlow"
    :edit-flow-id="flowId"
    :users-options="usersOptions"
    :is-edit="false"
    :flow-organ-id="flowOrganId"
  />
</template>
<script>
import { page } from '@/mixins'
export default {
  name: 'PrewFlow',
  mixins: [page],
  //   props: {
  //     flowId: {
  //       type: String,
  //       default: ''
  //     }
  //   },
  data() {
    return {
      flowOrganId: '',
      usersOptions: [],
      flowId: ''
    }
  },
  //   watch: {
  //     async flowId() {
  //       await this.getFolwHomeData()
  //       await this.getTaker()
  //     }
  //   },
  methods: {
    async initData(flowId) {
      this.flowId = flowId
      await this.getFolwHomeData()
      //await this.getTaker()
    },
    async getFolwHomeData() {
      if (this.flowId) {
        if (this.flowId == 'SA') {
          // SA流程預設長一個空白的
          this.$refs.editFlow.initData(
            [
              {
                taskSeq: null,
                taskOrder: 0,
                taskName: '',
                taskType: 2,
                taskAssignees: '',
                taskAssigneesCombox: {
                  options: []
                }
              }
            ],
            '',
            ''
          )
        } else {
          await this.loadingContainer(async () => {
            const res = await this.$api.management.getTaskDetail({
              flowId: this.flowId
            })
            const data = res.data.data.tpiFlow
            this.flowOrganId =
              data.organId || this.$store.state.user.userInfo.organId
            this.$refs.editFlow.initData(
              data.flowTasks,
              data.flowName,
              data.flowDesc
            )
          })
        }
      } else {
        this.flowOrganId = this.$store.state.user.userInfo.organId
      }
    }
    // async getTaker() {
    //   const res = await this.$api.share.getOrganUintUserSelectHome({
    //     organId: this.flowOrganId,
    //     unitId: this.flowOrganId
    //     //roleId: this.isCaseM ? 'CASE_M' : ''
    //   })
    //   const data = res.data.data
    //   const tableData = data.grid || []
    //   this.usersOptions = tableData.map(x => ({
    //     label: x.name,
    //     value: x.userId
    //   }))
    // }
  }
}
</script>
