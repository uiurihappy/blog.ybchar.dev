<script setup lang="ts">
import { defineProps, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
// import dotenv from 'dotenv';
// dotenv.config();
const router = useRouter();

const getPost = ref({
  id: 0,
  title: '',
  content: '',
  display: 0,
});

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

axios
  .get(`/api/posts/${props.postId}`)
  .then(result => {
    getPost.value = result.data;
  })
  .catch(() => {
    alert('글 조회에 실패하였습니다.');
  });

const edit = () => {
  axios
    .patch(`/api/posts/update/${props.postId}`, getPost.value)
    .then(() => {
      alert('글 수정이 완료되었습니다.');
      router.replace({ name: 'home' });
    })
    .catch(() => alert('글 수정에 실패하였습니다.'));
};
</script>

<template>
  <div>
    <el-input v-model="getPost.title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="getPost.content" type="textarea" rows="15" />
  </div>

  <div class="mt-2">
    노출 상태
    <el-checkbox v-model="getPost.display" true-label="1" false-label="0" />
  </div>

  <div class="mt-2 d-flect justify-content-end">
    <el-button type="warning" @click="edit()"> 수정 </el-button>
  </div>
</template>

<style></style>
