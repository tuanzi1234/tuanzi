<template>
  <div>
    <!----------- 查询学生评价信息 ----------->
    <div class="card">
      <el-input v-model="data.orderNo" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入订单编号"></el-input>
      <el-input v-model="data.dishName" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入菜品名称"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 新增学生评价信息 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <!----------------- 学生评价信息列表 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-table stripe :data="data.tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column prop="orderNo" label="订单编号" />
        <el-table-column prop="dish.name" label="菜品名称" width="150px" show-overflow-tooltip />
        <el-table-column prop="dish.img" label="图片" width="100px" >
          <template v-slot="scope">
            <el-image v-if="scope.row.dish.img" :src="scope.row.dish.img" :preview-src-list="[scope.row.dish.img]"
              fit="cover" style="width: 50px; height: 50px; object-fit: cover; " preview-teleported>
              <template #error>
                <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100%;">
                  <el-icon>
                    <PictureFilled />
                  </el-icon>
                </div>
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="下单份数" width="100px">
          <template v-slot="scope">
            x{{ scope.row.num }}
          </template>
        </el-table-column>
        <el-table-column prop="dish.price" label="价格" width="100px" >
          <template v-slot="scope">
            <span style="color: red; font-weight: bold;">￥ {{ scope.row.dish.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="score" label="评分" width=180px>
          <template v-slot="scope">
            <el-rate v-model="scope.row.score" disabled show-score text-color="#ff9900" score-template="{value} 分" />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评价内容" show-overflow-tooltip />
        <el-table-column prop="time" label="评价时间" width="180px" />
        <el-table-column label="操作" width="180" >
          <template v-slot="scope">
            <el-button type="danger" circle :icon="Delete" @click="del(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!----------------- 分页 ------------------>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize"
        v-model:current-page="data.pageNum" :total="data.total" />
    </div>
   
  </div>
</template>

<script setup>
import { reactive,ref } from 'vue'// 从 Vue 的composition中导入 reactive 函数，用于创建响应式的对象
import request from '@/utils/request'// 导入自定义的request.js，用于处理后端请求
import { Edit, Delete, PictureFilled } from '@element-plus/icons-vue'// 从 Element Plus 图标库中按需导入编辑和删除图标组件
import { ElMessage, ElMessageBox } from 'element-plus'


// 声明响应式公告表单数据的函数
const data = reactive({
  formVisible: false,// 控制表单的可见状态，初始设置为不可见
  form: {},// 表单数据对象，初始为空
  tableData: [],// 表格数据对象，初始为空
  pageNum: 1,// 页码，初始设置为1
  pageSize: 5,// 每页显示的条数，初始设置为10
  total: 0,// 总条数，初始设置为0
  orderNo: null,// 查询条件:标题，初始设置为null
  dishName: null,// 查询条件:内容，初始设置为null
  ids: [],// 选中的id，初始设置为空数组
})


//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.id)// 将选中的学生评价id赋值给ids数组
}
//分页查询学生评价信息的函数
const load = () => {
  request.get('/ordersItem/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      orderNo: data.orderNo,
      content: data.content,
      dishName: data.dishName
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []// 将从后端获取到的学生评价信息列表赋值给表格数据对象  
      data.total = res.data?.total// 将从后端获取到的总条数赋值给总条数对象
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//调用分页查询函数
load()

// 删除单个学生评价信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该学生评价信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/ordersItem/delete/${row.id}`).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    }).catch(err => {
      ElMessage.error(err)
    })
  })
}
// 批量删除学生评价信息的函数
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的学生评价信息')
    return
  }
  ElMessageBox.confirm('是否删除该学生评价信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/ordersItem/delete/batch`, { data: data.ids }).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    }).catch(err => {
      ElMessage.error(err)
    })
  })
}


// 重置查询条件的函数
const reset = () => {
  data.orderNo = null
  data.dishName = null
  load()
}


</script>