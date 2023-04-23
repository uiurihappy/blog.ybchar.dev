<template>
  <div class="container">
    <div class="edit-header">
      <h2 class="edit-title">글 수정</h2>
      <el-button type="warning" @click="edit()">수정 완료</el-button>
    </div>
    <hr class="edit-divider" />

    <div class="edit-form">
      <el-form label-width="80px">
        <el-form-item label="제목">
          <el-input
            v-model="updatePost.title"
            placeholder="제목을 입력해주세요"
          />
        </el-form-item>

        <el-form-item label="내용">
          <el-input
            v-model="updatePost.content"
            type="textarea"
            :autosize="{ minRows: 10, maxRows: 20 }"
            placeholder="내용을 입력해주세요"
          />
        </el-form-item>

        <el-form-item label="노출 상태">
          <el-switch
            v-model="updatePost.display"
            :active-value="1"
            :inactive-value="0"
            active-color="#13ce66"
            inactive-color="#ff4949"
          />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

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

const router = useRouter();

const checkDisplay = ref(0);

const updatePost = ref({
  id: 0,
  title: '',
  content: '',
  display: checkDisplay.value,
});

axios
  .get(`/api/posts/${props.postId}`)
  .then(result => {
    updatePost.value.id = result.data.id;
    updatePost.value.title = result.data.title;
    updatePost.value.content = result.data.content;
    checkDisplay.value = result.data.display;
    updatePost.value.display = result.data.display;
  })
  .catch(() => {
    alert('글 조회에 실패하였습니다.');
  });

const edit = () => {
  axios
    .patch(`/api/posts/update/${props.postId}`, {
      title: updatePost.value.title,
      content: updatePost.value.content,
      display: Number(updatePost.value.display),
    })
    .then(() => {
      alert('글 수정이 완료되었습니다.');
      router.replace({ name: 'Home' });
    })
    .catch(() => alert('글 수정에 실패하였습니다.'));
};
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

.edit-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.edit-title {
  margin: 0;
}

.edit-divider {
  margin-top: 1.5rem;
  margin-bottom: 1.5rem;
  border-top: 1px solid #eee;
}

.edit-form {
  margin-top: 2rem;
}
</style>
