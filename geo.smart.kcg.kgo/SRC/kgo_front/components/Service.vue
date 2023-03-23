<template>
  <div class="wrap list-wrap">
    <h3>
      <img src="/img/icon-3-title.svg" alt="" />
      選擇服務
    </h3>
    <div class="department" ref="roleRef">
      <div v-for="(item1, index) in depData" :key="index">
        <div class="item" v-for="item in item1" :key="item.id">
          <a href="#" :title="item.title" @click.prevent="goApply(item.id)">
            <i>{{ item.serviceCount }}</i>
            <span>{{ item.title }}</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      depData: []
    }
  },
  computed: {},
  /*async asyncData() {

  },*/
  async mounted() {
    this.$nextTick(() => $(this.$refs.departmentRef).slick)
    this.depData = [
      [
        { id: 'test01', title: '服務01', serviceCount: '01' },
        { id: 'test02', title: '服務02', serviceCount: '02' },
        { id: 'test03', title: '服務03', serviceCount: '02' },
        { id: 'test04', title: '服務04', serviceCount: '04' },
        { id: 'test05', title: '服務05', serviceCount: '05' },
        { id: 'test06', title: '服務06', serviceCount: '02' }
      ],
      [
        { id: 'test07', title: '服務07', serviceCount: '01' },
        { id: 'test08', title: '服務08', serviceCount: '03' },
        { id: 'test09', title: '服務09', serviceCount: '03' }
      ]
    ]
  },
  methods: {
    goApply(id) {
      this.$router.push({ path: '/apply/_id', query: { id: id } })
    }
  }
}
</script>
