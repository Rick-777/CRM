<template>
    <el-card class="box-card">
        <dev class="mod-role">
            <el-form :inline="true" :model="dataForm" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model="dataForm.roleName" clearable placeholder="Role Name">
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="onSubmit">Find</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">Add</el-button>
                </el-form-item>
            </el-form>
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
            }
        }
    }, mounted() {
        this.$http.get('/sys/sysUser/list').then((res) => {
            console.log(res)
        })
    }
}</script>