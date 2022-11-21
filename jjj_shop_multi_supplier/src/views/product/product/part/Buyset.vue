<template>
  <div class="buy-set-content pl16 pr16">
    <!--积分设置-->
    <div class="common-form">积分设置</div>
    <el-form-item label="是否开启积分赠送：">
      <el-radio-group v-model="form.model.isPointsGift">
        <el-radio :label="1">开启</el-radio>
        <el-radio :label="0">关闭</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="是否允许使用积分抵扣：">
      <el-radio-group v-model="form.model.isPointsDiscount">
        <el-radio :label="1">允许</el-radio>
        <el-radio :label="0">不允许</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item v-if="form.model.isPointsDiscount == 1" label="最大抵扣积分数量：" :rules="[{ required: true, message: ' ' }]" prop="model.maxPointsDiscount">
      <el-input type="number" min="0" v-model="form.model.maxPointsDiscount" class="max-w460"></el-input>
    </el-form-item>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        
      };
    },
    created() {
      
    },
    inject: ['form'],
    methods: {
      
    }
  };
</script>

<style></style>
