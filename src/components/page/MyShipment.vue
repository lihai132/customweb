<template>
    <div>我的发货</div>
</template>
<script>
export default {
    methods: {
        getData() {
            let action = this.$store.state.Login.url + '/30001';
            this.loginData = JSON.parse(this.$store.state.Login.loginData);
            console.log(action);
            var bd = {
                tid: this.loginData.tid,

                zid: this.startId,
                xid: this.endId,
                ct: '',
                cl: '',
                te: '',
                tte: '',
                zdy: '',
                pg: 1,
                sz: 100
            };

            this.$axios
                .post(action, {
                    hd: {
                        pi: 30001,
                        si: this.loginData.si,
                        sq: 8
                    },
                    bd: bd
                })
                .then(res => {
                    let data = JSON.parse(res.data.bd);
                    let tableData = data.olst;
                    console.log(tableData);
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
};
</script>