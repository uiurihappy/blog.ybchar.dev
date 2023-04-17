<script setup lang="ts">
import axios from 'axios';
import type { PostList, Posts } from '../common/posts/posts.interface';

const getPostList = () => {
  const posts: PostList = {
    list: [],
    totalCount: 0,
    totalElements: 0,
  };

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
</script>

<template>
  <ul>
    <li v-for="post in getPostList().list" :key="post.id">
      <div>
        {{ post.title }}
      </div>
      <div>
        {{ post.content }}
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
