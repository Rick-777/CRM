<template>
    <dev>
        <el-row>
            <el-col :span="8">
                <el-card class="box-card">
                    <div class="user">
                        <img src="@/assets/images/user1.png"/>
                    
                    <div class="userinfo">
                        <p class="name">Admin</p>
                        <p class="access">Super Manager</p>
                    </div>
                </div>
                    <div>
                        Login User Info
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
        padding-bottom: 20px;
        border:1px solid #999;
        display: flex;
        align-items: center;
        img{
            margin-left: 40px;
            width:150px;
            height: 150px;
            border-radius: 50%;
        }
        .userinfo{
            margin-left: 40px;
            .name{
                font-size: 24px;
            }.access{
                color:#999;
                margin-top: 0px;
            }
        }
    }
</style>