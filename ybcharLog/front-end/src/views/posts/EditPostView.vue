<template>
  <div class="container">
    <div class="edit-header">
      <h2 class="edit-title">글 수정</h2>
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
          <div id="editor" ref="editor" class="row">
            <editor v-model="updatePost.content" />
          </div>
        </el-form-item>

        <el-form-item label="노출 상태">
          <el-switch
            v-model="updatePost.display"
            active-color="#13ce66"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>
      <el-button
        style="justify-content: end"
        type="warning"
        @click="edit(updatePost.content)"
        >수정 완료</el-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

export default defineComponent({
  props: {
    postId: {
      type: [Number, String],
      required: true,
    },
  },

  setup(props, { emit }) {
    const router = useRouter();
    const checkDisplay = ref(0);
    const updatePost = ref({
      id: 0,
      title: '',
      content: '',
      display: checkDisplay.value,
    });

    const fetchData = async () => {
      try {
        const result = await axios.get(`/api/posts/${props.postId}`);
        updatePost.value.id = result.data.id;
        updatePost.value.title = result.data.title;
        updatePost.value.content = result.data.content;
        checkDisplay.value = result.data.display;
        updatePost.value.display = result.data.display;
      } catch (error) {
        alert('글 조회에 실패하였습니다.');
        console.error(error);
      }
    };

    const edit = async (content: string) => {
      console.log(content);

      try {
        await axios.patch(
          `/api/posts/update/${props.postId}`,
          {
            title: updatePost.value.title,
            content,
            display: Number(updatePost.value.display),
          },
          {
            headers: {
              Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
            },
          }
        );
        alert('글 수정이 완료되었습니다.');
        router.replace({ name: 'Home' });
      } catch (error) {
        console.error(error);
        alert('글 수정에 실패하였습니다.');
      }
    };

    const addImageBlobHook = (
      blob: Blob,
      callback: (url: string, altText: string) => void
    ) => {
      const formData = new FormData();
      formData.append('image', blob);
      axios
        .post('/api/upload/image', formData)
        .then(response => {
          const { imageUrl } = response.data;
          callback(imageUrl, 'Image description');
        })
        .catch(error => {
          console.error(error);
          alert('이미지 업로드에 실패하였습니다.');
        });
    };
    onMounted(async () => {
      await fetchData();

      const editor = new Editor({
        el: document.querySelector('#editor') as HTMLElement,
        height: '600px',
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        initialValue: updatePost.value.content,
        events: {
          change: () => {
            emit('update:modelValue', editor.getMarkdown());
          },
        },
      });
    });
    return {
      updatePost,
      edit,
      addImageBlobHook,
    };
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/edit-view.scss';
</style>
