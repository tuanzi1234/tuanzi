<template>
  <div style="width: 40%; margin: 20px auto;" class="card">
    <div class="card-title" style="margin: 10px 0 20px 15px; font-weight: bold; font-size: 20px; ">个人中心</div>
    <el-form ref="user" :model="data.user" label-width="70px" style="padding: 20px;">
      <div style="text-align: center; margin-bottom: 20px;">
        <el-upload class="avatar-uploader" :action="baseUrl + '/files/upload'" :on-success="handleAvatarSuccess"
          :show-file-list="false">
          <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" />
        </el-upload>
      </div>
      <el-form-item prop="username" label="学号">
        <el-input disabled v-model="data.user.username" placeholder="请输入账号（学号）"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="姓名">
        <el-input v-model="data.user.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="电话">
        <el-input v-model="data.user.phone" placeholder="请输入电话"></el-input>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="data.user.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="account" label="余额">
        <span style="color: red;">￥ {{ data.user.account }}</span>
      </el-form-item>
      <div style="text-align:center;">
        <el-button type="primary" @click="update">保存</el-button>
        <el-button type="info" @click="reChargeInit">充值</el-button>
      </div>
    </el-form>
    <el-dialog title="个人充值" v-model="data.formVisible" width="30%">
      <el-form ref="form" label-width="70px" style="padding: 20px;">
        <el-form-item prop="account" label="充值金额">
          <el-input-number v-model="data.account" :min="10" />
        </el-form-item>
        <el-form-item prop="payType" label="支付方式">
          <div>
            <el-radio-group v-model="data.payType">
              <el-radio-button label="支付宝" value="支付宝" />
              <el-radio-button label="微信" value="微信" />
            </el-radio-group>
            <div>
              支付功能需要营业执照,故此处仅作演示
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="info" @click="reCharge">
            充值
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'


// 获取后端Url
const baseUrl = import.meta.env.VITE_BASE_URL

// 用户表单数据
const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  formVisible: false,
  account: 10,
  payType: null,
})

// 定义一个自定义事件，用于通知父组件更新用户信息
const emit = defineEmits(['updateUser'])
// 更新个人信息的函数
const update = () => {
  if (data.user.role === 'STUDENT') {
    request.put('/student/update', data.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('更新成功')
        localStorage.setItem('project-user', JSON.stringify(data.user))
        emit('updateUser')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
}

// 处理头像上传成功的函数
const handleAvatarSuccess = (res) => {
  data.user.avatar = res.data
  update()
}

// 充值弹窗的函数
const reChargeInit = () => {
  data.formVisible = true
}
// 充值的函数
const reCharge = () => {
  if (!data.payType) {
    ElMessage.warning('请选择支付方式')
    return
  }
  data.user.account = data.account + data.user.account
  update()
  data.formVisible = false
}



</script>


<style scoped>
.avatar-uploader {
  height: 120px;
}

/* 头像上传组件的样式 */
.avatar-uploader .el-upload {
  /* 设置1像素的虚线边框，颜色使用Element Plus的边框颜色变量 */
  border: 1px dashed var(--el-border-color);

  /* 设置鼠标指针为指针样式，表示该区域可点击 */
  cursor: pointer;
  /* 设置定位为相对定位，以便子元素可以相对于该元素进行定位 */
  position: relative;
  /* 隐藏超出元素框的内容 */
  overflow: hidden;
  /* 添加过渡效果，过渡时间使用Element Plus的快速过渡时间变量 */
  transition: var(--el-transition-duration-fast);
}

/* 鼠标悬停在头像上传组件上时的样式 */
.avatar-uploader .el-upload:hover {
  /* 当鼠标悬停时，将边框颜色更改为Element Plus的主色调 */
  border-color: var(--el-color-primary);
}

/* 头像上传组件的图标样式 */
.el-icon.avatar-uploader-icon {
  /* 设置图标的字体大小为28像素 */
  font-size: 28px;
  /* 设置图标的颜色为灰色 */
  color: #8c939d;
  /* 设置图标的宽度为120像素 */
  width: 120px;
  /* 设置图标的高度为120像素 */
  height: 120px;
  /* 设置文本在图标内居中对齐 */
  text-align: center;
}

/* 头像容器的样式 */
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
  /* 设置边框半径为50%，使边框呈现圆角效果 */
  border-radius: 50%;
  /*使其保持原始比例*/
  object-fit: cover;
}
</style>
