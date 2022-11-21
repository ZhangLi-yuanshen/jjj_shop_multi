<template>
  <!--
        作者：luoyiming
        时间：2019-10-25
        描述：权限-管理员列表
    -->
  <div class="user">
    <!--添加管理员-->
    <div class="common-level-rail">
      <el-button size="small" type="primary" icon="el-icon-plus" @click="addClick" v-auth="'/appsetting/appupdate/add'">添加升级</el-button>
    </div>

    <!--内容-->
    <div class="product-content">
      <div class="table-wrap">
        <el-table size="small" :data="tableData" border style="width: 100%" v-loading="loading">
          <el-table-column prop="versionAndroid" label="android版本号"></el-table-column>
          <el-table-column prop="versionIos" label="ios版本号"></el-table-column>
          <el-table-column prop="wgtUrl" label="热更包"></el-table-column>
          <el-table-column prop="pkgUrlAndroid" label="全量包-android"></el-table-column>
          <el-table-column prop="pkgUrlIos" label="全量包-ios"></el-table-column>
          <el-table-column prop="createTime" label="添加时间"></el-table-column>
          <el-table-column fixed="right" label="操作" width="90">
            <template slot-scope="scope">
              <el-button @click="editClick(scope.row)" type="text" size="small" v-auth="'/appsetting/appupdate/edit'">编辑</el-button>
              <el-button @click="deleteClick(scope.row)" type="text" size="small" v-auth="'/appsetting/appupdate/delete'">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!--分页-->
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
          :current-page="curPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="totalDataNumber"
        ></el-pagination>
      </div>
    </div>

    <Add :open="open_add" @close="closeAdd"></Add>
    <Edit :open="open_edit" :model="curModel" @close="closeEdit"></Edit>

  </div>
</template>

<script>
import AppSettingApi from '@/api/appsetting.js';
import Add from './update/Add.vue';
import Edit from './update/Edit.vue';
export default {
  components: {
    Add,
    Edit
  },
  data() {
    return {
      /*是否加载完成*/
      loading: true,
      /*列表数据*/
      tableData: [],
      /*一页多少条*/
      pageSize: 15,
      /*一共多少条数据*/
      totalDataNumber: 0,
      /*当前是第几页*/
      curPage: 1,
      /*是否打开添加弹窗*/
      open_add: false,
      /*是否打开编辑弹窗*/
      open_edit: false,
      /*当前编辑的对象*/
      curModel: {},
    };
  },
  created() {
    /*获取列表*/
    this.getTableList();
  },
  methods: {
    /*选择第几页*/
    handleCurrentChange(val) {
      let self = this;
      self.curPage = val;
      self.loading = true;
      self.getTableList();
    },

    /*每页多少条*/
    handleSizeChange(val) {
      this.curPage = 1;
      this.pageSize = val;
      this.getTableList();
    },

    /*获取列表*/
    getTableList() {
      let self = this;
      let Params = {};
      Params.pageIndex = self.curPage;
      Params.pageSize = self.pageSize;
      AppSettingApi.appUpdateList(Params, true)
        .then(res => {
          self.loading = false;
          self.tableData = res.data.records;
          self.totalDataNumber = res.data.total;
        })
        .catch(error => {});
    },

    /*打开添加*/
    addClick() {
        this.open_add=true;
    },

    /*关闭添加*/
    closeAdd(e){
      this.open_add=false;
      if(e.type=='success'){
        this.getTableList();
      }
    },

    /*打开编辑*/
    editClick(row) {
      this.curModel = row;
      this.open_edit=true;
    },

    /*关闭添加*/
    closeEdit(e){
      this.open_edit=false;
      if(e.type=='success'){
        this.getTableList();
      }
    },

    /*删除*/
    deleteClick(row) {
      let self = this;
      self
        .$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          self.loading = true;
          AppSettingApi.delAppUpdate(
            {
              updateId: row.updateId
            },
            true
          )
            .then(data => {
              self.loading = false;
              self.$message({
                message: '恭喜你，删除成功',
                type: 'success'
              });
              //刷新页面
              self.getTableList();
            })
            .catch(error => {
              self.loading = false;
            });
        })
        .catch(() => {});
    }
  }
};
</script>

<style></style>
