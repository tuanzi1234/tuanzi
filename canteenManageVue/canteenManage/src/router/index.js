// 导入 Vue Router 模块中的 createRouter 和 createWebHistory 函数
import { createRouter, createWebHistory } from 'vue-router'

// 创建路由实例
// 这里的 createWebHistory 函数用于创建基于 HTML5 History API 的路由历史
// import.meta.env.BASE_URL 是 Vite 提供的环境变量，用于获取项目的基 URL
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 重定向根路径到管理主页
    {
      path: '/',
      redirect: '/manager/home',
    },
    {
      // 定义路径为 /manager 的路由
      path: '/manager',
      // 使用动态导入的方式引入 Manager 组件作为该路由的视图组件
      // 这种方式可以提高应用的性能，因为它只在路由被访问时加载对应的组件
      component: () => import('@/views/Manager.vue'),
      // 定义一个名为主页的子路由
      children: [
        {
          // 子路由的路径为 /manager/home
          path: 'home',
          // 子路由的名称为 home
          meta:{name: '系统首页'},
          // 同样使用动态导入的方式引入 Editor 组件作为该子路由的视图组件
          component: () => import('@/views/manager/Home.vue'),
        },
        //定义管理员信息的子路由
        {
          path: 'admin',
          meta:{name: '管理员信息'},
          component: () => import('@/views/manager/Admin.vue'),
        },
        //定义管理系统个人中心的子路由
        {
          path: 'person',
          meta:{name: '个人资料'},
          component: () => import('@/views/manager/Person.vue'),
        },
        //定义管理修改密码的子路由
        {
          path: 'password',
          meta:{name: '修改密码'},
          component: () => import('@/views/manager/Password.vue'),
        },
        //定义管理系统公告的子路由
        {
          path: 'notice',
          meta:{name: '系统公告'},
          component: () => import('@/views/manager/Notice.vue'),
        },
        //定义管理用户信息的子路由
        {
          path: 'student',
          meta:{name: '用户信息'},
          component: () => import('@/views/manager/Student.vue'),
        },
         //定义管理菜品分类的子路由
         {
          path: 'category',
          meta:{name: '菜品分类'},
          component: () => import('@/views/manager/Category.vue'),
        },
        //定义管理菜品信息的子路由
        {
          path: 'dish',
          meta:{name: '菜品信息'},
          component: () => import('@/views/manager/Dish.vue'),
        },
        
      ],
    },
    // 定义路径为前台的路由
    {
      path: '/front',
      component: () => import('@/views/Front.vue'),
      // 定义前台的主页子路由
      children: [
        {
          path: 'home',
          component: () => import('@/views/front/Home.vue'),
        },
        //定义前台个人中心的子路由
        {
          path: 'person',
          meta:{name: '个人资料'},
          component: () => import('@/views/front/Person.vue'),
        },
        //定义前台修改密码的子路由
        {
          path: 'password',
          meta:{name: '修改密码'},
          component: () => import('@/views/front/Password.vue'),
        },
        //定义前台系统公告的子路由
        {
          path: 'notice',
          meta:{name: '系统公告'},
          component: () => import('@/views/front/Notice.vue'),
        },
      ],
    },
    {
      // 定义路径为 /login 的登录路由
      path: '/login',
      component: () => import('@/views/login.vue'),
    },
    {
      // 定义路径为 /register 的注册路由
      path: '/register',
      component: () => import('@/views/Register.vue'),
    },
    {
      // 定义路径为 /404 的路由
      path: '/404',
      component: () => import('@/views/404.vue'),
    },
    {
      path:'/:pathMatch(.*)*',
      redirect: '/404',
    }
  ],
})

// 导出创建好的路由实例
// 这个路由实例将在 Vue 应用中被使用，以实现路由导航功能
export default router
