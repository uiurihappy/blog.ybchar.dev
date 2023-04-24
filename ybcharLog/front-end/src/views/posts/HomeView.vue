<template>
  <div class="container">
    <ul class="post-list">
      <li
        v-for="post in pagedPosts"
        :key="post.id"
        @click="moveToRead(post.id)"
      >
        <div class="post-title">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
            post.title
          }}</router-link>
        </div>
        <!-- <div class="post-thumbnail">
          <img
            src="https://d1.awsstatic.com/asset-repository/products/amazon-rds/1024px-MySQL.ff87215b43fd7292af172e2a5d9b844217262571.png"
            alt="MySQL"
          />
        </div> -->

        <div class="post-sub">
          <div class="post-category">개발</div>
          <div class="post-date">{{ getFormattedDate(post.createdAt) }}</div>
        </div>
        <div class="post-content">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">
            <p v-html="truncateText(post.content, 300)"></p>
          </router-link>
        </div>
      </li>
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
import { ref, computed, nextTick } from 'vue';
import axios from 'axios';
import type { PostList, Posts } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import { truncateText } from '../../common/tools/truncateText.tool';
import { marked } from 'marked';

const router = useRouter();
const PAGE_SIZE = 10;
const currentPage = ref(1);

const posts = ref<PostList>({ list: [], totalCount: 0, totalElements: 0 });

axios
  .get(`/api/posts/list`)
  .then(result => {
    posts.value = result.data;
  })
  .catch(err => {
    console.log(err);
  });

const pagedPosts = computed(() => {
  const startIndex = (currentPage.value - 1) * PAGE_SIZE;
  return posts.value.list.slice(startIndex, startIndex + PAGE_SIZE);
});

const totalPages = computed(() =>
  Math.ceil(posts.value.totalCount / PAGE_SIZE)
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
};

const moveToRead = (postId: number) => {
  router.push({ name: 'read', params: { postId } });
  // nextTick(() => {
  //   window.scrollTo(0, 0);
  // });
};

const pagedPostsHtml = computed(() => {
  const startIndex = (currentPage.value - 1) * PAGE_SIZE;
  const pagedPostsHtmlArray = posts.value.list
    .slice(startIndex, startIndex + PAGE_SIZE)
    .map(post => {
      return {
        ...post,
        content: marked(post.content),
      };
    });
  return pagedPostsHtmlArray;
});
</script>

<style scoped lang="scss">
@import '@/assets/styles/home-view.scss';
/* 홈 화면에만 적용될 스타일 코드 */
</style>
