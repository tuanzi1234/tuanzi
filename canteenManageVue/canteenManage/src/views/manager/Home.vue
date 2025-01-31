<template>
  <div>
    <div class="card" style="height: 50px; font-size: 20px;">{{ data.user?.name }}，欢迎使用食堂管理系统！</div>
    <div style="margin-top: 10px; display: flex; grid-gap: 10px; height: 350px;">
      <div style="width: 300px;">
        <div class="card" style="height: 80px; display: flex; align-items: center;">
          <div style="flex: 1; text-align: center;">
            <img src="@/assets/imgs/菜品数量.png" alt="" style="height: 60px; width: 70px;">
          </div>
          <div style="flex: 1;">
            <div style="font-size: 20px;">菜品数量</div>
            <div style="font-size: 20px; font-weight: bold;">{{ data.baseData.dishNum }}</div>
          </div>
        </div>
        <div class="card" style="height: 80px; margin-top: 10px; display: flex; align-items: center;">
          <div style="flex: 1; text-align: center;">
            <img src="@/assets/imgs/订单数量.png" alt="" style="height: 60px; width: 70px;">
          </div>
          <div style="flex: 1;">
            <div style="font-size: 20px;">订单数量</div>
            <div style="font-size: 20px; font-weight: bold;">{{ data.baseData.orderNum }}</div>
          </div>
        </div>
        <div class="card" style="height: 80px; margin-top: 10px; display: flex; align-items: center;">
          <div style="flex: 1; text-align: center;">
            <img src="@/assets/imgs/订单总额.png" alt="" style="height: 65px; width: 70px;">
          </div>
          <div style="flex: 1;">
            <div style="font-size: 20px;">订单总额</div>
            <div style="font-size: 20px; font-weight: bold;">￥{{ data.baseData.orderTotal }}</div>
          </div>
        </div>
        <div class="card" style="height: 80px; margin-top: 10px; display: flex; align-items: center;">
          <div style="flex: 1; text-align: center;">
            <img src="@/assets/imgs/资讯数量.png" alt="" style="height: 60px; width: 70px;">
          </div>
          <div style="flex: 1;">
            <div style="font-size: 20px;">资讯数量</div>
            <div style="font-size: 20px; font-weight: bold;">{{ data.baseData.InfoNum }}</div>
          </div>
        </div>
      </div>
      <div class="card" id="line" style="flex: 1;"></div>
    </div>
    <div style="margin-top: 10px; display: flex; grid-gap: 10px;">
      <div class="card" id="pie1" style="height: 400px; flex: 1;"></div>
      <div class="card" id="pie2" style="height: 400px; flex: 1"></div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import request from '@/utils/request'
import { ElMessage } from 'element-plus'
import * as echarts from "echarts";

// 用户表单数据
//公告的数据
const data = reactive({
  user: JSON.parse(localStorage.getItem('project-user') || '{}'),
  baseData: [],
}
)

// 加载统计数据的函数
const loadBase = () => {
  request.get('/statistics/base').then(res => {
    if (res.code === '200') {
      data.baseData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadBase()
})

// 加载平滑折线图：近一周销售额趋势图的函数
const loadLine = () => {

  request.get('/statistics/line').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('line');
      let myChart = echarts.init(chartDom);
      lineOptions. xAxis.data = res.data.xList;
      lineOptions.series[0].data = res.data.yList;
      myChart.setOption(lineOptions);
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadLine()

// 加载饼图1：不同分类下的菜品数量饼状图的函数
const loadPie1 = () => {
  request.get('/statistics/pie1').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('pie1');
      let myChart = echarts.init(chartDom);
      pie1Options.series[0].data = res.data;
      myChart.setOption( pie1Options);
    }else {
      ElMessage.error(res.msg) 
    }
  })
}
loadPie1()

// 加载饼图2：正在点餐的菜品统计占比饼状图的函数
const loadPie2 = () => {
  request.get('/statistics/pie2').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('pie2');
      let myChart = echarts.init(chartDom);
      pie2Options.series[0].data = res.data;
      myChart.setOption( pie2Options);
    }else {
      ElMessage.error(res.msg) 
    }
  })
}
loadPie2()
// 平滑折线图：近一周销售额趋势图
let lineOptions = {
  title: {
    text: '近一周销售额趋势图',
    subtext: '统计维度：最近一周',
    left: 'center'
  },
  legend: {
    data: [],
    template: ""
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  tooltip: {
    trigger: 'item'
  },
  xAxis: {
    name: '日期',
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    name: '销售额',
    type: 'value'
  },
  series: [
    {
      name: '销售金额',
      data: [120, 932, 2201, 1034, 1290, 1330, 3320],
      type: 'line',
      smooth: true,
      markLine: {
        data: [{ type: 'average', name: '最近一周销售额平均值' }]
      },
      markPoint: {
        data: [
          { type: 'max', name: '最大值' },
          { type: 'min', name: '最小值' }
        ]
      },
    },
  ]
};
// 饼图1：不同分类下的菜品数量饼状图
let pie1Options = {
  title: {
    text: '不同分类下的菜品数量饼状图', // 主标题
    subtext: '统计维度：菜品类型', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '数量占比', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: 1048, name: '瑞幸咖啡'}, // 示例数据：name表示维度，value表示对应的值
        {value: 735, name: '雀巢咖啡'},
        {value: 580, name: '星巴克咖啡'},
        {value: 484, name: '栖巢咖啡'},
        {value: 300, name: '小武哥咖啡'}
      ]
    }
  ]
}

// 饼图2：学生常点的菜品统计占比饼状图
let pie2Options = {
  title: {
    text: '学生常点的菜品统计', // 主标题
    subtext: '统计维度：菜品名称', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '数量占比', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: 1048, name: '瑞幸咖啡'}, // 示例数据：name表示维度，value表示对应的值
        {value: 735, name: '雀巢咖啡'},
        {value: 580, name: '星巴克咖啡'},
        {value: 484, name: '栖巢咖啡'},
        {value: 300, name: '小武哥咖啡'}
      ]
    }
  ]
}
</script>