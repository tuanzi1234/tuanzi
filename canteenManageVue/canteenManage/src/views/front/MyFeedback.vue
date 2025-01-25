<template>
  <div style="width: 60%; margin: 20px auto; padding: 20px;">
    <div class="card">
      <!------------------- 反馈标题 --------------------------------->
      <div style="font-size: 25px;">我的反馈: ({{ data.total }})</div>
      <!------------------- 搜索栏 --------------------------------->
      <div style="margin-top: 15px;">
        <el-input v-model="data.title" prefix-icon="Search" style="width:240px; margin-right: 10px;"
          placeholder="请输入标题"></el-input>
        <el-button type="info" plain @click="loadFeedback">查询</el-button>
        <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
      </div>
    </div>
    <!------------------- 反馈信息 --------------------------------->
    <div class="card" style="margin: 10px auto;">
      <el-table stripe :data="data.feedbackData" border style="width: 100%">
        <el-table-column prop="title" label="反馈标题" show-overflow-tooltip />
        <el-table-column prop="question" label="反馈问题" show-overflow-tooltip />
        <el-table-column prop="idea" label="想法" show-overflow-tooltip />
        <el-table-column prop="createTime" label="反馈时间" width="180px" />
        <el-table-column prop="content" label="回复内容" show-overflow-tooltip />
        <el-table-column prop="replyTime" label="回复时间" width="180px" />
        <el-table-column label="操作" width="180" >
          <template v-slot="scope">
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!------------------- 分页 --------------------------------->
    <div class="card" v-if="data.total">
        <el-pagination @current-change="loadFeedback" background layout="total, prev, pager, next" :page-size="data.pageSize"
          v-model:current-page="data.pageNum" :total="data.total" />
      </div>
    <div>
    </div>
  </div>
</template>

<script setup>
import { reactive, } from 'vue'
import request from '@/utils/request'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'


const data = reactive({
  feedbackData: [],
  pageNum: 1,
  pageSize: 5,
  total: 0,
})
// 加载反馈信息的函数
const loadFeedback = () => {
  request.get('/feedback/selectPage',
    {
      params: {
        pageNum: data.pageNum,
        pageSize: data.pageSize,
        title: data.title,
      }
    }
  ).then((res) => {
    if (res.code === '200') {
      data.feedbackData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadFeedback()

// 重置函数
const reset = () => {
  data.title = null,
    loadFeedback()
}

// 删除单个系统公告信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该系统公告信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/feedback/delete/${row.id}`).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        loadFeedback()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })
}

</script>