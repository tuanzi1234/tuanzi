<template>
  <div style="width: 60%; margin: 20px auto; min-height: 800px;">
    <div style="display: flex; grid-gap: 15px">
      <div class="card" style="flex: 1;">
        <img :src="data.dishData.img" alt="" style="width: 100%; height: 300px; border-radius: 5px;">
      </div>
      <div class="card" style="flex: 1; padding: 10px 20px;">
        <div style="font-size: 25px;">{{ data.dishData.name }}</div>
        <div style=" margin-top: 10px;">
          <strong style="color: red; "><span style="font-size: 25px;">￥{{ data.dishData.price }} </span> / 份</strong>
          <span style="margin-left: 10px;">剩余：{{ data.dishData.num }}份</span>
        </div>
        <div style="margin-top: 10px;">所属分类：<el-tag type="success">{{ data.dishData.categoryName }}</el-tag></div>
        <div style="margin-top: 15px; line-height: 25px;  text-align: justify;" class="line4">菜品描述：{{ data.dishData.content }}</div>
        <div style="margin-top: 20px; display: flex; align-items: center; justify-content: center; ">
          <img src="@/assets/imgs/点赞.png" alt="" style="width: 25px; height: 25px; cursor: pointer;">
          <div style="margin-left: 5px; font-size: 20px;">2</div>
          <img src="@/assets/imgs/收藏.png" alt="" style="width: 30px; height: 30px; margin-left: 40px; cursor: pointer;">
          <div style="margin-left: 3px; font-size: 20px;">2</div>
        </div>
        <div style="margin-top: 20px; text-align: center;">
          <el-button type="info" style="padding: 20px 30px; font-size: 15px; ">我想吃</el-button>
        </div>
      </div>
    </div>
    <div></div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'
import router from '@/router'


//定义菜品信息数据
const data = reactive({
  dishId: router.currentRoute.value.query.id,// 获取当前路由中的dishId查询参数
  dishData: {}
})

//加载菜品信息数据的函数
const loadDish = () => {
  request.get('/dish/selectById/' + data.dishId).then(res => {
    if (res.code === '200') {
      data.dishData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadDish()

</script>

<style scoped></style>