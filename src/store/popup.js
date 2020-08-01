export default {
    state: {
        popup1: {},
        popup2: {},
        show1: false

    },
    mutations: {
        setPopup1(state, n) {
            state.popup1 = n;
        },
        setPopup2(state, n) {
            state.popup2 = n;
        },
        setShow1(state, n) {
            state.show1 = n;
        }

    }
}