<template>
  <div style="width: 50%;" class="card">
    <el-form ref="formRef" :rules="data.rules" :model="data.user" label-width="70px" style="padding: 20px; ">
      <el-form-item label="原密码" prop="password">
        <el-input v-model="data.user.password" placeholder="请输入原密码" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="data.user.newPassword" placeholder="请输入新密码" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="confirmPassword">
        <el-input v-model="data.user.confirmPassword" placeholder="再次输入新密码" show-password></el-input>
      </el-form-item>
      <div style="text-align:center;">
        <el-button type="primary" @click="updatePassword">保存</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'




// 表单引用
const formRef = ref()

//自定义再次输入密码的校验
const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入密码'))
  } else {
    if (value !== data.user.newPassword) {
      callback(new Error('两次输入的密码不匹配，请重新输入'))
    }
    callback()
  }
}

const admin = JSON.parse(localStorage.getItem('project-user') || '{}')
// 用户表单数据
const data = reactive({
  user: {
    password: '', // 初始化为空字符串
    newPassword: '',
    confirmPassword: ''
  }, 
  rules: {
    password: [
      { required: true, message: '请输入原密码', trigger: 'blur' },
      { min: 6, message: '密码不能少于六位', trigger: 'blur' },
    ],
    newPassword: [
      { required: true, message: '请输入新密码', trigger: 'blur' },
      { min: 6, message: '密码不能少于六位', trigger: 'blur' },
    ],
    confirmPassword: [
      { validator: validatePass, trigger: 'blur' }
    ]
  }
})

// 更新密码的函数
const updatePassword = () => {
  formRef.value.validate(valid => {
    if (valid) {
      const adminData ={
        ...admin,
       password: data.user.password,
       newPassword: data.user.newPassword,
      }
      request.put('/updatePassword', adminData).then(res => {
        if (res.code === '200') {
          ElMessage.success('更新成功')
          logout()
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      console.log('验证失败')
    }
  })
}

// 退出登录的函数
const logout = () => {
  // 清空本地存储
  localStorage.removeItem('project-user');
  setInterval(() => {
     location.href = '/login'
  }, 500)
}



</script>
<style scoped></style>