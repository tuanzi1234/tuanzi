<template>
  <div style="width: 70%; margin: 20px auto;">
    <div class="card">
      <el-input v-model="data.oderNo" prefix-icon="Search" style="width:240px; margin-right: 10px;"
        placeholder="请输入订单编号"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px;" plain @click="reset">重置</el-button>
    </div>
    <!----------------- 订单信息列表 ------------------>
    <div class="card" style="margin: 10px auto;">
      <el-table stripe :data="data.ordersData" border style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" width="160px" />
        <el-table-column prop="createTime" label="下单时间" width="180px" />
        <el-table-column prop="payNo" label="支付单号" show-overflow-tooltip/>
        <el-table-column prop="payType" label="支付方式" >
          <template v-slot="scope">
            <el-tag v-if="scope.row.payType === '支付宝'" type="info">{{ scope.row.payType }}</el-tag>
            <el-tag v-if="scope.row.payType === '微信'" type="success">{{ scope.row.payType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="订单价格">
          <template v-slot="scope">
            <span style="color: red;">￥{{ scope.row.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="订单状态">
          <template v-slot="scope">
            <el-tag v-if="scope.row.status === '待支付'" type="warning">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '已支付'" type="info">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '已上餐'" type="primary">{{ scope.row.status }}</el-tag>
            <el-tag v-if="scope.row.status === '已完成'" type="success">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="订单详情">
          <template v-slot="scope">
            <el-button type="primary" plain @click="loadDetail(scope.row.id, scope.row.status)">查看详情</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px">
          <template v-slot="scope">
            <el-button v-if="scope.row.status === '待支付'" type="danger" plain @click="cancel(scope.row)">取消订单</el-button>
            <el-button v-if="scope.row.status === '待支付'" type="info" plain @click="payInit(scope.row)">支付</el-button>
            <el-button v-if="scope.row.status === '已上餐'" type="success" plain @click="changeStatus(scope.row, '已完成')">完成</el-button>
            <el-button v-if="scope.row.status === '已完成'" type="danger" plain @click="del(scope.row)">删除</el-button>
            <span v-if="scope.row.status === '已支付'">出餐中......</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
     <!----------------- 分页 ------------------>
     <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize"
        v-model:current-page="data.pageNum" :total="data.total" />
    </div>
    <!----------------- 订单详情的弹窗 ------------------>
    <el-dialog title="订单详情" v-model="data.itemVisible" width="60%">
      <el-table stripe :data="data.ordersItemData" border style="width: 100%">
        <el-table-column prop="dish.name" label="菜品名称">
          <template v-slot="scope">
            <a :href="'/front/dishDetail?id=' + scope.row.dishId">{{ scope.row.dish.name }}</a>
          </template>
        </el-table-column>
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
        <el-table-column prop="dish.price" label="菜品价格">
          <template v-slot="scope">
            <span style="color: red; font-weight: bold;">￥ {{ scope.row.dish.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="菜品份数">
          <template v-slot="scope">
            x{{ scope.row.num }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" v-if="data.status == '已完成'">
          <template v-slot="scope">
            <el-button type="success" plain @click="del(scope.row)">评价</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!----------------- 支付详情的弹窗 ------------------>
    <el-dialog title="支付详情" v-model="data.payVisible" width="30%">
      <el-table stripe :data="data.ordersItemData" border style="width: 100%">
        <el-table-column prop="dish.name" label="菜品名称">
          <template v-slot="scope">
            <a :href="'/front/dishDetail?id=' + scope.row.dishId">{{ scope.row.dish.name }}</a>
          </template>
        </el-table-column>
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
        <el-table-column prop="dish.price" label="菜品价格">
          <template v-slot="scope">
            <span style="color: red; font-weight: bold;">￥ {{ scope.row.dish.price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="菜品份数">
          <template v-slot="scope">
            x{{ scope.row.num }}
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px; display: flex; align-items: center;">
        <div style="font-size: 15px;">支付方式：</div>
        <el-radio-group v-model="data.orders.payType">
          <el-radio-button label="支付宝" value="支付宝" />
          <el-radio-button label="微信" value="微信" />
        </el-radio-group>
        <div style="margin-left: 5px;">
          支付功能需要营业执照,故此处仅作演示
        </div>
      </div>
      <div style="margin-top: 40px; display: flex; align-items: center; grid-gap: 20px;">
        <div style="flex:1; text-align: right; font-size: 15px; color: red;">支付金额：<span style="font-size: 25px;">￥{{ data.orders.price }}</span></div>
        <div style="width: 100px;">
          <el-button @click="pay" type="danger" style="width: 100px;" >确认支付</el-button>
        </div>
        
      </div>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'
import { PictureFilled } from '@element-plus/icons-vue'



const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  oderNo: null,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  ordersData: [],
  ordersItemData: [],
  status: null,
  itemVisible: false,
  payVisible: false,
  totalPrice: 0,
  orders: {},
})

const load = () => {
  request.get('/orders/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      studentId: data.user.id,
    }
  }).then(res => {
    if (res.code == '200') {
      data.ordersData = res.data.list
      data.total = res.data.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
load()
const reset = () => {
  data.oderNo = null
  load()
}

//查看订单详情的函数
const loadDetail = (orderId, status) => {
  request.get('/ordersItem/selectAll', {
    params: {
      orderId: orderId,
    }
  }).then(res => {
    if (res.code == '200') {
      data.ordersItemData = res.data
      data.status = status
      data.itemVisible = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 查看支付详情的函数
const loadPayDetail = (orderId) => {
  request.get('/ordersItem/selectAll', {
    params: {
      orderId: orderId,
    }
  }).then(res => {
    if (res.code == '200') {
      data.ordersItemData = res.data
      data.payVisible = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 取消单个订单信息的函数
const cancel = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('取消后需要重新点单，是否取消？', '取消确认', { type: 'warning' }).then(res => {
    request.delete(`/orders/delete/${row.id}`).then(res => {
      if (res.code === '200') {
        ElMessage.success('取消成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    }).catch(err => {
      ElMessage.error(err)
    })
  })
}

// 支付弹窗的函数
const payInit = (row) => {
  data.orders = row
  loadPayDetail(row.id)
}

// 支付的函数
const pay = () => {
  if (!data.orders.payType) {
    ElMessage.warning('请选择支付方式')
    return 
  }
  request.post('/orders/pay', data.orders).then(res => {
    if (res.code == '200') {
      localStorage.setItem('project-user', JSON.stringify(res.data))
      ElMessage.success('支付成功')
      data.payVisible = false
      load()
    }else{
      ElMessage.error(res.msg) 
    }
  })
}

// 上餐按钮的函数
const changeStatus = (row, status) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.form.status = status
  request.put('/orders/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      load()// 更新成功后，重新加载系统订单信息列表
      data.formVisible = false// 更新成功后，将表单的可见状态设置为false，隐藏表单
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 删除单个订单信息的函数
const del = (row) => {//传整行数据，否则可能无法请求到id
  ElMessageBox.confirm('删除后数据无法恢复，是否删除？', '删除确认', { type: 'warning' }).then(res => {
    request.delete(`/orders/delete/${row.id}`).then(res => {
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
</script>