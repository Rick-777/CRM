export default {
    state: {
        // Control menu state
        isCollapse: false,
        tabsList: [//breadcrumb
            {
                path: '/',
                name: 'firstPage',
                label: 'First Page',
                icon: 's-home',
                url: 'firstPage'
            }
        ]
    },
    mutations: {
        collapseChange(state) {
            state.isCollapse = !state.isCollapse
        },menuChange(state,val){
            //Update breadcrumb val menu object data
            if(val.name != 'main'){
                const index = state.tabsList.findIndex(item => item.name == val.name)
                if(index === -1){
                    state.tabsList.push(val)
                }
                
            }
        },closeTag(state,val){
            const index = state.tabsList.findIndex(item => item.name == val.name)
            state.tabsList.splice(index,1)
        }
    }
}