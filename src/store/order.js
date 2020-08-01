export default {
    state: {
        orderData: {},
        address:{},
        newsData:{}
       
    },
    mutations: {
        setOrderData(state, n) {
            state.orderData = n;
        },
        setNewsData(state, n) {
            state.newsData = n;
        },
       
        setAddress(state, n) {
            state.address = n;
        },
      
    }
}