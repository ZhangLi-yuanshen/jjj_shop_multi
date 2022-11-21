<template>
  <!--
        作者：luoyiming
        时间：2019-10-25
        描述：权限-管理员列表-修改管理员
    -->
  <el-dialog title="修改管理员" :visible.sync="dialogVisible" @close="dialogFormVisible" :close-on-click-modal="false" :close-on-press-escape="false">
    <!--form表单-->
    <el-form size="small" ref="form" :model="form" :rules="formRules" :label-width="formLabelWidth" v-loading="loading">
      <el-form-item label="用户名" prop="userName"><el-input v-model="form.userName" placeholder="请输入用户名"></el-input></el-form-item>
      <el-form-item label="所属角色" prop="roleId">
        <el-select v-model="form.roleId" :multiple="true">
          <el-option v-for="item in roleList" :value="item.roleId" :key="item.roleId" :label="item.roleName"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="登录密码" prop="password">
        <el-input v-model="form.password" placeholder="请输入登录密码" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" placeholder="请输入确认密码" type="password"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="realName"><el-input v-model="form.realName"></el-input></el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="onSubmit" :loading="loading">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import AuthApi from '@/api/auth.js';
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
        shopUserId: 0,
        userName: '',
        realName: '',
        roleId: [],
        password: '',
        confirmPassword:''
      },
      /*form验证*/
      formRules: {
        userName: [
          {
            required: true,
            message: ' ',
            trigger: 'blur'
          }
        ],
        roleId: [
          {
            required: true,
            message: ' ',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: ' ',
            trigger: 'blur'
          }
        ],
        confirmPassword: [
          {
            required: true,
            message: ' ',
            trigger: 'blur'
          }
        ],
        realName: [
          {
            required: true,
            message: ' ',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  props: ['open','roleList','model'],
  watch: {
    open: function(n, o) {
      if (n != o) {
        this.dialogVisible = this.open;
        this.form.userName = this.model.userName;
        this.form.realName = this.model.realName;
        this.form.shopUserId = this.model.shopUserId;
        this.form.roleId = [];
        this.form.password = '';
        this.form.confirmPassword = '';
        this.model.roleList.forEach(item=>{
          this.form.roleId.push(item.roleId);
        });
      }
    }
  },
  created() {},
  methods: {
    /*修改*/
    onSubmit() {
      let self = this;
      self.loading = true;
      let params = self.form;
      AuthApi.userEdit(params, true)
        .then(data => {
          self.loading = false;
          self.$message({
            message: '恭喜你，修改成功',
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
