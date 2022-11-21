<template>
  <div class="product-add">
    <!--form表单-->
    <el-form size="small" ref="form" :model="form" label-width="250px">
      <!--小程序设置-->
      <div class="common-form">app设置</div>
      <el-form-item label="AppID 应用ID">
        <el-input v-model="form.openappId" class="max-w460"></el-input>
      </el-form-item>
      <el-form-item label="AppSecret 应用密钥">
        <el-input v-model="form.openappSecret" class="max-w460"></el-input>
      </el-form-item>
      <el-form-item label="logo" :rules="[{required: true,message: '请输入上传logo'}]">
        <el-button @click="chooseImg">选择图片</el-button>
        <img class="mt10" v-img-url="form.logo" width="50">
        <div class="tips">logo会用在登录页和一些分享页</div>
      </el-form-item>

      <!--提交-->
      <div class="common-button-wrapper">
          <el-button type="primary" @click="onSubmit">提交</el-button>
      </div>
    </el-form>

  <!--上传图片-->
  <Upload v-if="isupload" :isupload="isupload" :config="{ total: 1 }" @returnImgs="returnImgsFunc"></Upload>
  </div>

</template>

<script>
  import AppSettingApi from '@/api/appsetting.js';
  import Upload from '@/components/file/Upload';
  export default {
    components:{
      Upload
    },
    data() {
      return {
        /*form表单数据*/
        form: {
        },
        /*是否打开图片选择*/
        isupload:false
      };
    },
    created() {
      this.getData()
    },

    methods: {
      getData() {
        let self = this;
        AppSettingApi.appopenDetail({}, true)
          .then(res => {
            if(res.data != null){
              self.form = res.data;
            }
          })
          .catch(error => {});

      },
      //提交表单
      onSubmit() {
        let self = this;
        let params = this.form;
        AppSettingApi.editAppOpen(params, true)
          .then(data => {
            self.$message({
              message: '恭喜你，设置成功',
              type: 'success'
            });
          })
          .catch(error => {

          });
      },
      /*选择图片*/
      chooseImg(){
        this.isupload=true;
      },

      /*关闭选择图片*/
      returnImgsFunc(e){
        this.isupload=false;
        this.form.logo=e[0].filePath;
      }
    }

  };
</script>

<style>
  .tips {
    color: #ccc;
  }
</style>
