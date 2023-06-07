<template>
  <div class="container">
    <div class="edit-header">
      <h2 class="edit-title">글 수정</h2>
      <el-button type="warning" @click="edit">수정 완료</el-button>
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

        <el-form-item label="내용" class="form-item">
          <Editor
            v-model="updatePost.content"
            :initialEditType="'wysiwyg'"
            :previewStyle="'vertical'"
            :height="'500px'"
            :initialValue="updatePost.content"
            :hooks="{ addImageBlobHook: addImageBlobHook }"
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
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Editor } from '@toast-ui/vue-editor';
import '@toast-ui/editor/dist/toastui-editor.css';

export default defineComponent({
  props: {
    postId: {
      type: [Number, String],
      required: true,
    },
  },
  components: {
    Editor,
  },
  setup(props) {
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

    const edit = async () => {
      try {
        await axios.patch(
          `/api/posts/update/${props.postId}`,
          {
            title: updatePost.value.title,
            content: updatePost.value.content,
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

    return {
      updatePost,
      edit,
      Editor,
      addImageBlobHook,
    };
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/edit-view.scss';
</style>
