<script setup lang="ts">
import { defineProps, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
// import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

let editorInstance = null;
const loadMdEditor = async () => {
  const { MdEditor } = await import('md-editor-v3');
  editorInstance = new MdEditor();
  // 이후에 필요한 작업 수행
};
const props = defineProps({
  post: { type: Object },
});

const router = useRouter();
const form = ref({
  title: props.post ? props.post.title : '',
  content: props.post ? props.post.content : '',
  display: props.post ? Boolean(props.post.display) : true,
});
const loading = ref(false);

const submitForm = async function () {
  loading.value = true;
  const postData = { ...form.value, display: form.value.display ? 1 : 0 };

  try {
    if (props.post) {
      await axios.patch(`/api/posts/${props.post.id}`, postData, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
        },
      });
      alert('게시글이 수정되었습니다.');
    } else {
      await axios.post('/api/posts/save', postData, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
        },
      });
      alert('게시글이 작성되었습니다.');
    }
    router.push({ name: 'Home' });
  } catch (error) {
    console.error(error);
    alert('게시글 작성/수정에 실패하였습니다.');
  }

  loading.value = false;
};

// const beforeUpload = (file: any) => {
//   const formData = new FormData();
//   const path = `post/thumbnail/${props.postId}`;
//   formData.append('file', file);
//   formData.append('path', '/' + path);
//   formData.append('postId', props.postId);
//   $refs.dropzone.uploadFiles(formData);
//   return false;
// };

// const onUploadSuccess = () => {
//   alert('썸네일 이미지 등록이 완료되었습니다.');
// };
</script>

<template>
  <div class="post-form-container">
    <el-form :model="form" class="post-form" label-width="120px">
      <el-form-item label="제목">
        <el-input v-model="form.title" placeholder="제목을 입력해주세요" />
      </el-form-item>

      <el-form-item label="내용" class="form-item">
        <loadMdEditor
          v-model="form.content"
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

      <el-form-item label="노출 여부" class="form-item">
        <el-switch
          v-model="form.display"
          active-color="#13ce66"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>

      <!-- <el-form-item label="썸네일 이미지 등록">
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
      </el-form-item> -->

      <el-form-item class="form-item form-item--submit">
        <el-button
          type="primary"
          @click="submitForm"
          :loading="loading"
          class="form-button"
        >
          작성 완료
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/styles/write-view.scss';
</style>
