<template>
  <div class="hello">
    <el-container>
    <el-aside width="200px">
      <Aside></Aside>
    </el-aside>
    <el-container>
      <el-header>Header</el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  </div>
</template>

<script>

import Aside from '@/components/common/Aside.vue'

export default {
    name: "HelloWorld",
    data() {
        return {
            msg: "Welcome to Your Vue.js App"
        };
    },components: {
    Aside
    
}
}
</script>
<style scoped>

</style>