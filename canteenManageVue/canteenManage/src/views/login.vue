<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-title" style=" font-size: 30px; font-weight: bold; margin-bottom: 10px; color:#F0FFFF">欢 迎 登 录
      </div>
      <el-form ref="formRef" :model="data.form" :rules="data.rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="User" v-model="data.form.username" size="large" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input show-password :prefix-icon="Lock" v-model="data.form.password" size="large"
            placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select size="large" v-model="data.form.role">
            <el-option label="管理员" value="ADMIN"></el-option>
            <el-option label="学生" value="STUDENT"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="validCode">
          <div class="valid-code">
            <el-input prefix-icon="Check" size="medium" placeholder="请输入验证码" v-model="data.form.validCode"></el-input>
            <div class="valid-code-img">
              <validCode @change="handleValidCodeChange" />
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="large" @click="login">登 录</el-button>
        </el-form-item>
        <div style="display: flex;">
          <div style="flex: 1; margin-bottom: 10px;">
            <a style="color: #551A8B;" @click="showResetDialog" >忘记密码？</a>
          </div>
          <div style="flex: 1;text-align: right; margin-bottom: 10px;">
            还没有账号？<a href="/register">立即注册</a>
          </div>
        </div>
      </el-form>
     <div class="reset-dialog">
      <el-dialog  title="忘记密码" v-model="data.formVisible" width="35%">
        <div class="centered-dialog">
        <el-form ref="resetFormRef" :rules="resetForm.resetRules" :model="resetForm.data" label-width="60px" >
          <el-form-item prop="username" label="账号">
            <el-input v-model="resetForm.data.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="phone" label="电话">
            <el-input v-model="resetForm.data.phone" placeholder="请输入电话"></el-input>
          </el-form-item>
          <el-form-item prop="role" label="角色">
          <el-select size="large" v-model="resetForm.data.role">
            <el-option label="管理员" value="ADMIN"></el-option>
            <el-option label="学生" value="STUDENT"></el-option>
          </el-select>
        </el-form-item>
        </el-form>
      </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button  @click="data.formVisible = false">取消</el-button>
            <el-button type="primary" @click="resetPassword">确定</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
    </div>
  </div>

</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import validCode from '../components/validCode.vue'; // 导入 validCode 组件


// 定义重置密码的表单变量和响应式数据
const resetForm = reactive({
  data: {
    username: '',
    phone: '',
    role: '',
  },
  
  // 定义重置密码表单的验证规则
  resetRules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    phone: [
      { required: true, message: '请输入电话', trigger: 'blur' },
    ],
    role: [
      { required: true, message: '请选择角色', trigger: 'blur' },
    ],
  }
})
// 定义表格变量和响应式数据
const data = reactive({
  form: {
    username: '',
    password: '',
    role: '',
    validCode: ''
  },
  // 验证规则
  rules: {
    username: [
      { required: true, message: '请输入账号', trigger: 'blur' },
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
    ],
    validCode: [
      { required: true, message: '请输入验证码', trigger: 'blur' },
    ],
  }
})
const validCodeValue = ref('');
const formRef = ref();
const resetFormRef = ref();

// 验证码改变时触发的函数
const handleValidCodeChange = (newCode) => {
  validCodeValue.value = newCode.toLowerCase(); // 将生成的验证码转换为小写
};
// 登录函数
const login = () => {
  formRef.value.validate((valid) => {
    // 检查用户输入的验证码是否与系统生成的验证码匹配, 不区分大小写
    const userInput = data.form.validCode.toLowerCase();
    if (userInput !== validCodeValue.value) {
      // 如果验证码不匹配, 显示错误消息并中断登录过程
      ElMessage.error('验证码错误');
      return;
    }
    if (valid) {// 验证通过
      request.post('/login', data.form).then(res => {
        if (res.code === '200') {
          // 登录成功，跳转到主页
          ElMessage.success('登录成功')
          localStorage.setItem('project-user', JSON.stringify(res.data))// 将用户信息存储到本地存储中，名为project-user
          setInterval(() => {
            if ('ADMIN' === res.data.role) {
              location.href = '/manager/home'
            } else {
              location.href = '/front/home'
            }
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
// 定义显示重置密码弹窗的方法
const showResetDialog = () => {
  // 重置重置密码表单的数据
  resetForm.data = {
  }
  data.formVisible = true
}

// 定义保存重置密码的方法
const resetPassword = () => {
  resetFormRef.value.validate((valid) => {
    if (valid) {
      request.put('/resetPassword', resetForm.data).then(res => {
        if (res.code === '200') {
          ElMessage.success('密码重置成功')
          data.formVisible = false
        } else {
          ElMessage.error(res.msg)
        }
      })
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
  background: linear-gradient(to top, #00CD66, #00FF7F);

}

/* 设置登录框的样式 */
.login-box {
  /* 设置登录框宽度为500px */
  width: 500px;
  /* 设置登录框高度为500px */
  height: 500px;
  /* 设置登录框圆角为5px */
  border-radius: 5px;
  /* 设置登录框阴影效果 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  /* 设置登录框背景颜色为半透明的白色 */
  background-color: rgba(255, 255, 255, 0.5);
  /* 使用flex布局 */
  display: flex;
  /* 水平居中内容 */
  justify-content: center;
  /* 垂直居中内容 */
  align-items: center;
  /* 设置flex布局方向为垂直方向 */
  flex-direction: column;

}

/* 设置输入框宽度 */
.el-input {
  width: 360px;
}

/* 设置按钮宽度 */
.el-button {
  width: 360px;
}

/* 设置选择框 */
.el-select {
  width: 360px;
}

/* 设置验证码整体样式 */
.valid-code {
  width: 360px;
  display: flex;
  height: 40px;
}

/* 设置验证码图片样式 */
.valid-code-img {
  flex: 1;
  height: 40px;
}

/* 设置验证码输入框宽度样式 */
.valid-code.el-input {
  flex: 1;
  height: 40px;
  width: 200px;
}

/*设置重置密码弹窗底部按钮的样式 */
.centered-dialog {
  /* 使用flex布局 */
  display: flex;
  /* 水平居中内容 */
  justify-content: center;
  /* 垂直居中内容 */
  align-items: center;
  /* 设置flex布局方向为垂直方向 */
  flex-direction: column;
}
.dialog-footer {
  /* 使用flex布局 */
  display: flex;
  /* 水平居中内容 */
  justify-content: center;
  /* 垂直居中内容 */
  align-items: center;
  /* 设置flex布局方向为水平方向 */
  flex-direction: row;
}
</style>