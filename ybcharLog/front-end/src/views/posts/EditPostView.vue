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

        <el-form-item label="내용" class="form-item">
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
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const { MdEditor } = require('md-editor-v3');
import 'md-editor-v3/lib/style.css';

export default defineComponent({
  props: {
    postId: {
      type: [Number, String],
      required: true,
    },
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

    const edit = async function () {
      await axios
        .patch(
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
        )
        .then(() => {
          alert('글 수정이 완료되었습니다.');
          router.replace({ name: 'Home' });
        })
        .catch(() => alert('글 수정에 실패하였습니다.'));
    };

    return {
      updatePost,
      edit,
      MdEditor,
    };
  },
});
</script>

<style lang="scss" scoped>
@import '@/assets/styles/edit-view.scss';
</style>
