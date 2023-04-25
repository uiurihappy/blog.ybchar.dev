<template>
  <div class="container">
    <ul class="post-list">
      <div class="post-grid">
        <div
          v-for="post in pagedPosts"
          :key="post.id"
          @click="moveToRead(post.id)"
        >
          <div class="post-title">
            <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
              post.title
            }}</router-link>
          </div>
          <div v-if="post.thumbnailImage" class="post-thumbnail">
            <img :src="post.thumbnailImage" />
          </div>

          <div class="post-sub">
            <div class="post-category">작성일:</div>
            <div class="post-date">{{ getFormattedDate(post.createdAt) }}</div>
          </div>
          <div class="post-content-box">
            <div class="post-content">
              <router-link :to="{ name: 'read', params: { postId: post.id } }">
                <p v-html="truncateText(post.content, 150)"></p>
              </router-link>
            </div>
          </div>
        </div>
      </div>
      <!-- <li
        v-for="post in pagedPosts"
        :key="post.id"
        @click="moveToRead(post.id)"
      >
        <div class="post-title">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
            post.title
          }}</router-link>
        </div>
        <div v-if="post.thumbnailImage" class="post-thumbnail">
          <img :src="post.thumbnailImage" />
        </div>

        <div class="post-sub">
          <div class="post-category">개발</div>
          <div class="post-date">{{ getFormattedDate(post.createdAt) }}</div>
        </div>
        <div class="post-content">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">
            <p v-html="truncateText(post.content, 300)"></p>
          </router-link>
        </div>
      </li> -->
    </ul>
    <ul class="pagination">
      <li
        class="pagination-item"
        :class="{ disabled: currentPage === 1 }"
        @click="currentPage > 1 ? setCurrentPage(currentPage - 1) : null"
      >
        이전
      </li>
      <li
        v-for="page in pageNumbers"
        :key="page"
        class="pagination-item"
        :class="{ active: currentPage === page }"
        @click="setCurrentPage(page)"
      >
        {{ page }}
      </li>
      <li
        class="pagination-item"
        :class="{ disabled: currentPage === totalPages }"
        @click="
          currentPage < totalPages ? setCurrentPage(currentPage + 1) : null
        "
      >
        다음
      </li>
    </ul>
    <div class="page-count">페이지: {{ totalPages }}</div>
    <div class="post-count">전체 글 수: {{ posts.totalElements }}</div>
  </div>
</template>
<script setup lang="ts">
import { ref, computed, nextTick, onMounted } from 'vue';
import axios from 'axios';
import type { PostList, Posts } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import { truncateText } from '../../common/tools/truncateText.tool';
import { marked } from 'marked';

const router = useRouter();
const PAGE_SIZE = 12;
const currentPage = ref(1);

const posts = ref<PostList>({ list: [], totalCount: 0, totalElements: 0 });

const loadPosts = async () => {
  try {
    const result = await axios.get(
      `/api/posts/list?page=${currentPage.value}&size=${PAGE_SIZE}`
    );

    posts.value = result.data;
  } catch (err) {
    console.log(err);
  }
};

// loadPosts();
const pagedPosts = computed(() => {
  const startIndex = (currentPage.value - 1) * PAGE_SIZE;
  const endIndex = Math.min(posts.value.list.length, startIndex + PAGE_SIZE);
  return posts.value.list.slice(startIndex, endIndex);
});

const totalPages = computed(() =>
  Math.ceil(posts.value.totalElements / PAGE_SIZE)
);
const pageNumbers = computed(() => {
  const numbers: number[] = [];
  for (let i = 1; i <= totalPages.value; i++) {
    numbers.push(i);
  }
  return numbers;
});

const setCurrentPage = (page: number) => {
  currentPage.value = page;
  // nextTick();
  loadPosts();
};

const moveToRead = (postId: number) => {
  router.push({ name: 'read', params: { postId } });
};

const pagedPostsHtml = computed(() => {
  const startIndex = (currentPage.value - 1) * PAGE_SIZE;
  const endIndex = Math.min(posts.value.list.length, startIndex + PAGE_SIZE);
  const pagedPostsHtmlArray = posts.value.list
    .slice(startIndex, endIndex)
    .map(post => {
      return {
        ...post,
        content: marked(post.content),
      };
    });
  return pagedPostsHtmlArray;
});

onMounted(() => {
  axios
    .get(`/api/posts/list?page=${currentPage.value}&size=${PAGE_SIZE}`)
    .then(result => {
      posts.value = result.data;
    })
    .catch(() => {
      alert('글 조회에 실패하였습니다.');
    });
});
</script>

<style scoped lang="scss">
@import '@/assets/styles/home-view.scss';
/* 홈 화면에만 적용될 스타일 코드 */
</style>
