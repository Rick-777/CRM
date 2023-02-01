<template>
    <dev>
        Role Management
    </dev>
</template>

<script>
export default{
    name:'sysRole',
    data(){
        return{}
    },mounted(){
        this.$http.get('https://localhost:8082/lib/sys/sysUser/list').then((res)=>{
            console.log(res)
        })
    }
}</script>