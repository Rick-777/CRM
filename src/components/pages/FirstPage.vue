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
            <el-col :span="16" style="padding-left: 10px">
                <el-card class="box-card system_bulletin">
                    <div slot="header" class="system_title">
                        <span>系统公告</span>
                    </div>
                    <div v-for="item in systemData" :key="item.id" class="system_context item">
                        <div>{{ item.title }}</div>
                        <div><i :class="`el-icon-${item.icon}`"> {{ item.date }}</i></div>
                    </div>
                </el-card>
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
            }],
            systemData: [
                {
                    id: 1,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
                {
                    id: 2,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
                {
                    id: 3,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
                {
                    id: 4,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
                {
                    id: 5,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
                {
                    id: 6,
                    date: "2022-10-21 09:00",
                    title: "【内部分享】如何建立良好的客户关系",
                    icon: 'timer'
                },
            ]

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

.system_bulletin {
    .system_title {
        font-size: 22px;
        color: #000000;
    }

    .system_context {
        padding-bottom: 10px;
        margin-bottom: 10px;
        border-bottom: 1px solid rgb(242, 242, 242);
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
}
</style>