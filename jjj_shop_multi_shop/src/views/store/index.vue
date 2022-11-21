<template>
  <!--
      	作者：wangxw
      	时间：2020-09-14
      	描述：插件中心-门店
      -->
  <div class="common-seach-wrap">

    <!--订单核销记录-->
    <Order v-if="activeName=='order'"></Order>
    <!--店员列表-->
    <Clerk v-if="activeName=='clerk'"></Clerk>
    <!--门店列表-->
    <Store v-if="activeName=='store'"></Store>
  </div>
</template>
<script>
  import bus from '@/utils/eventBus.js';
  import Order from './order/index.vue';
  import Clerk from './clerk/index.vue';
  import Store from './store/index.vue';
  export default {
    components: {
      Order,
      Clerk,
      Store
    },
    data() {
      return {
        /*是否加载完成*/
        loading: true,
        form: {},
        /*参数*/
        param: {},
        /*当前选中*/
        activeName: 'store',
        /*切换数组原始数据*/
      sourceList: [
        {
            key: 'order',
            value: '订单核销记录',
            path:'/store/order/index'
        },
          {
              key: 'clerk',
              value: '店员列表',
              path:'/store/clerk/index'
          },
          {
              key: 'store',
              value: '门店列表',
              path:'/store/store/index'
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
          tab_type:'store'
      }
      bus.$emit('tabData', params);

    },
    beforeDestroy () {
        //发送类别切换
        bus.$emit('tabData', {active: null,tab_type:'store', list: []});
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
