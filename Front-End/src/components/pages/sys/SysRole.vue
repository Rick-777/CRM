<template>
    <el-card class="box-card">
        <dev class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.roleName" clearable placeholder="Role Name">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="getDataList">Find</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">Add</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="roleId" label="ID" width="150">
                </el-table-column>
                <el-table-column prop="roleName" label="name" width="180">
                </el-table-column>
                <el-table-column prop="remark" label="remark" width="280">
                </el-table-column>
                <el-table-column prop="createTime" label="createTime" width="180">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
            </el-pagination>

        </dev>
    </el-card>
</template>

<script>
export default {
    name: 'sysRole',
    data() {
        return {
            dataForm: {
                roleName: ''
            }, dataList: [

            ],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
        }
    }, methods: {
        sizeChangeHandle() {

        }, currentChangeHandle() {

        }, getDataList() {
            // Define params for finding
            const params = {
                params: {
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize,
                    roleName: this.dataForm.roleName
                }

            }
            this.$http.get("/sys/sysRole/list", params).then((res) => {
                console.log(res)
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
            })
        }
    },
    mounted() {
        this.getDataList();
    }
}</script>