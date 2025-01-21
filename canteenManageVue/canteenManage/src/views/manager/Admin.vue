<template>
  <div>
    <!----------- 查询管理员信息 ----------->
    <div class="card">
      <el-input v-model="data.username" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入账号查询"></el-input>
      <el-input v-model="data.name" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入名称查询"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 新增管理员信息 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <!----------------- 管理员信息列表 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-table stripe :data="data.tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column prop="username" label="账号" />
        <el-table-column prop="avatar" label="头像" >
          <template v-slot="scope">
            <el-image 
              :src="scope.row.avatar" 
              :preview-src-list="[scope.row.avatar]"
              fit="cover"
              style="width: 50px; height: 50px; object-fit: cover; "
              preview-teleported
            >
              <template #error>
                <div style="display: flex; justify-content: center; align-items: center; width: 100%; height: 100%;">
                  <el-icon><PictureFilled /></el-icon>
                </div>
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="role" label="角色" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="180" fixed="right">
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
    <!----------------- 新增管理员信息的弹窗 ------------------>
    <el-dialog title="管理员信息" v-model="data.formVisible" width="30%">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px;">
        <el-form-item prop="username" label="用户账号">
          <el-input v-model="data.form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="avatar" label="头像">
          <el-upload class="upload-demo" :action="baseUrl + '/files/upload'" :on-success="handleAvatarSuccess"
            list-type="picture" :show-file-list="false">
            <el-button type="primary">文件上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="姓名">
          <el-input v-model="data.form.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话">
          <el-input v-model="data.form.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="data.form.email" placeholder="请输入邮箱"></el-input>
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

const baseUrl = import.meta.env.VITE_BASE_URL

// 管理员表单数据
const data = reactive({
  formVisible: false,// 控制表单的可见状态，初始设置为不可见
  form: {},// 表单数据对象，初始为空
  tableData: [],// 表格数据对象，初始为空
  pageNum: 1,// 页码，初始设置为1
  pageSize: 5,// 每页显示的条数，初始设置为10
  total: 0,// 总条数，初始设置为0
  name: null,// 查询条件:姓名，初始设置为null
  username: null,// 查询条件:账号，初始设置为null
  ids: [],// 选中的id，初始设置为空数组
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    name: [
      { required: true, message: '请输入姓名', trigger: 'blur' },
    ],
  }
}
)
const formRef = ref()

// 点击新增按钮时触发的函数
const handleAdd = () => {
  data.form = {} // 初始化表单数据，清空之前的数据
  data.formVisible = true// 点击新增按钮时，将表单的可见状态设置为true，显示表单
}
// 新增管理员信息的函数
const add = () => {
  request.post('/admin/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()// 新增成功后，重新加载管理员信息列表
      data.formVisible = false// 新增成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//编辑管理员信息的函数
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))// 将要编辑的管理员信息赋值给表单数据对象
  data.formVisible = true// 将表单的可见状态设置为true，显示表单
}
//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.id)// 将选中的管理员id赋值给ids数组
}
//分页查询管理员信息
const load = () => {
  request.get('/admin/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      username: data.username
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []// 将从后端获取到的管理员信息列表赋值给表格数据对象  
      data.total = res.data?.total// 将从后端获取到的总条数赋值给总条数对象
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//调用分页查询函数
load()

// 更新管理员信息的函数
const update = () => {
  request.put('/admin/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      load()// 更新成功后，重新加载管理员信息列表
      data.formVisible = false// 更新成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 删除单个管理员信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该管理员信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/admin/delete/${row.id}`).then(res => {
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
// 批量删除管理员信息的函数
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的管理员信息')
    return
  }
  ElMessageBox.confirm('是否删除该管理员信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/admin/delete/batch`, { data: data.ids }).then(res => {
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
      data.form.id? update() : add() // 根据表单数据对象中的id属性判断是编辑还是添加系统公告信息
    }
  })
}
// 处理头像上传成功的函数
const handleAvatarSuccess = (res) => {
  data.form.avatar = res.data
  ElMessage.success('文件上传成功，点击“确定”即可查看')
}
// 重置查询条件的函数
const reset = () => {
  data.username = null
  data.name = null
  load()
}


</script>