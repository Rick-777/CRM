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
                    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
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
            <el-dialog :title="dataDialogForm.menuId === 0 ? 'Add Menu' : 'Edit Menu'" width="35%"
                :visible.sync="dialogFormVisible" @close="closeDialog()">
                <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                    <el-form-item label="Menu Name" label-width="120px" prop="label">
                        <el-input v-model="dataDialogForm.label" placeholder="Menu Name" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Router Address" label-width="120px" prop="path">
                        <el-input v-model="dataDialogForm.path" placeholder="Router Address"
                            style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Icon" label-width="120px" prop="icon">
                        <el-input v-model="dataDialogForm.icon" placeholder="Icon" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Order Number" label-width="120px" prop="orderNum">
                        <el-input v-model="dataDialogForm.orderNum" placeholder="Order Number" type="number"
                            style="width:300px"></el-input>
                    </el-form-item>

                    <el-form-item label="Parent Menu" label-width="120px" prop="parentId">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option v-for="item in options" :key="item.menuId" :label="item.label"
                                :value="item.menuId">
                            </el-option>
                        </el-select>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="hadleSubmitFormData('ruleForm')">确 定</el-button>
                </div>
            </el-dialog>
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
                menuId: 0,
            }, options: [],
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
            // Obtain data of all parent menu
            this.$http.get("/sys/sysMenu/listParent").then((res) => {
                this.options = res.data.data.list;
                // Open dialog
            this.dialogFormVisible = true
            })
            
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