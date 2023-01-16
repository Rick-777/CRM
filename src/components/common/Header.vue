<template>
    <dev class="header-container">
        <div class="l-content">
            <el-button icon="el-icon-menu" 
            size="medium" 
            style="margin-left:20px;"
            @click="handleMenu"
            >
        </el-button>
            <span class="firstPage">First Page</span>
        </div>
        <div class="r-content">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <img class="user_img" src="@/assets/images/user.jpg" />
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>个人信息</el-dropdown-item>
                    <el-dropdown-item>注销</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </dev>
</template>

<script>
export default {
    name: 'Header',
    data() {
        return {}
    }
}</script>

<style lang="less" scoped>
.head-container {
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
}
</style>