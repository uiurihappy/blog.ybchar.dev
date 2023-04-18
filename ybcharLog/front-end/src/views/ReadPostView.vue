<script setup lang="ts">
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { defineProps } from 'vue';
import type { Comments } from '../common/comments/comments.interface';

const router = useRouter();
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

const post = ref({
  id: 0,
  title: '',
  content: '',
  comments: [] as Comments[],
});

const moveToEdit = () => {
  router.push({ name: 'edit', params: { postId: props.postId } });
};

onMounted(() => {
  axios
    .get(`/api/posts/${props.postId}`)
    .then(result => {
      post.value = result.data;
    })
    .catch(() => {
      alert('글 조회에 실패하였습니다.');
    });
});
</script>

<template>
  <div class="mt-2">
    <h2>제목: {{ post.title }}</h2>
  </div>

  <div class="mt-2">글 내용: {{ post.content }}</div>
  <br />
  <div class="d-flex justify-content-end">
    <el-button type="warning" @click="moveToEdit()"> 수정하기 </el-button>
  </div>
  <h2>댓글</h2>
  <div class="mt-2">
    <ul>
      <li v-for="comment in post.comments" :key="comment.id">
        <div>
          <p>{{ comment.username }}</p>
        </div>
        <div>
          <p>{{ comment.commentContent }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<style></style>
