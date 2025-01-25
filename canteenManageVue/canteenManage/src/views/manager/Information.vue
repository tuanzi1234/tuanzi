<template>
  <div>
    <!----------- 查询系统资讯信息 ----------->
    <div class="card">
      <el-input v-model="data.title" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入资讯标题"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 新增系统资讯信息 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <!----------------- 系统资讯信息列表 ------------------>
    <div class="card" style="margin-bottom: 10px;">
      <el-table stripe :data="data.tableData" border style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" header-align="center" align="center" />
        <el-table-column prop="img" label="封面" width="100%">
          <template v-slot="scope">
            <el-image :src="scope.row.img" :preview-src-list="[scope.row.img]" fit="cover"
              style="width: 50px; height: 50px; object-fit: cover; " preview-teleported>
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
        <el-table-column prop="title" label="资讯标题" />
        <el-table-column prop="content" label="资讯内容"  >
          <template v-slot="scope">
            <el-button @click="viewContent(scope.row.content)" type="primary">点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="发布时间" />
        <el-table-column prop="username" label="发布人" />
        <el-table-column prop="num" label="浏览量" />
        <el-table-column label="操作" width="180">
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
    <!----------------- 新增资讯信息的弹窗 ------------------>
    <el-dialog title="资讯信息" v-model="data.formVisible" width="50%">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px;">
        <el-form-item prop="title" label="资讯标题">
          <el-input v-model="data.form.title" placeholder="请输入资讯标题"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="资讯内容">
          <div style="border: 1px solid #ccc; width: 100%">
            <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :mode="mode" />
            <Editor style="height: 500px; overflow-y: hidden;" v-model="data.form.content" :mode="mode"
              :defaultConfig="editorConfig" @onCreated="handleCreated" />
          </div>
        </el-form-item>
        <el-form-item prop="img" label="资讯封面">
          <el-upload class="upload-demo" :action="baseUrl + '/files/upload'" :on-success="handleImgSuccess"
            list-type="picture" :show-file-list="false">
            <el-button type="primary">文件上传</el-button>
          </el-upload>
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
    <!----------------- 资讯内容的弹窗 ------------------>
    <el-dialog title="资讯内容" v-model="data.viewVisible" width="60%">
      <div v-html="data.viewContent" style="padding: 10px;">

      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { onBeforeUnmount, reactive, ref, shallowRef } from "vue";// 从 Vue 的composition中导入 reactive 函数，用于创建响应式的对象
import request from '@/utils/request'// 导入自定义的request.js，用于处理后端请求
import { Edit, Delete, PictureFilled } from '@element-plus/icons-vue'// 从 Element Plus 图标库中按需导入编辑和删除图标组件
import { ElMessage, ElMessageBox } from 'element-plus'
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'



const baseUrl = import.meta.env.VITE_BASE_URL
// 声明响应式资讯表单数据的函数
const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  formVisible: false,// 控制表单的可见状态，初始设置为不可见
  viewVisible: false,// 控制表单的可见状态，初始设置为不可见
  form: {},// 表单数据对象，初始为空
  tableData: [],// 表格数据对象，初始为空
  pageNum: 1,// 页码，初始设置为1
  pageSize: 5,// 每页显示的条数，初始设置为10
  total: 0,// 总条数，初始设置为0
  title: null,// 查询条件:标题，初始设置为null
  content: null,// 查询条件:内容，初始设置为null
  ids: [],// 选中的id，初始设置为空数组
  viewContent: null,// 查看内容，初始设置为null
  rules: {
    title: [
      { required: true, message: '请输入资讯标题', trigger: 'blur' },
    ],
    content: [
      { required: true, message: '请输入资讯内容', trigger: 'blur' },
    ],
    img: [
      { required: true, message: '请上传封面', trigger: 'blur' },
    ],
  },
}
)
const formRef = ref()

/* wangEditor5 初始化开始 */
const editorRef = shallowRef()  // 编辑器实例，必须用 shallowRef
const mode = 'default' 
const editorConfig = { MENU_CONF: {} }
// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
  headers: {
    token: data.user.token,
  },
  server: baseUrl + '/files/wang/upload',  // 服务端图片上传接口
  fieldName: 'file'  // 服务端图片上传接口参数
}
// 组件销毁时，也及时销毁编辑器，否则可能会造成内存泄漏
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
// 记录 editor 实例，重要！
const handleCreated = (editor) => {
  editorRef.value = editor
}
/* wangEditor5 初始化结束 */
// 控制咨询内容弹窗点击事件的函数
const viewContent = (content) => {
  data.viewContent = content
  data.viewVisible = true
}

// 点击新增按钮时触发的函数
const handleAdd = () => {
  data.form = {} // 初始化表单数据，清空之前的数据
  data.formVisible = true// 点击新增按钮时，将表单的可见状态设置为true，显示表单
}
// 新增系统资讯信息的函数
const add = () => {
  request.post('/information/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()// 新增成功后，重新加载系统资讯信息列表
      data.formVisible = false// 新增成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//编辑系统资讯信息的函数
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))// 将要编辑的系统资讯信息赋值给表单数据对象
  data.formVisible = true// 将表单的可见状态设置为true，显示表单
}
//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.id)// 将选中的系统资讯id赋值给ids数组
}
//分页查询系统资讯信息的函数
const load = () => {
  request.get('/information/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title,
      content: data.content
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []// 将从后端获取到的系统资讯信息列表赋值给表格数据对象  
      data.total = res.data?.total// 将从后端获取到的总条数赋值给总条数对象
    } else {
      ElMessage.error(res.msg)
    }
  })
}
//调用分页查询函数
load()

// 更新系统资讯信息的函数
const update = () => {
  request.put('/information/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      load()// 更新成功后，重新加载系统资讯信息列表
      data.formVisible = false// 更新成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 删除单个系统资讯信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否删除该系统资讯信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/information/delete/${row.id}`).then(res => {
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
// 批量删除系统资讯信息的函数
const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning('请选择要删除的系统资讯信息')
    return
  }
  ElMessageBox.confirm('是否删除该系统资讯信息?（删除后将无法恢复）', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/information/delete/batch`, { data: data.ids }).then(res => {
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
      data.form.id ? update() : add() // 根据表单数据对象中的id属性判断是编辑还是添加系统资讯信息
    }
  })
}

// 重置查询条件的函数
const reset = () => {
  data.title = null
  data.content = null
  load()
}
// 处理封面上传成功的函数
const handleImgSuccess = (res) => {
  data.form.img = res.data
  ElMessage.success('文件上传成功，点击“确定”即可查看')
}


</script>