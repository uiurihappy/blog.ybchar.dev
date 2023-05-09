import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/posts/HomeView.vue';
import WriteView from '../views/posts/WriteView.vue';
import ReadPostView from '../views/posts/ReadPostView.vue';
import EditPostView from '../views/posts/EditPostView.vue';
import LoginView from '../views/LoginView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView,
    },
    {
      path: '/write',
      name: 'Write',
      component: WriteView,
    },
    {
      path: '/read/:postId',
      name: 'read',
      component: ReadPostView,
      props: true,
    },
    {
      path: '/edit/:postId',
      name: 'edit',
      component: EditPostView,
      props: true,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { unauthorize: true },
    },
    //   {
    //     path: '/about',
    //     name: 'about',
    //     // route level code-splitting
    //     // this generates a separate chunk (About.[hash].js) for this route
    //     // which is lazy-loaded when the route is visited.
    //     component: () => import('../views/AboutView.vue')
    //   }
  ],
});

export default router;
