<template></template>
<script>
export default {
    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.$router.push(from.path);
        });
    }
};
</script>