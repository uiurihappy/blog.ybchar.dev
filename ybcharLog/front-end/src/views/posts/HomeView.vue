<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import type { PostList, Posts } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';

import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
dayjs.extend(timezone);

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
  <!-- <div>
    {{ posts.totalCount }}
  </div>
  <div>
    {{ posts.totalElements }}
  </div> -->
  <ul>
    <li v-for="post in posts.list" :key="post.id" @click="moveToRead()">
      <div class="title">
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
          post.title
        }}</router-link>
      </div>

      <div class="content">
        <br />
        <router-link :to="{ name: 'read', params: { postId: post.id } }">{{
          post.content
        }}</router-link>
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>

        <div class="regDate">
          {{
            post.createdAt === null
              ? dayjs().format('YYYY-MM-DD HH:mm:ss')
              : dayjs(post.createdAt).format('YYYY-MM-DD HH:mm:ss')
          }}
        </div>
      </div>
    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;
  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }
      a:hover {
        font: 1.2rem system-ui;
        text-decoration: underline;
      }
    }

    .content {
      a {
        font-size: 0.95rem;
        color: #5d5d5d;
        text-decoration: none;
        white-space: break-spaces;
        line-height: 1.5;
      }

      a:hover {
        color: #adadad;
        text-decoration: underline;
      }
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 5px;
      margin-bottom: 5px;
      font-size: 0.78rem;
      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}
</style>
