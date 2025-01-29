<template>
  <div style="width: 60%; margin: 20px auto;">
    <div class="card">
      <div style="display: flex; align-items: center; padding-bottom:10px; border-bottom: 1px solid #ccc;">
        <div style="flex: 1; font-size: 20px;">全部菜品 ({{ data.dishItemData.length }})</div>
        <div style="width: 100px;">
          <el-button @click="createOrders" type="danger" style="border-radius: 5px;">下单</el-button>
        </div>
      </div>
      <!----------------- 点单信息列表 ------------------>
      <div style="padding: 10px;">
        <el-table stripe :data="data.dishItemData" border style="width: 100%" @selection-change="handleSelectionChange">
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
              <span style="color: red; font-weight: bold;">￥ {{ scope.row.dish.price }}/份</span>
            </template>
          </el-table-column>
          <el-table-column prop="num" label="菜品份数">
            <template v-slot="scope">
              <el-input-number v-model="scope.row.num" :min="1" :max="scope.row.dish.num" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template v-slot="scope">
              <el-button type="danger" @click="del(scope.row)">移除点单</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import { ElMessageBox } from 'element-plus'

const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  dishItemData: [],
  dishes: [],
})

//加载点单详情的函数
const loadDishItem = () => {
  request.get('/dishItem/selectAll', {
    params: {
      studentId: data.user.id
    }
  }).then(res => {
    if (res.code === '200') {
      data.dishItemData = res.data;
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadDishItem()

// 删除单个点单信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('是否移除该点单信息?', '移除确认', { type: 'warning' }).then(res => {
    request.delete(`/dishItem/delete/${row.id}`).then(res => {
      if (res.code === '200') {
        ElMessage.success('移除成功')
        loadDishItem()
      } else {
        ElMessage.error(res.msg)
      }
    }).catch(err => {
      ElMessage.error(err)
    })
  })
}

//点击下单按钮的函数
const createOrders = () => {
  if (!data.dishes.length) {
    ElMessage.warning('请选择要下单的菜品')
    return
  }
  request.post('/orders/create', data.dishes).then(res => {
    if (res.code === '200') {
      ElMessage.success('下单成功')
      loadDishItem()
    } else {
      ElMessage.error(res.msg)
    }
    
  })
}
//处理选中行变化的函数 rows --> 当前选中的行数据数组
const handleSelectionChange = (rows) => {
  data.dishes = rows
}
</script>