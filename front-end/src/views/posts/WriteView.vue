<template>
  <div class="post-form-container">
    <el-form :model="form" class="post-form" label-width="120px">
      <el-form-item label="제목">
        <el-input v-model="form.title" placeholder="제목을 입력해주세요" />
      </el-form-item>

      <el-form-item label="내용" class="form-item">
        <div id="editor" ref="editor" class="row">
          <editor v-model="form.content" />
        </div>
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

<script lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
export default {
  props: {
    post: {
      type: Object,
      required: false,
    },
  },

  setup(props, { emit }) {
    const router = useRouter();
    const form = ref({
      title: props.post ? props.post.title : '',
      content: props.post ? props.post.content : '',
      display: props.post ? Boolean(props.post.display) : true,
    });
    const loading = ref(false);

    const submitForm = async function () {
      loading.value = true;
      const editorInstance = editorRef.value;
      const postData = {
        ...form.value,
        content: editorInstance.getMarkdown(),
        display: form.value.display ? 1 : 0,
      };

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

    const editorRef = ref<Editor>(null);

    onMounted(async () => {
      const editor = new Editor({
        el: document.querySelector('#editor'),
        height: '600px',
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        initialValue: '',
        events: {
          change: () => {
            emit('update:modelValue', editor.getMarkdown());
          },
        },
      });

      editorRef.value = editor;
    });
    return {
      form,
      loading,
      submitForm,
    };
  },
};
</script>

<style lang="scss" scoped>
@import '@/assets/styles/write-view.scss';
@import '@toast-ui/editor/dist/toastui-editor.css';
</style>
