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
          <MdEditor
            v-model="updatePost.content"
            :editable="true"
            :subfield="false"
            :defaultOpen="true"
            :toolbarsFlag="true"
            :previewMode="true"
            :scrollStyle="{
              height: '800px',
              'max-height': '800px',
              'min-height': '400px',
            }"
            placeholder="마크다운 내용을 입력해주세요."
            style="height: 800px"
          />
        </el-form-item>

        <el-form-item label="노출 상태">
          <el-switch
            v-model="updatePost.display"
            active-color="#13ce66"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
        <el-form-item label="썸네일 이미지 등록">
          <el-upload
            ref="dropzone"
            action="/api/posts/thumbnail/image"
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-success="onUploadSuccess"
          >
            <el-button size="small" type="primary">파일 선택</el-button>
            <template v-slot:tip>
              <div class="el-upload__tip">썸네일 이미지를 업로드하세요</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

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

const postThumbnailEdit = () => {
  const formData = new FormData();
  const image = $refs.dropzone.getAcceptedFiles()[0];
  const path = `post/thumbnail/${props.postId}`;
  formData.append('file', image);
  formData.append('path', path);
  formData.append('postId', props.postId);
  console.log(formData);

  axios
    .post(`/api/posts/thumbnail/image`, formData)
    .then(() => {
      alert('썸네일 이미지 등록이 완료되었습니다.');
    })
    .catch(() => {
      alert('썸네일 이미지 등록 실패되었습니다.');
    });
};

// postThumbnailEdit 함수 삭제

const beforeUpload = (file: any) => {
  const formData = new FormData();
  const path = `post/thumbnail/${props.postId}`;
  formData.append('file', file);
  formData.append('path', '/' + path);
  formData.append('postId', props.postId);
  $refs.dropzone.uploadFiles(formData);
  return false;
};

const onUploadSuccess = () => {
  alert('썸네일 이미지 등록이 완료되었습니다.');
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
