<template>
    <dev>
        <el-row>
            <el-col :span="8">
                <el-card class="box-card">
                    <div class="user">
                        <img src="@/assets/images/user1.png"/>
                    </div>
                    <div class="userinfo">
                        <p class="name">Admin</p>
                        <p class="access">Super Manager</p>
                    </div>
                    <div>

                    </div>
                </el-card>
            </el-col>
            <el-col :span="16">
                <div class="grid-content bg-purple-light"></div>
            </el-col>
        </el-row>
    </dev>
</template>

<script>
export default {
    name: 'firstPage',
    data() {
        return {}
    }
}</script>

<style lang="less" scoped>
    .user{
        img{
            width:150px;
            height: 150px;
        }
    }
</style>