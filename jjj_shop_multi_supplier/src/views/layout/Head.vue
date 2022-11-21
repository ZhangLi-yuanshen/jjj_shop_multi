<template>
  <!--
          作者：luoyiming
          时间：2019-10-24
          描述：后台系统头部
      -->
  <div class="common-header">
    <div class="breadcrumb">
      <!--一般的标题显示-->
      <div class="baseInfo-left-base d-s-c">
        <span class="name">{{ menu_title }}</span>
        <!--插件切换-->
        <div class="el-tabs-container">
          <el-tabs v-model="activeValue" @tab-click="tabClick">
            <el-tab-pane :label="item.value" :name="item.key" v-for="(item, index) in tabList" :key="index"></el-tab-pane>
          </el-tabs>
        </div>
      </div>

      <div class="header-navbar">
        <div class="header-navbar-icon">
          <span class="gray">当前版本：{{baseInfo.version}}</span>
        </div>
        <div class="header-navbar-icon">
          <span class="icon iconfont icon-geren9"></span>
          <span class="text ml4 blue">{{ baseInfo.user.userName }}，欢迎您！</span>
        </div>
        <div class="header-navbar-icon"><span class="gray">|</span></div>
        <div class="header-navbar-icon" @click="passwordFunc()"><span class="text">修改密码</span></div>
        <div class="header-navbar-icon login-out" @click="login_out()">
          <span class="icon iconfont icon-tuichu"></span>
          <span class="text ml4">退出</span>
        </div>
      </div>
    </div>

    <!--修改密码-->
    <UpdatePassword v-if="is_password" @close="closeFunc"></UpdatePassword>
  </div>
</template>

<script>
  import bus from '@/utils/eventBus.js';
  import AuthApi from '@/api/auth.js';
  import UserApi from '@/api/user.js';
  import {
    setCookie,
    delCookie,
    getCookie,
    deleteSessionStorage
  } from '@/utils/base.js';
  import store from '@/store';
  import UpdatePassword from './part/UpdatePassword.vue';
  export default {
    components: {
      UpdatePassword
    },
    data() {
      return {
        /*菜单名称*/
        menu_title: '菜单',
        /*切换菜单*/
        tabList: [],
        /*切换选中*/
        activeValue: 0,
        /*是否修改密码*/
        is_password: false,
        /*tab切换类别*/
        tab_type: ''
      };
    },
    inject: ['baseInfo'],
    created() {
      /*监听菜单传过来的值*/
      bus.$on('menuName', res => {
        this.menu_title = res;
      });

      /*监听传插件的值*/
      bus.$on('tabData', res => {
        this.tabList = res.list;
        this.activeValue = res.active;
        this.tab_type = res.tab_type;
      });

      //发送给其它组件头部加载完成
      bus.$emit('headLoad', true);

    },
    beforeDestroy() {
      bus.$off('menuName');
      bus.$off('tabData');
    },
    mounted() {},
    methods: {

      /*退出登录*/
      login_out() {
        this.$confirm('此操作将退出登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(() => {
            UserApi.loginOut({}, true)
              .then(data => {
                /*删除登录状态*/
                delCookie('isLogin_supplier');
                /*删除基本信息*/
                delCookie('baseInfo_supplier');
                /*删除用户菜单*/
                deleteSessionStorage('rolelist_supplier');
                /*删除用户权限*/
                deleteSessionStorage('authlist_supplier');
                /*删除用户权限*/
                deleteSessionStorage('token_supplier');
                /*返回登录页*/
                this.$router.replace({
                  path: '/login'
                });
                /*删除stores数据*/
                this.$store.commit('user/setState', {
                  key: 'roles',
                  val: null
                });
                /*刷新页面*/
                location.reload();
              })
              .catch(error => {});
          })
          .catch(() => {
            // this.$message({
            //   type: 'info',
            //   message: '已取消退出'
            // });
          });
      },

      /*点击跳转*/
      tabClick(e) {
        /*优惠券*/
        if (this.tab_type == 'coupon') {
          this.$router.push({
            path: '/operate/coupon/index',
            query: {
              type: e.name
            }
          });
        }
        /*广告*/
        if (this.tab_type == 'ad') {
          this.$router.push({
            path: '/operate/ad/index',
            query: {
              type: e.name
            }
          });
        }
        /*拼团*/
        if (this.tab_type == 'assemble') {
          this.$router.push({
            path: '/activity/assemble/index',
            query: {
              type: e.name
            }
          });
        }
        /*秒杀*/
        if (this.tab_type == 'seckill') {
          this.$router.push({
            path: '/activity/seckill/index',
            query: {
              type: e.name
            }
          });
        }
        /*砍价*/
        if (this.tab_type == 'bargain') {
          this.$router.push({
            path: '/activity/bargain/index',
            query: {
              type: e.name
            }
          });
        }


        bus.$emit('activeValue', this.activeValue);
      },

      /*修改密码*/
      passwordFunc() {
        this.is_password = true;
      },

      /*关闭修改密码*/
      closeFunc() {
        this.is_password = false;
      }
    }
  };
</script>

<style lang="scss">
  .common-header .el-tabs__nav-wrap::after {
    display: none;
  }

  .common-header .el-tabs-container {
    margin-left: 20px;
    padding-left: 20px;
    border-left: 1px solid #EEEEEE;
  }

  .common-header .el-tabs__header {
    margin-bottom: 0;
  }
  .login-out .icon-tuichu {
    color: red;
  }
  
  .header-navbar-icon .icon-geren9 {
    font-size: 20px;
  }
</style>
