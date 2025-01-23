<template>
  <!--------------------------- 主页 ---------------------------------------------->
  <div class="main-content" style="width: 70%; margin: 20px auto; ">
    <!----------------------------------- 第一部分 ---------------------------------->
    <div style="display: flex; grid-gap: 20px;">
      <!-------------------------------- 轮播图 ----------------------------------->
      <div style="flex: 1;">
        <el-carousel height="300px" style=" border-radius: 5px;">
          <el-carousel-item v-for="item in data.sideshowData" :key="item.id">
            <img :src="item.img" alt="" style="height: 300px; width: 100%; object-fit: contain; ">
          </el-carousel-item>
        </el-carousel>
      </div>
      <!-------------------------------- 登录注册 ------------------------------------>
      <div style="width: 360px; height: 300px; background-color: #F5F5F5; border-radius: 5px;">
        <div style="display: flex; flex-direction: column; justify-content: center; margin-top: 100px;">
          <el-button @click="navTo('/login')" type="info" style="margin: 0 50px 40px 50px;"><el-icon
              style="margin-right: 10px;">
              <UserFilled />
            </el-icon>用户登录</el-button>
          <el-button @click="navTo('/register')" type="primary" style="margin: 0 50px 0 50px;"><el-icon
              style="margin-right: 10px;">
              <User />
            </el-icon>用户注册</el-button>
        </div>
      </div>
    </div>
    <!----------------------------------- 第二部分 ------------------------------------------>
    <!---------------------------- 菜品信息区域 ------------------------->
    <div style="margin-top: 20px;">
      <div style="display: flex; align-items: center; grid-gap: 20px;">
        <!-------------- 菜品分类 -------------->
        <div
          style="width: 100px; height: 40px; line-height: 40px; padding-left: 10px; font-size: 18px; border-left: 5px solid #008B8B; background: linear-gradient(to right, #00CDCD, white); ">
          菜品分类</div>
        <!-------------- 详细菜品分类信息 -------------->
        <div style="flex: 1;">
          <el-button :type="data.categoryId === null ? 'primary' : ''" @click="changeType(null)">全部</el-button>
          <el-button :type="item.id === data.categoryId ? 'primary' : ''" @click="changeType(item.id)"
            v-for="item in data.categoryData" :key="item.id">{{ item.title }}</el-button>
        </div>
      </div>
      <div style="margin-top: 20px; display: flex; grid-gap: 40px;">
        <!-------------- 菜品信息 -------------->
        <div style="flex: 1; padding: 5px;">
          <el-row :gutter="10">
            <el-col :span="6" v-for="item in data.dishTypeData" :key="item.id">
              <div class="front_card">
                <img @click="navTo('/front/dishDetail?id=' + item.id)" :src="item.img" alt=""
                  style="width: 100%; height: 130px; object-fit: cover; border-top-left-radius: 6px; border-top-right-radius: 6px; cursor: pointer;" >
                <div style="padding: 10px;">
                  <div style="font-size: 20px;">{{ item.name }}</div>
                  <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.content }}</div>
                </div>
                <div style="margin-top: 5px; display: flex; align-items: center; padding: 0 5px;">
                  <div style="margin-right: 50px; font-size: 25px; color: red;">￥ {{ item.price }}</div>
                  <div style="flex: 1; text-align: right;">剩余：{{ item.num }} 份</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
        <!-------------- 菜品搜索记录 -------------->
        <div style="width:200px; background-color: red; height: 200px;"></div>
      </div>
    </div>
    <!------------------------- 显示菜品信息分页区域 ------------------------>
    <div style="margin-top: 10px;" v-if="data.total">
      <el-pagination size="small" @current-change="loadDish" background layout="total, prev, pager, next"
        :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>
    <div style="margin-top: 20px; width: 100px; height: 40px; line-height: 40px; padding-left: 10px; font-size: 18px; border-left: 5px solid #008B8B; background: linear-gradient(to right, #00CDCD, white); ">
      热门菜品</div>
      <div style="margin-top: 10px;">
        <el-row :gutter="20">
            <el-col :span="6" v-for="item in data.dishSalesData" :key="item.id" style="margin-bottom: 10px;">
              <div class="front_card">
                <img @click="navTo('/front/dishDetail?id=' + item.id)" :src="item.img" alt=""
                  style="width: 100%; height: 200px; object-fit: cover; border-top-left-radius: 6px; border-top-right-radius: 6px; cursor: pointer;">
                <div style="padding: 10px;">
                  <div style="font-size: 20px;">{{ item.name }}</div>
                  <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.content }}</div>
                </div>
                <div style="margin-top: 5px; display: flex; align-items: center; padding: 0 5px;">
                  <div style="margin-right: 50px; font-size: 25px; color: red;">￥ {{ item.price }}</div>
                  <div style="flex: 1; text-align: right;">剩余：{{ item.num }} 份</div>
                </div>
              </div>
            </el-col>
          </el-row>
      </div>
  </div>
</template>


<script setup>
import { reactive } from 'vue';
import { User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
import request from '@/utils/request'

//定义轮播图的图片数据
const data = reactive({
  sideshowData: [],
  categoryData: [],
  categoryId: null,
  dishTypeData: [],
  dishSalesData: [],
  pageNum: 1,
  pageSize: 4,
  total: 0,
})

//加载轮播图数据的函数
const loadSideshow = () => {
  request.get('/sideshow/selectAll').then(res => {
    if (res.code === '200') {
      data.sideshowData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadSideshow()

//跳转到指定页面的函数
const navTo = (url) => {
  setInterval(() => {
    location.href = url
  }, 300)
}

//查询分类的函数
const loadCategory = () => {
  request.get('/category/selectAll').then(res => {
    if (res.code === '200') {
      data.categoryData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadCategory()

//获取分类菜品信息的函数
const changeType = (id) => {
  data.categoryId = id
  loadDish()
}

//加载菜品详细信息的函数
const loadDish = () => {
  request.get('/dish/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      categoryId: data.categoryId,
    }
  }).then(res => {
    if (res.code === '200') {
      data.dishTypeData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadDish()

//加载根据菜品销量查菜品的函数
const loadDishSales = () => {
  request.get('/dish/selectBySales').then(res => {
    if (res.code === '200') {
      data.dishSalesData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadDishSales()
</script>