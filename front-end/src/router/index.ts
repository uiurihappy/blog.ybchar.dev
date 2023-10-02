import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/posts/HomeView.vue';
import WriteView from '../views/posts/WriteView.vue';
import ReadPostView from '../views/posts/ReadPostView.vue';
import EditPostView from '../views/posts/EditPostView.vue';
import LoginView from '../views/LoginView.vue';
import AboutView from '../views/about/AboutView.vue';
import jwt_decode from 'jwt-decode';
interface AccessToken {
  userRoles: string;
  // 다른 프로퍼티가 있다면 추가 가능
}

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
      meta: { requiresAuth: true, requiredRoles: 'ROLE_ADMIN' },
    },
    {
      path: '/read/:postId',
      name: 'Read',
      component: ReadPostView,
      props: true,
    },
    {
      path: '/edit/:postId',
      name: 'Edit',
      component: EditPostView,
      props: true,
      meta: { requiresAuth: true, requiredRoles: 'ROLE_ADMIN' },
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
      meta: { unauthorize: true },
    },
    {
      path: '/about',
      name: 'about',
      component: AboutView,
    },
  ],
});

router.beforeEach((to, from, next) => {
  const accessToken = sessionStorage.getItem('accessToken');
  const requiredRoles = to.meta.requiredRoles as string;

  if (to.matched.some(record => record.meta.unauthorize) && accessToken) {
    // 이미 로그인 되어있다면 로그인 페이지로 이동하지 않음
    next({ name: 'Home' });
  } else if (to.matched.some(record => record.meta.requiresAuth)) {
    // 로그인이 필요한 페이지일 경우
    if (!accessToken) {
      // 로그인 되어있지 않다면 로그인 페이지로 이동
      next({ name: 'Login' });
    } else {
      // 로그인 되어있다면 권한 체크
      const { userRoles }: AccessToken = jwt_decode(accessToken) as {
        userRoles: string;
      };

      if (requiredRoles === userRoles) {
        // 권한이 있으면 페이지 이동
        next();
      } else {
        // 권한이 없으면 Home으로 redirection
        next({ name: 'Home' });
      }
    }
  } else {
    // 권한 체크가 필요하지 않은 페이지
    next();
  }
});

export default router;
