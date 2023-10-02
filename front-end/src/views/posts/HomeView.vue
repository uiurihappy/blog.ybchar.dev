<template>
  <div class="container">
    <ul class="post-list">
      <div class="post-grid">
        <div
          class="post-item"
          v-for="post in posts.list"
          :key="post.id"
          @click="moveToRead(post.id)"
        >
          <div v-if="post.thumbnailImage" class="post-thumbnail">
            <img :src="post.thumbnailImage" style="border-radius: 2.5%" />
          </div>

          <div class="post-title">
            <div v-if="post.thumbnailImage" class="post-if-title">
              <router-link
                :to="{ name: 'Read', params: { postId: post.id } }"
                >{{ truncateText(post.title, 24) }}</router-link
              >
            </div>
            <div v-else class="post-else-title">
              <router-link
                :to="{ name: 'Read', params: { postId: post.id } }"
                >{{ truncateText(post.title, 24) }}</router-link
              >
            </div>
          </div>

          <div class="post-content-box">
            <div v-if="post.thumbnailImage" class="post-content">
              <router-link :to="{ name: 'Read', params: { postId: post.id } }">
                <div
                  style="padding-top: 0.4rem"
                  v-html="truncateText(post.content, 100)"
                ></div>
              </router-link>
            </div>
            <div v-else class="post-else-content">
              <router-link :to="{ name: 'Read', params: { postId: post.id } }">
                <div
                  style="padding-top: 0.4rem"
                  v-html="truncateText(post.content, 310)"
                ></div>
              </router-link>
            </div>
            <!-- <div v-if="post.thumbnailImage" class="post-content">
              <router-link :to="{ name: 'Read', params: { postId: post.id } }">
                <div
                  class="post-content"
                  :key="post.id"
                  :id="`viewer-${post.id}`"
                  :ref="`viewer-${post.id}`"
                ></div>
              </router-link>
            </div>
            <div v-else class="post-else-content">
              <router-link :to="{ name: 'Read', params: { postId: post.id } }">
                <div
                  class="post-content"
                  :key="post.id"
                  :id="`viewer-${post.id}`"
                  :ref="`viewer-${post.id}`"
                ></div>
              </router-link>
            </div> -->
          </div>
          <div class="post-sub">
            <div v-if="post.thumbnailImage" class="post-if-sub">
              <div class="post-category">작성일:</div>
              <div class="post-date">
                {{ getFormattedDate(post.createdAt) }}
              </div>
            </div>
            <div v-else class="post-else-sub">
              <div class="post-category">작성일:</div>
              <div class="post-date">
                {{ getFormattedDate(post.createdAt) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </ul>
    <ul class="pagination">
      <li
        class="pagination-item"
        :class="{ disabled: currentPage === 1 }"
        @click="currentPage > 1 ? setCurrentPage(currentPage - 1) : null"
      >
        이전
      </li>
      <li
        v-for="page in pageNumbers"
        :key="page"
        class="pagination-item"
        :class="{ active: currentPage === page }"
        @click="setCurrentPage(page)"
      >
        {{ page }}
      </li>
      <li
        class="pagination-item"
        :class="{ disabled: currentPage === totalPages }"
        @click="
          currentPage < totalPages ? setCurrentPage(currentPage + 1) : null
        "
      >
        다음
      </li>
    </ul>
    <div class="page-count">페이지: {{ totalPages }}</div>
    <div class="post-count">전체 글 수: {{ posts.totalElements }}</div>
  </div>
</template>
<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import type { PostList } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import { truncateText } from '../../common/tools/truncateText.tool';
import MarkdownIt from 'markdown-it';

const router = useRouter();
const PAGE_SIZE = 12;
const currentPage = ref(1);
const posts = ref<PostList>({ list: [], totalCount: 0, totalElements: 0 });

const loadPosts = async () => {
  const md = new MarkdownIt();
  try {
    const result = await axios.get(
      `/api/posts/list?page=${currentPage.value}&size=${PAGE_SIZE}`
    );
    posts.value = {
      list: result.data.list.map((item: any) => ({
        ...item,
        content: md.render(item.content),
      })),
      totalCount: result.data.totalCount,
      totalElements: result.data.totalElements,
    };
  } catch {
    alert('글 조회에 실패하였습니다.');
  }
};

const totalPages = computed(() =>
  Math.ceil(posts.value.totalElements / PAGE_SIZE)
);

const pageNumbers = computed(() => {
  const numbers: number[] = [];
  for (let i = 1; i <= totalPages.value; i++) {
    numbers.push(i);
  }
  return numbers;
});

const setCurrentPage = async (page: number) => {
  currentPage.value = page;
  await loadPosts();
};

const moveToRead = (postId: number) => {
  router.push({ name: 'Read', params: { postId } });
};

onMounted(() => {
  setCurrentPage(currentPage.value);
});
</script>
<style scoped lang="scss">
@import '@/assets/styles/home-view.scss';
/* 홈 화면에만 적용될 스타일 코드 */
</style>
<!-- <script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue';
import axios from 'axios';
import type { PostList } from '../../common/posts/posts.interface';
import { useRouter } from 'vue-router';
import { getFormattedDate } from '../../common/tools/dateFormat.tool';
import { truncateText } from '../../common/tools/truncateText.tool';
import Editor from '@toast-ui/editor';
// Import prismjs
import Prism from 'prismjs';
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import type Viewer from '@toast-ui/editor/dist/toastui-editor-viewer';
import 'prismjs/themes/prism.css';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import _ from 'lodash';
const router = useRouter();
const PAGE_SIZE = 12;
const currentPage = ref(1);
const posts = ref<PostList>({ list: [], totalCount: 0, totalElements: 0 });
const viewers = ref<{ [key: number]: Viewer }>({});

const loadPosts = async () => {
  try {
    const result = await axios.get(
      `/api/posts/list?page=${currentPage.value}&size=${PAGE_SIZE}`
    );
    posts.value = {
      list: result.data.list.map((item: any) => ({
        ...item,
      })),
      totalCount: result.data.totalCount,
      totalElements: result.data.totalElements,
    };
    await updateViewerContent();
  } catch {
    alert('글 조회에 실패하였습니다.');
  }
};

const totalPages = computed(() =>
  Math.ceil(posts.value.totalElements / PAGE_SIZE)
);

const pageNumbers = computed(() => {
  const numbers: number[] = [];
  for (let i = 1; i <= totalPages.value; i++) {
    numbers.push(i);
  }
  return numbers;
});

const setCurrentPage = async (page: number) => {
  currentPage.value = page;
  await loadPosts();
};

const moveToRead = (postId: number) => {
  router.push({ name: 'Read', params: { postId } });
};

const updateViewerContent = async () => {
  const postList = posts.value.list;

  for (let i = 0; i < postList.length; i++) {
    const post = postList[i];
    const viewer = viewers.value[post.id];
    if (post.thumbnailImage) {
      await viewer?.setMarkdown(truncateText(post.content, 45));
    } else {
      await viewer?.setMarkdown(truncateText(post.content, 50));
    }
  }
};

onMounted(async () => {
  await loadPosts();
  const postList = posts.value.list;

  for (let i = 0; i < postList.length; i++) {
    const post = postList[i];

    const viewer = Editor.factory({
      el: document.querySelector(`#viewer-${post.id}`),
      viewer: true,
      initialValue: '',
      plugins: [[codeSyntaxHighlight, { highlighter: Prism }]],
    });
    viewers.value[post.id] = viewer;
  }
  await updateViewerContent();
});
</script> -->
