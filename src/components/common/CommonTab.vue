<template>
    <div>
        <el-tag v-for="item in tags" :key="item.path" effect="dark">
            {{ item.label }}
        </el-tag>
    </div>
</template>
<script>
import { mapState,mapMutations } from 'vuex';
export default {
    name: 'commonTab',
    dara() {
        return {}
    },computed:{
        ...mapState({
            tags:state=>state.tab.tabsList
        })
    }
}
</script>