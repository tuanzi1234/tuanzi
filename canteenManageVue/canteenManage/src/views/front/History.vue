<template>
  <div style="width: 70%; margin: 20px auto;">
    <div style="font-size: 20px;" class="card">我的浏览记录：{{ data.total }}</div>
    <div style="flex: 1; padding: 5px;">
      <el-row :gutter="10">
        <el-col :span="6" v-for="item in data.dishData" :key="item.id">
          <div class="front_card" style="margin: 10px auto;">
            <img @click="navTo('/front/dishDetail?id=' + item.dish.id)" :src="item.dish.img" alt=""
              style="width: 100%; height: 130px; object-fit: cover; border-top-left-radius: 6px; border-top-right-radius: 6px; cursor: pointer;">
            <div style="padding: 10px;">
              <div style="display: flex; justify-content: space-between;">
                <div style="font-size: 20px;">{{ item.dish.name }}</div>
                <div>
                  <el-button type="danger" plain size="small" @click="del(item.id)">删除</el-button>
                </div>
              </div>
              <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.dish.content }}</div>
            </div>

          </div>
        </el-col>
      </el-row>
    </div>
    <div class="card" v-if="data.total" style="margin-top: 5px;">
      <el-pagination @current-change="loadHistory" background layout="total, prev, pager, next"
        :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'


// 公告表单数据
const data = reactive({
  dishData: [],
  pageNum: 1,
  pageSize: 8,
  total: 0,
}
)

//查询历史记录的函数
const loadHistory = () => {
  request.get('/history/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
    }
  }).then(res => {
    if (res.code === '200') {
      data.dishData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}

//跳转到指定页面的函数
const navTo = (url) => {
  setInterval(() => {
    location.href = url
  }, 300)
}

//删除浏览记录的函数
const del = (id) => {
  request.delete('/history/delete/' + id).then(res => {
    if (res.code === '200') {
      ElMessage.success("删除成功！")
      loadHistory()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadHistory()
</script>

<style scoped></style>