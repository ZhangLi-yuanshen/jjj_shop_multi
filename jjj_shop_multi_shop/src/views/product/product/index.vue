<template>
  <div class="product-list">
    <!--搜索表单-->
    <div class="common-seach-wrap">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="出售中" name="sell">
          <span slot="label">出售中 <el-tag size="mini">{{productCount.sell}}</el-tag></span>
        </el-tab-pane>
        <el-tab-pane label="仓库中" name="lower">
          <span slot="label">仓库中 <el-tag size="mini">{{productCount.lower}}</el-tag></span>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="recovery">
          <span slot="label">回收站 <el-tag size="mini">{{productCount.recovery}}</el-tag></span>
        </el-tab-pane>
        <el-tab-pane label="待审核" name="audit">
          <span slot="label">待审核 <el-tag size="mini">{{productCount.audit}}</el-tag></span>
        </el-tab-pane>
        <el-tab-pane label="未通过" name="noAudit">
          <span slot="label">未通过 <el-tag size="mini">{{productCount.noAudit}}</el-tag></span>
        </el-tab-pane>
      </el-tabs>
      <el-form size="small" :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="商品分类">
          <el-select size="small" v-model="searchForm.categoryId" placeholder="所有分类">
            <el-option label="全部" value="0"></el-option>
            <el-option v-for="(item, index) in categoryList" :key="index" :label="item.name" :value="item.categoryId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称"><el-input size="small" v-model="searchForm.productName" placeholder="请输入商品名称"></el-input></el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--内容-->
    <div class="product-content">
      <div class="table-wrap">
        <el-table size="small" :data="tableData" border style="width: 100%" v-loading="loading">
          <el-table-column prop="productName" label="产品" width="400px">
            <template slot-scope="scope">
              <div class="product-info">
                <div class="pic"><img v-img-url="scope.row.imagePath" alt="" /></div>
                <div class="info">
                  <div class="name">{{ scope.row.productName }}</div>
                  <div class="price">￥{{ scope.row.productPrice }}</div>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="categoryName" label="分类"></el-table-column>
          <el-table-column prop="salesActual" label="实际销量"></el-table-column>
          <el-table-column prop="productStock" label="库存"></el-table-column>
          <el-table-column prop="viewTimes" label="点击数"></el-table-column>
          <el-table-column label="商品属性">
            <template slot-scope="scope">
              <span v-if="scope.row.isVirtual == 0">实物商品</span>
              <span v-if="scope.row.isVirtual == 1" class="green">虚拟商品</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="发布时间"></el-table-column>
          <el-table-column prop="productSort" label="排序"></el-table-column>
          <el-table-column fixed="right" label="操作" width="90">
            <template slot-scope="scope">
              <div class="table-btn-column">
                <el-button v-if="scope.row.auditStatus==10" @click="editClick(scope.row)" type="text" size="small" v-auth="'/product/product/edit'">编辑</el-button>
                <el-button v-if="scope.row.auditStatus==0" @click="editClick(scope.row)" type="text" size="small" v-auth="'/product/product/edit'">审核</el-button>
                <el-button @click="delClick(scope.row)" type="text" size="small" v-auth="'/product/product/delete'">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
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
</template>

<script>
import PorductApi from '@/api/product.js';
export default {
  components: {},
  data() {
    return {
      /*切换菜单*/
      activeName: 'sell',
      /*切换选中值*/
      activeIndex: '0',
      /*是否正在加载*/
      loading: true,
      /*一页多少条*/
      pageSize: 15,
      /*一共多少条数据*/
      totalDataNumber: 0,
      /*当前是第几页*/
      curPage: 1,
      /*搜索参数*/
      searchForm: {
        productName: '',
        categoryId: ''
      },
      /*列表数据*/
      tableData: [],
      /*全部分类*/
      categoryList: [],
      productCount: {},
    };
  },
  created() {
    /*获取列表*/
    this.getData();
  },
  methods: {
    /*选择第几页*/
    handleCurrentChange(val) {
      let self = this;
      self.loading = true;
      self.curPage = val;
      self.getData();
    },

    /*每页多少条*/
    handleSizeChange(val) {
      this.pageSize = val;
      this.getData();
    },

    /*切换菜单*/
    handleClick(tab, event) {
      let self = this;
      self.curPage = 1;
      self.getData();
    },

    /*获取列表*/
    getData() {
      let self = this;
      let Params = self.searchForm;
      Params.pageIndex = self.curPage;
      Params.pageSize = self.pageSize;
      Params.type = self.activeName;
      self.loading = true;
      PorductApi.productList(Params, true)
        .then(res => {
          self.loading = false;
          self.tableData = res.data.productList.records;
          self.categoryList = res.data.categoryList;
          self.totalDataNumber = res.data.productList.total;
          self.productCount = res.data.productCount;
        })
        .catch(error => {
          self.loading = false;
        });
    },

    /*搜索查询*/
    onSubmit() {
      this.curPage = 1;
      this.getData();
    },

    /*打开添加*/
    addClick() {
      this.$router.push('/product/product/add');
    },

    /*打开编辑*/
    editClick(row) {
      console.log(row);
      this.$router.push({
        path: '/product/product/edit',
        query: {
          productId: row.productId,
          scene: 'edit'
        }
      });
    },

    linkClick: function(val) {
      let self = this;
      let copyText = 'pages/product/detail/detail?productId=' + val;
      self.$copyText(copyText).then(function(e) {
        self.$message({
          showClose: true,
          message: '复制成功',
          type: 'success'
        });
      }, function(e) {
        self.$message({
          showClose: true,
          message: '复制失败',
          type: 'error'
        });
      })
    },
   /*打开复制*/
     copyClick(row) {
       this.$router.push({
         path: '/product/product/edit',
         query: {
           productId: row.productId,
           scene: 'copy'
         }
       });
     },
    /*删除*/
    delClick: function(row) {
      let self = this;
      let msg = '';
      if(row.productStatus == 30){
        msg = '删除后不可恢复，确认删除该记录吗?';
      }else{
        msg = '删除后将移动到回收站，确定删除该记录吗?';
      }
      self
        .$confirm(msg, '提示', {
          type: 'warning'
        })
        .then(() => {
          PorductApi.delProduct({
            productId: row.productId
          }).then(data => {
            self.$message({
              message: '删除成功',
              type: 'success'
            });
            self.getData();
          });
        });
    },
    /*修改状态*/
    changeStatus(item,value){
      let self = this;
      let msg = '';
      if(value == 10){
        msg = '确认要上架该商品吗?';
      }else{
        msg = '确认要下架该商品吗?';
        if(self.activeName == 'recovery'){
          msg = '确认要移动该商品到仓库吗?';
        }
      }
      self.$confirm(msg, '提示', {
          type: 'warning'
        })
        .then(() => {
          PorductApi.changeStatus({
            productId: item.productId,
            state: value
          }).then(data => {
            self.$message({
              message: '操作成功',
              type: 'success'
            });
            self.getData();
          });
        });
    },
  }
};
</script>

<style>

</style>
