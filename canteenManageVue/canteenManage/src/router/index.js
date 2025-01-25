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
        //定义管理轮播图的子路由
        {
          path: 'sideshow',
          meta:{name: '轮播图管理'},
          component: () => import('@/views/manager/Sideshow.vue'),
        },
        //定义管理浏览记录的子路由
        {
          path: 'history',
          meta:{name: '浏览记录管理'},
          component: () => import('@/views/manager/History.vue'),
        },
        //定义管理点赞记录的子路由
        {
          path: 'praise',
          meta:{name: '点赞记录管理'},
          component: () => import('@/views/manager/Praise.vue'),
        },
        //定义管理收藏记录的子路由
        {
          path: 'collect',
          meta:{name: '收藏记录管理'},
          component: () => import('@/views/manager/Collect.vue'),
        },
        //定义管理反馈意见的子路由
        {
          path: 'feedback',
          meta:{name: '反馈意见管理'},
          component: () => import('@/views/manager/Feedback.vue'),
        },
        //定义管理食堂资讯的子路由
        {
          path: 'Information',
          meta:{name: '食堂资讯管理'},
          component: () => import('@/views/manager/Information.vue'),
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
        //定义前台菜品详情的子路由
        {
          path: 'dishDetail',
          meta:{name: '菜品详情'},
          component: () => import('@/views/front/DishDetail.vue'),
        },
         //定义前台浏览历史的子路由
         {
          path: 'history',
          meta:{name: '浏览历史'},
          component: () => import('@/views/front/History.vue'),
        },
         //定义前台点赞记录的子路由
         {
          path: 'praise',
          meta:{name: '点赞记录'},
          component: () => import('@/views/front/Praise.vue'),
        },
        //定义前台收藏记录的子路由
        {
          path: 'collect',
          meta:{name: '收藏记录'},
          component: () => import('@/views/front/Collect.vue'),
        },
        //定义前台意见反馈的子路由
        {
          path: 'feedback',
          meta:{name: '意见反馈'},
          component: () => import('@/views/front/Feedback.vue'),
        },
        //定义前台我的意见反馈的子路由
        {
          path: 'myFeedback',
          meta:{name: '我的意见反馈'},
          component: () => import('@/views/front/MyFeedback.vue'),
        },
      ],
    },
    {
      // 定义路径为 /login 的登录路由
      path: '/login',
      component: () => import('@/views/Login.vue'),
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
      // 定义路径为 /404 的路由
      path: '/403',
      component: () => import('@/views/403.vue'),
    },
    {
      path:'/:pathMatch(.*)*',
      redirect: '/404',
    }
  ],
})

// 定义路由守卫的回调函数，用于处理路由导航的逻辑
router.beforeEach((to, from, next) => {
  //to是要进入的目标路由对象
  //from 是当前导航正在离开的路由对象
  //next 是一个函数，用于控制导航的下一步操作
  // 检查是否为登录注册页面，如果是，则放行
  if (to.path === '/login' || to.path === '/register') {
    next();
    return;
  }

  let adminPath = '/manager'
  let user = JSON.parse(localStorage.getItem('project-user' || '{}'))
  if(user.role !== 'ADMIN' && to.path.startsWith(adminPath)){
    next('/403')
  }else{
    next()
  }
})

// 导出创建好的路由实例
// 这个路由实例将在 Vue 应用中被使用，以实现路由导航功能
export default router
