<template>
  <div class="wrap list-wrap">
    <h3>
      <img src="/img/icon-2-title.svg" alt="" />
      選擇身份
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
        { id: 'test01', title: '角色01', serviceCount: '01' },
        { id: 'test02', title: '角色02', serviceCount: '02' },
        { id: 'test03', title: '角色03', serviceCount: '02' },
        { id: 'test04', title: '角色04', serviceCount: '04' },
        { id: 'test05', title: '角色05', serviceCount: '05' },
        { id: 'test06', title: '角色06', serviceCount: '02' }
      ],
      [
        { id: 'test07', title: '角色07', serviceCount: '01' },
        { id: 'test08', title: '角色08', serviceCount: '03' },
        { id: 'test09', title: '角色09', serviceCount: '03' },
        { id: 'test10', title: '角色10', serviceCount: '07' }
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
