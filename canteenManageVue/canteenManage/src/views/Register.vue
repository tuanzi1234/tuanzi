<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-title" style=" font-size: 30px; font-weight: bold; margin-bottom: 10px; color:#000000">欢 迎 注 册
      </div>
      <el-form ref="formRef" :model="data.form" :rules="data.rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" v-model="data.form.username" size="large" placeholder="请输入账号（即学号）"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password :prefix-icon="Lock" v-model="data.form.password" size="large"
            placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input show-password :prefix-icon="Lock" v-model="data.form.confirmPassword" size="large"
            placeholder="请确认密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" @click="login">注 册</el-button>
        </el-form-item>
        <div style="text-align: right; margin:10px 0;">
          已有账号？<a href="/login">登录</a>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'


//自定义再次输入密码的校验
const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入密码'))
  } else {
    if (value !== data.form.password) {
      callback(new Error('两次输入的密码不匹配，请重新输入'))
    }
    callback()
  }
}


// 定义表格变量和响应式数据
const data = reactive({
  form: {},
  // 验证规则
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' },
      { pattern: /^\d+$/, message: '账号只能输入学号', trigger: 'blur' }, // 添加正则表达式验证规则
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, message: '密码不能少于六位', trigger: 'blur' },
    ],
    confirmPassword: [
      { validator: validatePass, trigger: 'blur' }
    ],
  }
})
const formRef = ref()
// 登录函数
const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {// 验证通过
      request.post('/register', data.form).then(res => {
        if (res.code === '200') {
          // 登录成功，跳转到主页
          ElMessage.success('注册成功')
          setInterval(() => {
            location.href = '/login'
          }, 500)
        } else {
          // 登录失败，弹出提示框
          ElMessage.error(res.msg)
        }
      }
      )
    }
  })
}



</script>

<style scoped>
/* 设置登录页面的样式 */
.login-container {
  /* 设置容器高度为视口高度的100% */
  height: 100vh;
  /* 使用flex布局 */
  display: flex;
  /* 隐藏溢出内容 */
  overflow: hidden;
  /* 水平居中内容 */
  justify-content: center;
  /* 垂直居中内容 */
  align-items: center;
  /* 设置背景颜色为线性渐变，从下到上颜色逐渐变浅 */
  background: linear-gradient(to top, #009ACD, #00BFFF);

}

/* 设置登录框的样式 */
.login-box {
  /* 设置登录框宽度为400px */
  width: 350px;
  /* 设置登录框高度为500px */
  height: 400px;
  /* 设置登录框圆角为5px */
  border-radius: 5px;
  /* 设置登录框阴影效果 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  /* 设置登录框背景颜色为半透明的白色 */
  background-color: rgba(255, 255, 255);
  /* 使用flex布局 */
  display: flex;
  /* 水平居中内容 */
  justify-content: center;
  /* 垂直居中内容 */
  align-items: center;
  /* 设置flex布局方向为垂直方向 */
  flex-direction: column;

}

/* 设置输入框宽度为245px */
.el-input {
  width: 245px;
}

/* 设置按钮宽度为245px */
.el-button {
  width: 245px;
}
</style>