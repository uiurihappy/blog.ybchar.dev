<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
// import dotenv from 'dotenv';
// dotenv.config();
const router = useRouter();
const title = ref('');
const content = ref('');
const display = ref(0);

const writePost = function () {
  axios
    .post(`/api/posts/save`, {
      title: title.value,
      content: content.value,
      display: display.value,
    })
    .then(() => {
      router.replace({ name: 'home' });
      alert('게시글 작성이 정상적으로 되었습니다.');
    })
    .catch(() => {
      alert('게시글 작성이 실패되었습니다.');
    });
};
</script>

<template>
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15" />
  </div>

  <div class="mt-2">
    노출 상태 <el-checkbox v-model="display" true-label="1" false-label="0" />
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="writePost()">작성완료</el-button>
    </div>
  </div>
</template>

<style></style>
