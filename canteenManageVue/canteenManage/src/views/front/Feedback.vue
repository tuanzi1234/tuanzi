<template>
  <div class="card" style="width: 60%; margin: 20px auto; padding: 20px;">
    <div style="font-size: 25px; text-align: center; ">提交您的反馈信息</div>
    <div style="margin-top: 10px;">
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding: 20px;">
        <el-form-item prop="title" label="反馈标题">
          <el-input v-model="data.form.title" placeholder="请输入反馈标题"></el-input>
        </el-form-item>
        <el-form-item prop="question" label="反馈问题">
          <el-input type="textarea" rows="4" v-model="data.form.question" placeholder="请输入反馈问题"></el-input>
        </el-form-item>
        <el-form-item prop="idea" label="您的想法">
          <el-input type="textarea" rows="4" v-model="data.form.idea" placeholder="请输入您的想法"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="text-align: center;">
      <el-button @click="submitForm" type="primary" style="padding: 20px 60px;">提 交</el-button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'


// 表单引用
const formRef = ref()
// 反馈意见表单数据
const data = reactive({
  form: {},
  rules: {
    title: [
      { required: true, message: '请输入标题', trigger: 'blur' },
    ],
    question: [
      { required: true, message: '请输入问题', trigger: 'blur' },
    ],
    idea: [
      { required: true, message: '请输入您的想法', trigger: 'blur' },
    ]
  }
}
)
//提交反馈信息的函数
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/feedback/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('反馈成功，请耐心等待管理员的回复，您可以在“我的反馈”中查看反馈信息。')
          data.form = {}
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

</script>

<style scoped></style>