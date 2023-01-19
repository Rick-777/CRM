export default{
    state:{
        // Control menu state
        isCollapse:false
    },
    mutations:{
        collapseChange(state){
            state.isCollapse = !state.isCollapse
        }
    }
}