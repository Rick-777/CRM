<template>
    <dev>
        <el-row>
            <el-col :span="8">
                <el-card class="box-card">
                    <div class="user">
                        <img src="@/assets/images/user1.png" />

                        <div class="userinfo">
                            <p class="name">Admin</p>
                            <p class="access">Super Manager</p>
                        </div>
                    </div>
                    <div class="login-info">
                        <p>Last Login Time:<span>2023-1-19</span></p>
                        <p>Last Login Location:<span>China</span></p>
                    </div>
                </el-card>
                <el-card class="box-card" style="margin-top:20px;">
                    <el-table :data="tableData" stripe style="width: 100%">
                        <el-table-column prop="date" label="日期" width="180">
                        </el-table-column>
                        <el-table-column prop="name" label="姓名" width="180">
                        </el-table-column>
                        <el-table-column prop="address" label="地址">
                        </el-table-column>
                    </el-table>
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
        return {
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }]
        }
    }
}</script>
<style lang="less" scoped>
.user {
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #999;
    display: flex;
    align-items: center;

    img {
        margin-left: 40px;
        width: 150px;
        height: 150px;
        border-radius: 50%;
    }

    .userinfo {
        margin-left: 40px;

        .name {
            font-size: 24px;
        }

        .access {
            color: #999;
            margin-top: 0px;
        }
    }
}

.login-info {
    p {
        font-size: 14px;
        color: #999999;
        line-height: 28px;

        span {
            color: #666666;
            margin-left: 20px;
        }
    }
}
</style>