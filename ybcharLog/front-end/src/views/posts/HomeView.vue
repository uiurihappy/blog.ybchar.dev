<template>
  <div class="container">
    <div class="page-count">페이지: {{ posts.totalCount }}</div>
    <div class="post-count">전체 글 수: {{ posts.totalElements }}</div>
    <ul class="post-list">
      <li
        v-for="post in posts.list"
        :key="post.id"
        @click="moveToRead(post.id)"
      >
        <div class="post-title">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
            post.title
          }}</router-link>
        </div>
        <div class="post-content">
          <router-link :to="{ name: 'read', params: { postId: post.id } }">
            <p v-text="truncateText(post.content, 120)"></p>
          </router-link>
        </div>
        <div class="post-sub">
          <div class="post-category">개발</div>
          <div class="post-date">{{ getFormattedDate(post.createdAt) }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import type { PostList, Posts } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import { truncateText } from '../../common/tools/truncateText.tool';
const router = useRouter();

const posts = ref<PostList>({ list: [], totalCount: 0, totalElements: 0 });

axios
  .get(`/api/posts/list`)
  .then(result => {
    console.log(result);

    posts.value = result.data;
  })
  .catch(err => {
    console.log(err);
  });

const moveToRead = (postId: number) => {
  router.push({ name: 'read', params: { postId } });
};
</script>

<style scoped lang="scss">
@import '@/assets/styles/home-view.scss';

/* 홈 화면에만 적용될 스타일 코드 */
</style>
