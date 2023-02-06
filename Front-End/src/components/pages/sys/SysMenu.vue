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
                    <el-table-column prop="menuId" label="ID">
                    </el-table-column>
                    <el-table-column prop="label" label="Menu name">
                    </el-table-column>
                    <el-table-column prop="path" label="Path">
                    </el-table-column>
                    <el-table-column prop="icon" label="Icon">
                    </el-table-column>
                    <el-table-column prop="parent_id" label="Parent Menu ID">
                    </el-table-column>
                    <el-table-column label="Operation" width="160">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary"
                                @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                            <el-button size="mini" type="danger"
                                @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
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

                    <el-form-item label="Parent Menu" label-width="160px" prop="parentId">
                        <el-select v-model="dataDialogForm.parentId" placeholder="Empty if it is a parent menu"
                            clearable>
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
            }, rules: {
                label: [
                    { required: true, message: 'Please Enter Menu Name', trigger: 'blur' }
                ], orderNum: [
                    { required: true, message: 'Please Enter Order Number', trigger: 'blur' }
                ],
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
        }, handleDelete(index, row) {
            // Delete role info
            this.$confirm('This would delete this record permanently, continue?', 'Notice', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (this.dialogFormSubmitVisible) {
                    return
                }
                this.dialogFormSubmitVisible = true
                this.$http
                    .get("/sys/sysMenu/deleteMenu?menuId=" + item.menuId)
                    .then((res) => {
                        if (res.data.data === '0') {
                            // Indecate the data cannot be deleted
                            this.$message({
                                type: 'warning',
                                message: 'This record cannot be deleted'
                            });
                        } else {
                            this.$message({
                                type: 'success',
                                message: 'Success!'
                            });
                        }
                        this.dialogFormSubmitVisible = false
                        this.getDataList()
                    })

            }).catch(() => {
                this.dialogFormSubmitVisible = false
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }, handleEdit(index, row) {
            // Update data, query menu info by menu id
            this.$http.get("/sys/sysMenu/queryMenuById?menuId=" + row.menuId).then((res) => {
                // Collect data corrsponding to id
                var menu = res.data.data.menu;
                // Obtain all parent menu data
                this.options = res.data.data.parents
                this.dataDialogForm = {
                    menuId: menu.menuId, // Eject dialog to bind menuId to update
                    label: menu.label,
                    path: menu.path,
                    icon: menu.icon,
                    orderNum: menu.orderNum,
                    parentId: menu.parentId
                }
                // Open dialog
                this.dialogFormVisible = true
            })
        }, closeDialog() {

        }, handleUpdateStatus(index, row) {

        },
        openDialog() {
            // Obtain data of all parent menu
            this.$http.get("/sys/sysMenu/listParent").then((res) => {
                this.options = res.data.data;
                // Open dialog
                this.dialogFormVisible = true
            })

        }, hadleSubmitFormData(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.dialogFormSubmitVisible) {
                        return
                    }
                    this.dialogFormSubmitVisible = true

                    this.$http.post('/sys/sysMenu/save', this.dataDialogForm).then((res) => {
                        // Close dialog
                        this.dialogFormVisible = false
                        // Clear form
                        this.dataDialogForm = {
                            menuId: 0,
                        }
                        this.dialogFormSubmitVisible = false
                        this.getDataList()
                    })
                } else {
                    return false;
                }
            });
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