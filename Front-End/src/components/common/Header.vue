<template>
  <div class="header-container">
    <div class="l-content">
      <el-button icon="el-icon-menu" size="medium" style="margin-left: 20px" @click="handleMenu"></el-button>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="{ path: item.path }">{{ item.label }}</el-breadcrumb-item>
        
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <img class="user_img" src="@/assets/images/user1.png" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">Self Info</el-dropdown-item>
          <el-dropdown-item command="b">Log Out</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: "Header",
  data() {
    return {};
  },
  methods: {
    handleMenu() {
      //console.log(111)
      // 对菜单做缩放操作
      this.$store.commit("collapseChange");
    }, handleCommand(command) {
      if('b' === command){
        this.$confirm('Continue Log Out?', 'Notice', {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning'
        }).then(() => {
          // Handle log out
        sessionStorage.clear(); // Clear token info
        // Jump back to login page
        this.$router.push("/Login")
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Cancel Log Out'
          });          
        });
        
      }
    }
  }, computed: {
    ...mapState({
      tags: state => state.tab.tabsList
    })
  }
};
</script>
<style lang="less" scoped>
.header-container {
  height: 60px;
  background-color: #242f42;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .firstPage {
    color: #fff;
    font-size: 16px;
    margin-left: 10px;
    cursor: pointer;
  }

  .r-content {
    padding-right: 20px;

    .user_img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }

  .l-content {
    display: flex;
    align-items: center;

    /deep/.el-breadcrumb__item {
      .el-breadcrumb__inner {
        font-weight: normal;

        &.is-link {
          color: #666;
        }
      }

      &:last-child {
        .el-breadcrumb__inner {
          color: #fff;
        }
      }

    }
  }
}
</style>