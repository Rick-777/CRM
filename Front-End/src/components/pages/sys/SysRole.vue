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
                    <el-button type="primary" @click="openDialog">Add</el-button>
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
                        <el-button size="mini" type="primary"
                            @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
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

        <el-dialog :title="dataDialogForm.roleId === 0 ? 'Add Role' : 'Edit Role'" width="35%"
            :visible.sync="dialogFormVisible" @close="closeDialog()">
            <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                <el-form-item label="Role Name" label-width="120px" prop="roleName">
                    <el-input v-model="dataDialogForm.roleName" placeholder="Role Name" style="width:300px"></el-input>
                </el-form-item>
                <el-form-item label="Description" label-width="120px" prop="remark">
                    <el-input type="textarea" v-model="dataDialogForm.remark" style="width:300px"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="hadleSubmitFormData('ruleForm')">确 定</el-button>
            </div>
        </el-dialog>
    </el-card>
</template>

<script>
export default {
    name: 'sysRole',
    data() {
        var checkRoleName = (rule, value, callback) => {
            if (this.dataDialogForm.roleId !== 0) {
                // Imply it is an edit
                callback();
            } else if (value === '') {
                callback(new Error('Please Enter Role Name'));
            } else {
                // Call back-end interface to check if role name exists
                this.$http.get("/sys/sysRole/checkRoleName?roleName=" + value).then((res) => {
                    if (res.data.data === 'fail') {
                        // Role Name does not exist, can be used
                        callback();
                    } else {
                        callback(new Error('Role Name Exists'));
                    }

                })

            }
        };
        return {
            dataForm: {
                roleName: ''
            }, dataList: [

            ],
            dataDialogForm: {
                roleId: 0,
                roleName: "",
                remark: ""
            },
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            dataListLoading: false,
            dialogFormVisible: false,
            dialogFormSubmitVisible: false,
            rules: {
                roleName: [
                    { validator: checkRoleName, trigger: 'blur' }
                ], remark: [
                    { required: true, message: 'Please Enter Description', trigger: 'blur' }
                ],
            }
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
                    roleName: this.dataForm.roleName
                }

            }
            this.$http.get("/sys/sysRole/list", params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
                this.dataListLoading = false
            })
        }, handleEdit(index, item) {
            // Open renew dialog
            this.dialogFormVisible = true
            this.dataDialogForm.roleId = item.roleId
            this.dataDialogForm.roleName = item.roleName
            this.dataDialogForm.remark = item.remark
        }, handleDelete(index, item) {
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
                .get("/sys/sysRole/deleteRole?roleId=" + item.roleId)
                .then((res) => {
                    if(res.data.data === '0'){
                        // Indecate the data cannot be deleted
                        this.$message({
                        type: 'warning',
                        message: 'This record cannot be deleted'
                    });
                    }else{
                        this.$message({
                        type: 'success',
                        message: 'Success!'
                    });
                    }
                    this.dialogFormSubmitVisible = false
                    this.getDataList()
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }, openDialog() {
            // Open dialog
            this.dialogFormVisible = true
            this.dataDialogForm.roleId = 0
            this.dataDialogForm.roleName = ''
            this.dataDialogForm.remark = ''
        }, closeDialog() {
            // Clear form
            this.dataDialogForm = {
                roleId: 0,
                roleName: "",
                remark: ""
            }
        },
        hadleSubmitFormData(formName) {
            this.updateRole(formName)
        }, updateRole(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.dialogFormSubmitVisible) {
                        return
                    }
                    this.dialogFormSubmitVisible = true

                    this.$http.post('/sys/sysRole/save', this.dataDialogForm).then((res) => {
                        // Close dialog
                        this.dialogFormVisible = false
                        // Clear form
                        this.dataDialogForm = {
                            roleId: 0,
                            roleName: "",
                            remark: ""
                        }
                        this.dialogFormSubmitVisible = false
                        this.getDataList()
                    })
                } else {
                    return false;
                }
            });

        }
    },
    mounted() {
        this.getDataList();
    }
}</script>