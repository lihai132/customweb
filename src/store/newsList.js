export default {
    state: {
        List: [],
        page:"",
        time:""
       
       
    },
    mutations: {
        setList(state, n) {
            state.List = n;
        },
        setPage(state, n) {
            state.page = n;
        },
        setTime(state, n) {
            state.time = n;
        },
       
    }
}