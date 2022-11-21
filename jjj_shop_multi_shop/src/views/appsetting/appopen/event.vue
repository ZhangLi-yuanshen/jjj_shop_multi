<template>
  <div class="common-seach-wrap">
    <!--基础设置-->
    <Basic v-if="activeName=='basic'"></Basic>
    <!--分享设置-->
    <Share v-if="activeName=='share'"></Share>
    <!--升级设置-->
    <Update v-if="activeName=='update'"></Update>
  </div>
</template>
<script>
  import bus from '@/utils/eventBus.js';
  import Basic from './part/basic';
  import Share from './part/share';
  import Update from './part/update';
  export default {
    components: {
      Basic,
      Share,
      Update
    },
    data() {
      return {
        /*是否加载完成*/
        loading: true,
        form: {},
        /*参数*/
        param: {},
        /*当前选中*/
        activeName: 'basic',
        /*切换数组原始数据*/
        sourceList: [
            {
                key: 'basic',
                value: '基础设置',
                path:'/appsetting/appopen/index'
            },
            {
                key: 'share',
                value: '分享设置',
                path:'/appsetting/appshare/index'
            },
            {
                key: 'update',
                value: '升级管理',
                path:'/appsetting/appupdate/index'
            }
        ],
        /*权限筛选后的数据*/
        tabList:[],
      };
    },
    created() {

      this.tabList=this.authFilter();

      if(this.tabList.length>0){
        this.activeName=this.tabList[0].key;
      }

      if (this.$route.query.type != null) {
        this.activeName = this.$route.query.type;
      }

      /*监听传插件的值*/
      bus.$on('activeValue', res =>
      {
          this.activeName = res;
      });

      //发送类别切换
      let params = {
          active: this.activeName,
          list: this.tabList,
          tab_type:'appopen'
      }
      bus.$emit('tabData', params);

    },
    beforeDestroy () {
        //发送类别切换
        bus.$emit('tabData', {active: null,tab_type:'appopen', list: []});
        bus.$off('activeValue');
    },
    methods: {

      /*权限过滤*/
      authFilter(){
        let list=[];
        for(let i=0;i<this.sourceList.length;i++){
          let item=this.sourceList[i];
          if(this.$filter.isAuth(item.path)){
            list.push(item);
          }
        }
        return list;
      },

    }
  };
</script>

<style>
  .operation-wrap {
    height: 124px;
    border-radius: 8px;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    padding: 30px 30px;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -ms-flex-direction: column;
    flex-direction: column;
    overflow: hidden;
    background: #909399;
    background-size: 100% 100%;
    color: #fff;
  }
</style>
