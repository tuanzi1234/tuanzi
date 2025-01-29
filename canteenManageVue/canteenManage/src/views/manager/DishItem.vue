<template>
  <div>
    <!----------- 查询学生点单信息 ----------->
    <div class="card">
      <el-input v-model="data.dishName" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入菜品名称"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 新增学生点单信息 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <!----------------- 学生点单信息列表 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-table stripe :data="data.tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column prop="dish.img" label="菜品图片" width="90px">
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
        <el-table-column prop="dish.name" label="菜品名称" />
        <el-table-column prop="dish.price" label="菜品价格">
          <template v-slot="scope">
            <span style="color: red; font-weight: bold;">￥ {{ scope.row.dish.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="菜品份数">
          <template v-slot="scope">
            {{ scope.row.num }} 份
          </template>
        </el-table-column>
        <el-table-column prop="studentName" label="学生姓名" />
        <el-table-column label="操作" width="180">
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
import { reactive, ref } from 'vue'// 从 Vue 的composition中导入 reactive 函数，用于创建响应式的对象
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
  dishName: null,// 查询条件:标题，初始设置为null
  content: null,// 查询条件:内容，初始设置为null
  ids: [],// 选中的id，初始设置为空数组
  rules: {
    dishName: [
      { required: true, message: '请输入公告标题', trigger: 'blur' },
    ],
    content: [
      { required: true, message: '请输入公告内容', trigger: 'blur' },
    ],
  },
}
)
const formRef = ref()

// 新增学生点单信息的函数
const add = () => {
  request.post('/dishItem/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()// 新增成功后，重新加载学生点单信息列表
      data.formVisible = false// 新增成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.id)// 将选中的学生点单id赋值给ids数组
}
//分页查询学生点单信息的函数
const load = () => {
  request.get('/dishItem/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      dishName: data.dishName,
      content: data.content
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []// 将从后端获取到的学生点单信息列表赋值给表格数据对象  
      data.total = res.data?.total// 将从后端获取到的总条数赋值给总条数对象
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//调用分页查询函数
load()

// 更新学生点单信息的函数
const update = () => {
  request.put('/dishItem/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      load()// 更新成功后，重新加载学生点单信息列表
      data.formVisible = false// 更新成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 删除单个学生点单信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该学生点单信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/dishItem/delete/${row.id}`).then(res => {
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
// 批量删除学生点单信息的函数
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的学生点单信息')
    return
  }
  ElMessageBox.confirm('是否删除该学生点单信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/dishItem/delete/batch`, { data: data.ids }).then(res => {
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
// 点击保存按钮时触发的函数
const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.id ? update() : add() // 根据表单数据对象中的id属性判断是编辑还是添加学生点单信息
    }
  })
}

// 重置查询条件的函数
const reset = () => {
  data.dishName = null
  data.content = null
  load()
}


</script>