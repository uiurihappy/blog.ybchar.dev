<script setup lang="ts">
import { defineProps, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import MdEditor from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

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
</script>

<template>
  <div class="post-form-container">
    <el-form :model="form" class="post-form" label-width="120px">
      <el-form-item label="제목" class="form-item">
        <el-input
          v-model.trim="form.title"
          placeholder="제목을 입력해주세요"
          clearable
          class="form-input"
        />
      </el-form-item>

      <el-form-item label="내용" class="form-item">
        <MdEditor
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
.post-form-container {
  margin: 20px;
}

.post-form {
  max-width: 1200px;
  margin: auto;
}

.form-input-container {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
}

.form-label {
  font-size: 16px;
  font-weight: bold;
  width: 120px;
  margin-right: 20px;
}

.form-input-wrapper {
  flex: 1;

  .form-input {
    width: 100%;
    margin-right: 20px;
  }

  .form-button {
    width: 120px;
    height: 40px;
    font-size: 16px;
    border-radius: 4px;
    background-color: #13ce66;
    border-color: #13ce66;

    &:hover {
      background-color: #0dbf5b;
      border-color: #0dbf5b;
    }

    &:active,
    &:focus {
      background-color: #13ce66;
      border-color: #13ce66;
    }
  }
}

.form-item {
  margin-bottom: 20px;
}

.form-item > .el-form-item__label {
  font-size: 16px;
  font-weight: bold;
  width: 120px;
  margin-right: 20px;
}

.form-item > .el-form-item__content {
  flex: 1;
}

.form-item--submit {
  display: flex;
  justify-content: flex-end;
}

.preview-column {
  flex: 1;
}
</style>
