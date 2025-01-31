<template>
  <div>
    <!----------- 查询系统库存信息 ----------->
    <div class="card">
      <el-input v-model="data.name" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入名字"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 新增系统库存信息 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <!----------------- 系统库存信息列表 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-table stripe :data="data.tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column prop="name" label="名字" />
        <el-table-column prop="price" label="市场价" >
          <template v-slot="scope">
            <span style="color: red;">￥ {{ scope.row.price }}  / 千克</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="库存" >
          <template v-slot="scope">
            <span style="color: green;">{{ scope.row.num }} 千克</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" >
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === '正常'" type="success">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '缺货'" type="danger">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '过剩'" type="warning">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="购入时间" />
        <el-table-column label="操作" width="180" >
          <template v-slot="scope">
            <el-button type="primary" circle :icon="Edit" @click="handleEdit(scope.row)"></el-button>
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
    <!----------------- 新增库存信息的弹窗 ------------------>
    <el-dialog title="库存信息" v-model="data.formVisible" width="30%">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px;">
        <el-form-item prop="name" label="库存标题">
          <el-input v-model="data.form.name" placeholder="请输入名字"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="市场价">
          <el-input v-model="data.form.price" placeholder="请输入市场价"></el-input>
        </el-form-item>
        <el-form-item prop="num" label="库存">
          <el-input v-model="data.form.num" placeholder="请输入库存"></el-input>
        </el-form-item>
        <el-form-item prop="status" label="状态">
          <el-select size="large" v-model="data.form.status">
            <el-option label="正常" value="正常"></el-option>
            <el-option label="缺货" value="缺货"></el-option>
            <el-option label="过剩" value="过剩"></el-option>
          </el-select>
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
import { reactive,ref } from 'vue'// 从 Vue 的composition中导入 reactive 函数，用于创建响应式的对象
import request from '@/utils/request'// 导入自定义的request.js，用于处理后端请求
import { Edit, Delete, PictureFilled } from '@element-plus/icons-vue'// 从 Element Plus 图标库中按需导入编辑和删除图标组件
import { ElMessage, ElMessageBox } from 'element-plus'


// 声明响应式库存表单数据的函数
const data = reactive({
  formVisible: false,// 控制表单的可见状态，初始设置为不可见
  form: {},// 表单数据对象，初始为空
  tableData: [],// 表格数据对象，初始为空
  pageNum: 1,// 页码，初始设置为1
  pageSize: 5,// 每页显示的条数，初始设置为10
  total: 0,// 总条数，初始设置为0
  name: null,// 查询条件:标题，初始设置为null
  ids: [],// 选中的id，初始设置为空数组
  rules: {
    name: [
      { required: true, message: '请输入名字', trigger: 'blur' },
    ],
    price: [
      { required: true, message: '请输入市场价', trigger: 'blur' }, 
    ],
    num: [
      { required: true, message: '请输入库存', trigger: 'blur' }, 
    ],
    status: [
      { required: true, message: '请输入状态', trigger: 'blur' },
    ],
  },
}
)
const formRef = ref()

// 点击新增按钮时触发的函数
const handleAdd = () => {
  data.form = {} // 初始化表单数据，清空之前的数据
  data.formVisible = true// 点击新增按钮时，将表单的可见状态设置为true，显示表单
}
// 新增系统库存信息的函数
const add = () => {
  request.post('/warehouse/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()// 新增成功后，重新加载系统库存信息列表
      data.formVisible = false// 新增成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//编辑系统库存信息的函数
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))// 将要编辑的系统库存信息赋值给表单数据对象
  data.formVisible = true// 将表单的可见状态设置为true，显示表单
}
//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.id)// 将选中的系统库存id赋值给ids数组
}
//分页查询系统库存信息的函数
const load = () => {
  request.get('/warehouse/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []// 将从后端获取到的系统库存信息列表赋值给表格数据对象  
      data.total = res.data?.total// 将从后端获取到的总条数赋值给总条数对象
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//调用分页查询函数
load()

// 更新系统库存信息的函数
const update = () => {
  request.put('/warehouse/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      load()// 更新成功后，重新加载系统库存信息列表
      data.formVisible = false// 更新成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 删除单个系统库存信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该系统库存信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/warehouse/delete/${row.id}`).then(res => {
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
// 批量删除系统库存信息的函数
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的系统库存信息')
    return
  }
  ElMessageBox.confirm('是否删除该系统库存信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/warehouse/delete/batch`, { data: data.ids }).then(res => {
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
      data.form.id? update() : add() // 根据表单数据对象中的id属性判断是编辑还是添加系统库存信息
    }
  })
}

// 重置查询条件的函数
const reset = () => {
  data.name = null
  load()
}


</script>