<template>
  <div style="width: 70%; margin: 20px auto;">
    <div style="font-size: 20px;">这是搜索“{{ data.name }}”的结果</div>
    <div style="margin-top: 20px;">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.dishData" :key="item.id" style="margin-bottom: 10px;">
          <div class="front_card" v-if="item.status === '上架' && item.num">
            <img @click="router.push('/front/dishDetail?id=' + item.id)" :src="item.img" alt=""
              style="width: 100%; height: 200px; object-fit: cover; border-top-left-radius: 6px; border-top-right-radius: 6px; cursor: pointer;">
            <div style="padding: 10px;">
              <div style="font-size: 20px;">{{ item.name }}</div>
              <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.content }}</div>
            </div>
            <div style="margin-top: 5px; display: flex; align-items: center; padding: 0 5px;">
              <div style="margin-right: 50px; font-size: 25px; color: red;">￥ {{ item.price }}</div>
              <div style="flex: 1; text-align: right;">库存：{{ item.num }} 份</div>
            </div>
          </div>
          <div class="front_card" v-if="item.status === '下架'">
            <div style="display: flex; justify-content: center;">
              <img src="@/assets/imgs/下架.svg" alt=""
                style="width: 130px; height: 130px;  border-top-left-radius: 6px; border-top-right-radius: 6px;">
            </div>
            <div style="padding: 10px;">
              <div style="font-size: 20px;">{{ item.name }}（已下架）</div>
              <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.content }}</div>
            </div>
            <div style="margin-top: 5px; display: flex; align-items: center; padding: 0 5px;">
              <div style="margin-right: 50px; font-size: 25px; color: red;">￥ {{ item.price }}</div>
              <div style="flex: 1; text-align: right;">库存：{{ item.num }} 份</div>
            </div>
          </div>
          <div class="front_card" v-if="!item.num">
            <div style="display: flex; justify-content: center;">
              <img src="@/assets/imgs/售罄.svg" alt=""
                style="width: 130px; height: 130px;  border-top-left-radius: 6px; border-top-right-radius: 6px;">
            </div>
            <div style="padding: 10px;">
              <div style="font-size: 20px;">{{ item.name }}</div>
              <div class="line2" style="margin-top: 5px; line-height: 20px; height: 40px;">{{ item.content }}</div>
            </div>
            <div style="margin-top: 5px; display: flex; align-items: center; padding: 0 5px;">
              <div style="margin-right: 50px; font-size: 25px; color: red;">￥ {{ item.price }}</div>
              <div style="flex: 1; text-align: right;">库存：{{ item.num }} 份</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!----------------- 分页 ------------------>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize"
        v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import router from '@/router';
import { reactive } from 'vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';

const data = reactive({
  name: router.currentRoute.value.query.name,
  dishData: [],
  pageNum: 1,
  pageSize: 4,
  total: 0,

})

// 搜索菜品的函数
const search = () => {
  request.get('/dish/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
    }
  }).then(res => {
    if (res.code === '200') {
      data.dishData = res.data.list
      data.total = res.data.total
    }else{
      ElMessage.error(res.msg)
    }
  })
}
search()
//将搜索的数据添加到后端的函数
const addSearch = () => {
  request.post('/search/add', {
    name: data.name,
  }).then(res => {
    if (res.code !== '200') {
      ElMessage.error(res.msg)
    }
  })
}
addSearch()
</script>