<script setup lang="ts">
import { defineProps, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});
axios
  .get(`/api/posts/${props.postId}`)
  .then(result => {
    updatePost.value.id = result.data.id;
    updatePost.value.title = result.data.title;
    updatePost.value.content = result.data.content;
    checkDisplay.value = result.data.display;
  })
  .catch(() => {
    alert('글 조회에 실패하였습니다.');
  });
const router = useRouter();
const checkDisplay = ref(1);
const updatePost = ref({
  id: 0,
  title: '',
  content: '',
  display: checkDisplay.value,
});

const edit = () => {
  axios
    .patch(`/api/posts/update/${props.postId}`, {
      title: updatePost.value.title,
      content: updatePost.value.content,
      display: Number(checkDisplay.value),
    })
    .then(() => {
      alert('글 수정이 완료되었습니다.');
      router.replace({ name: 'home' });
    })
    .catch(() => alert('글 수정에 실패하였습니다.'));
};
</script>

<template>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()"> 수정 </el-button>
  </div>
  <div>
    <el-input v-model="updatePost.title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="updatePost.content" type="textarea" rows="15" />
  </div>

  <div class="mt-2">
    <input
      type="checkbox"
      v-model="checkDisplay"
      true-value="1"
      false-value="0"
    />
    <el-label> 노출 상태 </el-label>
  </div>
</template>

<style></style>
