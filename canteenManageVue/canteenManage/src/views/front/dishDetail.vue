<template>
  <div style="width: 60%; margin: 20px auto; min-height: 800px;">
    <div style="display: flex; grid-gap: 15px">
      <div class="card" style="flex: 1; display: flex; align-items: center;">
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
          <div v-if="data.isCollect" style="margin-left: 5px; font-size: 20px; color:#0449a5">{{ data.collectNum }}
          </div>
          <div v-else style="margin-left: 5px; font-size: 20px;">{{ data.collectNum }}</div>
        </div>
        <div style="margin-top: 20px; text-align: center;">
          <el-button @click="myLike" type="info" style="padding: 20px 30px; font-size: 15px; ">我想吃</el-button>
        </div>
      </div>
    </div>
    <!------------------------------ 用户评价 ------------------------------------------------>
    <div class="card" style="margin-top: 10px;">
      <div style="font-size: 20px; height: 40px; line-height: 40px; border-bottom: 1px solid #cccccc;">菜品评价（{{
        data.ordersItemData.length }}）</div>
      <div v-for="item in data.ordersItemData" :key="item.id" style="margin-top: 20px; display: flex; grid-gap: 20px; align-items: center;">
        <img :src="item.studentAvatar" alt="" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover;">
        <div style="width: 100px;">{{ item.studentName }}</div>
        <div style="flex: 1;">
          <el-rate v-model="item.score" disabled show-score text-color="#ff9900" score-template="{value} 分" />
          <div style="color: #828282; line-height: 20px;">{{ item.comment }}</div>
        </div>
        <div style="width: 150px; color: #828282;">{{ item.time }}</div>
      </div>
    </div>
    <!----------------- 新增点单信息的弹窗 ------------------>
    <el-dialog title="选择点单数量" v-model="data.formVisible" width="30%">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px;">
        <el-form-item prop="num" label="份数">
          <el-input-number v-model="data.form.num" :min="1" :max="data.dishData.num" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
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
  form: {},
  formVisible: false,
  ordersItemData: [],
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

//点击“我想吃”的函数
const myLike = () => {
  data.form.dishId = data.dishId
  data.form.studentId = data.user.id
  data.form.num = 1
  data.formVisible = true
}
//点击”确定“的函数
const save = () => {
  request.post('/dishItem/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('点单成功')
      data.formVisible = false
      loadDish()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//加载评价的函数
const loadOrdersItem = () => {
  request.get('/ordersItem/selectAll', {
    params: {
      dishId: data.dishId
    }
  }).then(res => {
    if (res.code === '200') {
      data.ordersItemData = res.data.filter(v => v.comment && v.score)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadOrdersItem()
</script>

<style scoped></style>