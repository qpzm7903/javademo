import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import Home from '../views/Home.vue';
import PostDetail from '../views/PostDetail.vue';
import PostEditor from '../views/PostEditor.vue';

const routes: Array<RouteRecordRaw> = [
  { 
    path: '/', 
    name: 'home',
    component: Home 
  },
  { 
    path: '/post/:id(\\d+)',  // 确保 id 是数字
    name: 'post-detail',
    component: PostDetail,
    props: route => ({
      id: Number(route.params.id)  // 确保转换为数字
    })
  },
  { 
    path: '/edit', 
    name: 'edit',
    component: PostEditor,
    props: route => ({
      id: route.query.id ? Number(route.query.id) : undefined
    })
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 添加导航守卫用于调试
router.beforeEach((to, from) => {
  console.log('路由跳转:', { 
    from: from.path, 
    to: to.path,
    toComponent: to.matched[0]?.components?.default,  // 查看目标组件是否存在
  });
  return true;
})

export default router;