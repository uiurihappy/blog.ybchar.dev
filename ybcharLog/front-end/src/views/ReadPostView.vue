<script setup lang="ts">
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { defineProps } from 'vue';
const router = useRouter();
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});
onMounted(() => {
  console.log(props.postId);

  axios
    .get(`/api/posts/${props.postId}`)
    .then(result => {
      console.log(result);
    })
    .catch(() => {
      alert('글 조회에 실패하였습니다.');
    });
});
</script>

<template>
  <div class="mt-2">
    <p>제목</p>
  </div>

  <div class="mt-2">
    <p>내용</p>
  </div>

  <div class="mt-2">
    <p>댓글</p>
  </div>
</template>

<style></style>
