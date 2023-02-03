<template>
    <el-card class="box-card">
        <dev class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.msg" clearable placeholder="User Name/Operation">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="getDataList">Find</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="dataList" border style="width: 100%">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="ID">
                </el-table-column>
                <el-table-column prop="userName" label="User Name">
                </el-table-column>
                <el-table-column prop="operation" label="User Operation">
                </el-table-column>
                <el-table-column prop="method" label="Method">
                </el-table-column>
                <el-table-column prop="params" label="Params">
                </el-table-column>
                <el-table-column prop="time" label="Execution Time(mm)">
                </el-table-column>
                <el-table-column prop="ip" label="IP">
                </el-table-column>
                <el-table-column prop="createDate" label="Create Date">
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
    name: 'sysLog',
    data() {
        
        return {
            dataForm: {
                msg: ""
            }, dataList: [],
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
                    msg: this.dataForm.msg
                }

            }
            this.$http.get("/sys/sysLog/list", params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
                this.dataListLoading = false
            })
        }, 
    },
    mounted() {
        this.getDataList();
    }
}</script>