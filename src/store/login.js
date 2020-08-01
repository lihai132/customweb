export default {
    state: {
        DispatchData: {},
        CustomerData: {},
        DispatchList: [],
        CustomerList: [],
        LogingType: "",

        url: "https://trade.gdzxjy.net",
        // url: "http://192.168.0.230:9010",
        // url: "http://192.168.0.66:9010",
        key: "",
        addressList: [],
        name:""
       
    },
    mutations: {
        setDispatchData(state, n) {
            state.DispatchData = n;
        },
        setDispatchList(state, n) {
            state.DispatchList = n;
        },
        setCustomerData(state, n) {
            state.CustomerData = n;
        },
        setCustomerList(state, n) {
            state.CustomerList = n;
        },
        // setDispatchList(state, n) {
        //     state.DispatchList = n;
        // },
        setLogingType(state, n) {
            state.LogingType = n;
        },
        setkey(state, n) {
            state.key = n;
        },
        setaddressList(state, n) {
            state.addressList = n;
        },
        setUrl(state, n) {
            state.url = n;
        },
        setName(state, n) {
            state.name = n;
        }
    }
}