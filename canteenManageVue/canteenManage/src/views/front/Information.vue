<template>
  <div style="width: 60%; margin: 20px auto;">
    <div class="card" v-for="item in data.informationData" :key="item.id" style="margin-bottom: 10px;display: flex; grid-gap: 20px;">
      <img @click="$router.push('/front/informationDetail?id=' + item.id)" :src="item.img" alt="" style="width: 200px; height: 130px; border-radius: 5px; cursor: pointer;">
      <div style="flex: 1;">
        <div style="font-size: 20px;">{{ item.title }}</div>
        <div class="line2" style="margin-top: 10px; color: #828282; line-height: 20px; height: 40px;">{{ item.content }}</div>
        <div style="margin-top: 10px; display: flex; align-items: center; color: #B5B5B5;">
          <el-image 
              :src="item.avatar" 
              :preview-src-list="[item.avatar]"
              fit="cover"
              style="width: 40px; height: 40px; object-fit: cover; border-radius: 50%; "
              preview-teleported
            >
              <template #error>
                <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100%;">
                  <el-icon><PictureFilled /></el-icon>
                </div>
              </template>
            </el-image>
            <div style="margin-left: 5px;">{{ item.username }}</div>
            <el-icon style="margin-left: 20px;" size="20px"><View /></el-icon>
            <div style="margin-left: 5px;">{{ item.num }}</div>
        </div>
      </div>
    </div>
    <!----------------- 分页 ------------------>
    <div class="card" v-if="data.total" >
      <el-pagination @current-change="loadInformation()" background layout="total, prev, pager, next" :page-size="data.pageSize"
        v-model:current-page="data.pageNum" :total="data.total" />
    </div>
  </div>
</template>
<script setup>
import request from '@/utils/request';
import { reactive } from 'vue';
import { ElMessage } from 'element-plus';


const data = reactive({
  pageNum: 1,
  pageSize: 5,
  total: 0,
  informationData: [], 
})

//加载食堂资讯的函数
const loadInformation = () => {
  request.get('/information/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize, 
    }
  }).then(res => {
    if (res.code === '200') {
      data.informationData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadInformation()
</script>