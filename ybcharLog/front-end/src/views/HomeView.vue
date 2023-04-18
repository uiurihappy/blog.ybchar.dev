<script setup lang="ts">
import axios from 'axios';
import type { PostList, Posts } from '../common/posts/posts.interface';
import { useRouter } from 'vue-router';
const router = useRouter();

const posts: PostList = {
  list: [],
  totalCount: 0,
  totalElements: 0,
};
const getPostList = () => {
  axios
    .get(`/api/posts/list`)
    .then(result => {
      result.data.list.forEach((item: Posts) => {
        posts.list.push(item);
      });
      posts.totalCount = result.data.totalCount;
      posts.totalElements = result.data.totalElements;

      console.log(posts);
    })
    .catch(err => {
      console.log(err);
    });
  return posts;
};

const moveToRead = () => {
  router.push({ name: 'read' });
};
</script>

<template>
  <div>
    {{ posts.totalCount }}
  </div>
  <div>
    {{ posts.totalElements }}
  </div>
  <ul>
    <li v-for="post in getPostList().list" :key="post.id" @click="moveToRead()">
      <div>
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
          post.title
        }}</router-link>
        <br />
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
          post.content
        }}</router-link>
      </div>
    </li>
  </ul>
</template>

<style scoped>
li {
  margin-bottom: 1rem;
}

li:last-child {
  margin-bottom: 0;
}
</style>
