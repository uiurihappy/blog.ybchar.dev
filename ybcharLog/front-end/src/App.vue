<script setup lang="ts">
import Header from './components/Header.vue';
import Content from './components/Content.vue';
</script>

<template>
  <div style="background-color: #ffdddd">
    <Header />
    <Content />
  </div>
</template>

<style>
body {
  margin: auto;
  width: 100%;
  padding-top: 1rem;

  font-family: 'Pretendard Variable', Pretendard, -apple-system,
    BlinkMacSystemFont, system-ui, Roboto, 'Helvetica Neue', 'Segoe UI',
    'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic', 'Apple Color Emoji',
    'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
}
</style>
