import { createApp } from 'vue'// 从Vue.js导入createApp函数，用于创建Vue应用实例
import App from './App.vue'// 导入应用的根组件App.vue
import router from './router'// 导入路由配置
import ElementPlus from 'element-plus' // 导入Element Plus UI库
import 'element-plus/dist/index.css' // 导入Element Plus的样式
import '@/assets/css/global.css'// 导入全局样式文件，确保应用中的所有组件都能应用这些样式
import * as ElementPlusIconsVue from '@element-plus/icons-vue'// 导入Element Plus图标库
import zhCn from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)// 创建Vue应用实例
app.use(router)// 使用路由配置对应用实例进行扩展，使其支持路由功能
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}// 使用Element Plus UI库
app.use(ElementPlus, {
  locale: zhCn,
}) // 使用Element Plus UI库对应用实例进行扩展，使其支持Element Plus组件
app.mount('#app')// 将应用实例挂载到HTML模板中的'#app'元素上，启动Vue应用
