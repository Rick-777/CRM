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
                                {{ scope.row.status == 1 ? 'Normal' : "Disable" }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="createTime" width="180">
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary"
                                @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                            <el-button size="mini" type="danger" v-if="scope.row.status === 1"
                                @click="handleUpdateStatus(scope.$index, scope.row)">Disable</el-button>
                            <el-button size="mini" type="success" v-if="scope.row.status !== 1"
                                @click="handleUpdateStatus(scope.$index, scope.row)">Valid</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle"
                    :current-page="pageIndex" :page-sizes="[5, 10, 20, 50, 100]" :page-size="pageSize"
                    :total="totalPage" layout="total, sizes, prev, pager, next, jumper" style="margin-top:30px;">
                </el-pagination>
            </dev>

            <el-dialog :title="dataDialogForm.userId === 0 ? 'Add User' : 'Edit User'" width="35%"
                :visible.sync="dialogFormVisible" @close="closeDialog()">
                <el-form :model="dataDialogForm" :rules="rules" ref="ruleForm">
                    <el-form-item label="Account" label-width="120px" prop="username">
                        <el-input v-model="dataDialogForm.username" placeholder="Account"
                            :disabled="dataDialogForm.userId > 0" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item v-if="dataDialogForm.userId === 0" label="Password" label-width="120px"
                        prop="password">
                        <el-input type="password" v-model="dataDialogForm.password" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Email" label-width="120px" prop="email">
                        <el-input v-model="dataDialogForm.email" placeholder="Email" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Mobile" label-width="120px" prop="mobile">
                        <el-input v-model="dataDialogForm.mobile" placeholder="Mobile" style="width:300px"></el-input>
                    </el-form-item>
                    <el-form-item label="Status" label-width="120px" prop="status">
                        <el-select v-model="dataDialogForm.status" placeholder="Status">
                            <el-option v-for="item in statusList" :label="item.label" :value="item.value"
                                :key="item.value"></el-option>
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
    name: 'sysUser',
    data() {
        var checkusername = (rule, value, callback) => {
            if (this.dataDialogForm.userId !== 0) {
                if (value === "") {
                    callback(new Error('Please Enter Username'));
                }
                // Imply it is an edit
                callback();
            } else if (value === "") {
                callback(new Error('Please Enter Account'));
            } else {
                // Call back-end interface to check if role name exists
                this.$http.get("/sys/sysUser/checkUserName?username=" + value).then((res) => {
                    if (res.data.data === 'fail') {
                        // User Name does not exist, can be used
                        callback();
                    } else {
                        callback(new Error('User Name Exists'));
                    }

                })

            }
        };
        var checkpassword = (rule, value, callback) => {
            if (this.dataDialogForm.userId !== 0) {

                // Imply it is an edit
                callback();
            } else if (value === "") {
                // Add user info
                if (value === '') {
                    callback(new Error("Please enter password"))
                } else {
                    callback()
                }
            }
        };
        return {
            dataForm: {
                userName: ''
            },
            dataList: [],
            pageIndex: 1,
            pageSize: 5,
            totalPage: 0,
            dataListLoading: false,
            dialogFormVisible: false,
            statusList: [
                {
                    label: 'Enable',
                    value: 1
                }, {
                    label: 'Forbidden',
                    value: 0
                }
            ],
            dataDialogForm: {
                userId: 0,
                status: 1
            }, rules: {
                username: [
                    { required: true, message: 'Please Enter Account', trigger: 'blur' },
                    { validator: checkusername, trigger: 'blur' }
                ], password: [
                    { validator: checkpassword, trigger: 'blur' }
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
        }, handleEdit(index, row) {
            // Get user info from user id, write it back to dialog
            this.$http.get("/sys/sysUser/queryUserById?userId=" + row.userId).then((res) => {
                var user = res.data.data;
                this.dataDialogForm = {
                    userId: user.userId,
                    username: user.username,
                    mobile: user.mobile,
                    status: user.status
                }
                // Open dialog
                this.dialogFormVisible = true
            })
        }, closeDialog() {
            this.dataDialogForm = {
                userId: 0,
                status: 1
            }
        }, handleUpdateStatus(index, row) {
            var msg = row.status === 1 ? 'Forbidden' : 'Enable'
            // Assemble form data
            var userStatus = {
                userId: row.userId,
                status: row.status === 1 ? 0 : 1
            }
            this.$confirm('Continue' + msg + '?', 'Notice', {
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                type: 'warning'
            }).then(() => {
                if (this.dialogFormSubmitVisible) {
                    return
                }
                this.dialogFormSubmitVisible = true
                this.$http.post('/sys/sysUser/save', userStatus).then((res) => {
                    // Close dialog
                    this.dialogFormVisible = false
                    // Clear form
                    this.dataDialogForm = {
                        userId: 0,
                    }
                    this.dialogFormSubmitVisible = false
                    this.getDataList()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: 'Cancel' + msg
                });
            });

        },
        openDialog() {
            // Open dialog
            this.dialogFormVisible = true
        }, hadleSubmitFormData(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.dialogFormSubmitVisible) {
                        return
                    }
                    this.dialogFormSubmitVisible = true

                    this.$http.post('/sys/sysUser/save', this.dataDialogForm).then((res) => {
                        // Close dialog
                        this.dialogFormVisible = false
                        // Clear form
                        this.dataDialogForm = {
                            userId: 0,
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
                    username: this.dataForm.userName
                }

            }
            this.$http.get("/sys/sysUser/list", params).then((res) => {
                this.dataList = res.data.data.list;
                this.totalPage = res.data.data.totalCount
                this.dataListLoading = false
            })
        },
    }, mounter() {
        this.getDataList();
    }
}</script>