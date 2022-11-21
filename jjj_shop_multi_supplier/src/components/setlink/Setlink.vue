<template>
  <!--
        作者：luoyiming
        时间：2020-01-08
        描述：超链接选择
    -->
  <el-dialog title="超链接设置" :visible.sync="dialogVisible" @close="dialogFormVisible" :close-on-click-modal="false" :close-on-press-escape="false">
    <!--内容-->
    <el-tabs type="border-card" v-model="activeName">
      <el-tab-pane label="产品" name="product">
        <Product v-if="activeName == 'product'" @changeData="activeDataFunc"></Product>
      </el-tab-pane>
    </el-tabs>
    <div slot="footer" class="dialog-footer d-b-c">
      <div class="flex-1">
        <div v-if="activeData != null" class="d-s-s d-c tl">
          <p class="text-ellipsis setlink-set-link">
            <span>当前链接：</span>
            <span class="gray9">{{ activeData.type }}</span>
            <span class="p-0-10 gray">/</span>
            <span class="blue">{{ activeData.name }}</span>
          </p>
          <p class="text-ellipsis gray" style="font-size: 10px;">{{ activeData.url }}</p>
        </div>
        <div v-else class="tl">
          暂无
        </div>
      </div>
      <div class="setlink-footer-btn">
        <el-button size="small" @click="dialogFormVisible(false)">取 消</el-button>
        <el-button size="small" type="primary" @click="dialogFormVisible(true)">确 定</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Product from './part/Product.vue';
export default {
  components: {
    Product
  },
  data() {
    return {
      /*是否显示*/
      dialogVisible: true,
      /*选中的链接*/
      activeData: null,
      activeName: 'product'
    };
  },
  props: ['is_linkset'],
  created() {
    this.dialogVisible = this.is_linkset;
  },
  methods: {
    /*关闭弹窗*/
    dialogFormVisible(e) {
      if (e) {
        this.$emit('closeDialog', this.activeData);
      } else {
        this.$emit('closeDialog', null);
      }
    },

    /*页面返回值*/
    activeDataFunc(e) {
      this.activeData = e;
    }
  }
};
</script>

<style>
.marketing-box .el-tabs__item {
  font-size: 12px;
}
.setlink-footer-btn{ width: 160px;}
.setlink-set-link{ width: 500px;}
</style>
