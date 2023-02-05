<template>
    <dev>
        <el-card class="box-card">
            <dev class="mod-role">
                <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                    <el-form-item>
                        <el-input v-model="dataForm.userName" clearable placeholder="User Name">
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="getDataList">Find</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="openDialog">Add</el-button>
                    </el-form-item>
                </el-form>
                <el-table :data="dataList" border style="width: 100%">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column prop="userId" label="ID" width="150">
                    </el-table-column>
                    <el-table-column prop="userName" label="User name" width="180">
                    </el-table-column>
                    <el-table-column prop="email" label="Email" width="280">
                    </el-table-column>
                    <el-table-column prop="mobile" label="mobile" width="280">
                    </el-table-column>
                    <el-table-column prop="status" label="status" width="280">
                        <template slot-scope="scope">
                            <span>
                                {{ scope.row.status==1?'Normal':"Disable" }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="createTime" width="180">
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary"
                                @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                            <el-button size="mini" type="danger"
                                @click="handleDelete(scope.$index, scope.row)">Disable</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                    :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize"
                    :total="totalPage" layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
                </el-pagination>
            </dev>
        </el-card>
    </dev>
</template>

<script>
export default {
    name: 'sysUser',
    data() {
        return {
            dataForm: {
                userName: ''
            },
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            dataListLoading: false,
        }
    }, methods: {
        sizeChangeHandle(val) {
            // Change num of information in current page
            this.pageSize = val;
            this.pageIndex = 1;
            this.getDataList()
        }, currentChangeHandle(val) {
            this.pageIndex = val;
            this.getDataList();
        }, getDataList() {
            if (this.dataListLoading) {
                return;
            }
            this.dataListLoading = true
            // Define params for finding
            const params = {
                params: {
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize,
                    username: this.dataForm.userName
                }

            }
            this.$http.get("/sys/sysUser/list", params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
                this.dataListLoading = false
            })
        },
    },mounter(){
        this.getDataList();
    }
}</script>