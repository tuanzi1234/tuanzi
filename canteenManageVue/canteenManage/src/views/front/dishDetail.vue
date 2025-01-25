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
        <div style="margin-top: 15px; line-height: 25px;  text-align: justify;" class="line4">菜品描述：{{
          data.dishData.content }}</div>
        <div style="margin-top: 20px; display: flex; align-items: center; justify-content: center; ">
          <img @click="praise" v-if="data.isPraise" src="@/assets/imgs/点赞 (1).png" alt=""
            style="width: 25px; height: 25px; cursor: pointer;">
          <img @click="praise" v-else src="@/assets/imgs/点赞.png" alt=""
            style="width: 25px; height: 25px; cursor: pointer;">
          <div v-if="data.isPraise" style="margin-left: 5px; font-size: 20px; color:#d81e06">{{ data.praiseNum }}</div>
          <div v-else style="margin-left: 5px; font-size: 20px;">{{ data.praiseNum }}</div>
          <img @click="collect" v-if="data.isCollect" src="@/assets/imgs/收藏 (1).png" alt=""
            style="width: 30px; height: 30px; cursor: pointer; margin-left: 40px;">
          <img @click="collect" v-else src="@/assets/imgs/收藏.png" alt=""
            style="width: 30px; height: 30px; cursor: pointer; margin-left: 40px;">
            <div v-if="data.isCollect" style="margin-left: 5px; font-size: 20px; color:#0449a5">{{ data.collectNum }}</div>
            <div v-else style="margin-left: 5px; font-size: 20px;">{{ data.collectNum }}</div>
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
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  dishId: router.currentRoute.value.query.id,// 获取当前路由中的dishId查询参数
  dishData: {},
  praiseNum: 0,
  isPraise: false,
  collectNum: 0,
  isCollect: false,
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

//更新浏览记录的函数
const changeHistory = () => {
  request.post('/history/add', {
    dishId: data.dishId
  }).then(res => {
    if (res.code !== '200') {
      ElMessage.error(res.msg)
    }
  })
}
changeHistory()

//点赞的函数
const praise = () => {
  request.post('/praise/add', {
    dishId: data.dishId
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("点赞成功")
      loadPraiseNum()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//获取点赞数量
const loadPraiseNum = () => {
  request.get('/praise/selectAll', {
    params: {
      dishId: data.dishId
    }
  }).then(res => {
    if (res.code === '200') {
      data.praiseNum = res.data.length
      //找出有没有当前用户点过赞
      if (data.user.role === 'STUDENT') {
        let arr = res.data.filter(v => v.studentId === data.user.id)
        if (arr && arr.length) {
          data.isPraise = true
        } else {
          data.isPraise = false
        }
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadPraiseNum()

//收藏的函数
const collect = () => {
  request.post('/collect/add', {
    dishId: data.dishId
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("收藏成功")
      loadCollectNum()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//获取收藏数量
const loadCollectNum = () => {
  request.get('/collect/selectAll', {
    params: {
      dishId: data.dishId
    }
  }).then(res => {
    if (res.code === '200') {
      data.collectNum = res.data.length
      //找出有没有当前用户收藏
      if (data.user.role === 'STUDENT') {
        let arr = res.data.filter(v => v.studentId === data.user.id)
        if (arr && arr.length) {
          data.isCollect = true
        } else {
          data.isCollect = false
        }
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadCollectNum()
</script>

<style scoped></style>