<template>
    <div class="tabs">
        <el-tag 
        v-for="item in tags" 
        :key="item.path" 
        :effect="$router.name === item.name ? 'dark' : 'plain'"
        >
            {{ item.label }}
        </el-tag>
    </div>
</template>
<script>
import { mapState, mapMutations } from 'vuex';
export default {
    name: 'commonTab',
    dara() {
        return {}
    }, computed: {
        ...mapState({
            tags: (state) => state.tab.tabsList
        }),
    },
};
</script>
<style lang="less" scoped>
.tabs {
    position: relative;
    //height: 30px;
    overflow: hidden;
    background: #fff;
    //padding-right: 120px;
    box-shadow: 0 5px 10px #ddd;
    padding-bottom: 5px;
    padding-top: 5px;
    padding-left: 10px;

    .el-tag {
        margin-right: 15px;
        cursor: pointer
    }
}
</style>
