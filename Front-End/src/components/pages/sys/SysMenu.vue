<template>
    <dev>
        <el-card class="box-card">
            <dev class="mod-role">
                <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                    <el-form-item>
                        <el-input v-model="dataForm.label" clearable placeholder="Menu Name">
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="getDataList">Find</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="openDialog">Add</el-button>
                    </el-form-item>
                </el-form>
                <el-table :data="dataList" border style="width: 100%" row-key="menuId"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column prop="menuId" label="ID" width="150">
                    </el-table-column>
                    <el-table-column prop="label" label="Menu name" width="180">
                    </el-table-column>
                    <el-table-column prop="path" label="Path" width="280">
                    </el-table-column>
                    <el-table-column prop="icon" label="Icon" width="280">
                    </el-table-column>
                    <el-table-column prop="parent_id" label="Parent Menu ID" width="280">
                    </el-table-column>
                    <el-table-column label="Operation">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary"
                                @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                            
                            
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
    name: 'sysMenu',
    data() {
        
        return {
            dataForm: {
                label: ''
            },
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            dataListLoading: false,
            dialogFormVisible: false,
            dataDialogForm: {
                userId: 0,
                status: 1
            }, 
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
        }, handleEdit(index, row) {
            
        }, closeDialog() {
            
        }, handleUpdateStatus(index, row) {
            
        },
        openDialog() {
            // Open dialog
            this.dialogFormVisible = true
        }, hadleSubmitFormData(formName) {
            
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
                    username: this.dataForm.label
                }

            }
            this.$http.get("/sys/sysMenu/list", params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
                this.dataListLoading = false
            })
        },
    }, mounter() {
        this.getDataList();
    }
}</script>