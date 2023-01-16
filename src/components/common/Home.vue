<template>
  <div class="hello">
    <el-container>
      <el-aside width="auto">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header>
          <Header></Header>
        </el-header>
        <el-main>
          <!-- Router's out-->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from '@/components/common/Aside.vue'
import Header from '@/components/common/Header.vue'

export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  }, components: {
    Aside,
    Header
  }
}
</script>
<style scoped>
.el-header {
  padding: 0 0;
}
</style>