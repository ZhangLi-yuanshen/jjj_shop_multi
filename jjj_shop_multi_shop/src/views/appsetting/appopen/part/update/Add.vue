<template>
  <!--
        作者：luoyiming
        时间：2019-10-25
        描述：权限-管理员列表-添加管理员
    -->
  <el-dialog title="会员等级" :visible.sync="dialogVisible" @close="dialogFormVisible" :close-on-click-modal="false"
    :close-on-press-escape="false">
    <!--form表单-->
    <el-form size="small" ref="form" :model="form" :label-width="formLabelWidth">
      <el-form-item label="android版本号" prop="versionAndroid">
        <el-input v-model="form.versionAndroid" placeholder="请输入android版本号"></el-input>
      </el-form-item>
      <el-form-item label="ios版本号" prop="versionIos">
        <el-input v-model="form.versionIos" placeholder="请输入ios版本号"></el-input>
      </el-form-item>
      <el-form-item label="热更新包下载地址" prop="wgtUrl">
        <el-input v-model="form.wgtUrl"></el-input>
      </el-form-item>
      <el-form-item label="安卓整包升级地址" prop="pkgUrlAndroid">
        <el-input v-model="form.pkgUrlAndroid"></el-input>
      </el-form-item>
      <el-form-item label="ios整包升级地址" prop="pkgUrlIos">
        <el-input v-model="form.pkgUrlIos"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="onSubmit" :loading="loading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import AppSettingApi from '@/api/appsetting.js';
  export default {
    data() {
      return {
        /*左边长度*/
        formLabelWidth: '120px',
        /*是否显示*/
        loading: false,
        /*是否显示*/
        dialogVisible: false,
        /*form表单对象*/
        form: {
          versionAndroid: '',
          versionIos: '',
          wgtUrl: '',
          pkgUrlAndroid: '',
          pkgUrlIos: '',
        },
      };
    },
    props: ['open'],
    watch: {
      open: function(n, o) {
        if (n != o) {
          this.dialogVisible = this.open;
        }
      }
    },
    created() {},
    methods: {
      /*添加*/
      onSubmit() {
        let self = this;
        self.loading = true;
        AppSettingApi.addAppUpdate(self.form, true)
          .then(data => {
            self.loading = false;
            self.$message({
              message: '恭喜你，添加成功',
              type: 'success'
            });
            self.dialogFormVisible(true);
          })
          .catch(error => {
            self.loading = false;
          });
      },

      /*关闭弹窗*/
      dialogFormVisible(e) {
        if (e) {
          this.$emit('close', {
            type: 'success',
            openDialog: false
          });
        } else {
          this.$emit('close', {
            type: 'error',
            openDialog: false
          });
        }
      }
    }
  };
</script>

<style></style>
