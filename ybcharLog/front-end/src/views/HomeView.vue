<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import type { PostList, Posts } from '../common/posts/posts.interface';
import { useRouter } from 'vue-router';
const router = useRouter();

const posts = ref({ list: [] as Posts[], totalCount: 0, totalElements: 0 });
// PostList = {
//   list: [],
//   totalCount: 0,
//   totalElements: 0,
// };
axios
  .get(`/api/posts/list`)
  .then(result => {
    console.log(result);

    result.data.list.forEach((item: Posts) => {
      posts.value.list.push(item);
    });

    posts.value.totalCount = result.data.totalCount;
    posts.value.totalElements = result.data.totalElements;
  })
  .catch(err => {
    console.log(err);
  });

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
    <li v-for="post in posts.list" :key="post.id" @click="moveToRead()">
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
