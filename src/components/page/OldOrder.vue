<template>
<div><el-upload action  :limit="2" >
        <el-button size="small" type="primary">选择图片上传,最多上传一张图片</el-button>
    </el-upload>
    
    <el-upload
  action
  :auto-upload="false"
  list-type="picture-card"
  :on-change="getFile">
  <i class="el-icon-plus"></i>
</el-upload>
    
    </div>
    
    
</template>
<script>
export default {
    data() {
        return {};
    },
    methods: {
        getBase64(file) {
            return new Promise(function(resolve, reject) {
                let reader = new FileReader();
                let imgResult = '';
                reader.readAsDataURL(file);
                reader.onload = function() {
                    imgResult = reader.result;
                };
                reader.onerror = function(error) {
                    reject(error);
                };
                reader.onloadend = function() {
                    resolve(imgResult);
                };
            });
        },
        getFile(file, fileList) {
            console.log(file.raw);
            
            // this.getBase64(file.raw).then(res => {
            //     console.log(res);
            // });
        }
    }
};
</script>