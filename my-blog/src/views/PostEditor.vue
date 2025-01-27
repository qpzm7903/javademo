<template>
  <div>
    <nav class="navigation">
      <button @click="cancelAndReturn" class="nav-button cancel-button">
        取消编辑返回首页
      </button>
      <button @click="savePost" class="nav-button save-button">
        保存文章
      </button>
    </nav>

    <div class="editor-container">
      <input 
        v-model="title" 
        class="title-input" 
        placeholder="标题" 
      />
      <textarea 
        v-model="content" 
        class="content-input" 
        placeholder="内容（Markdown格式）"
      ></textarea>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { usePostStore } from '../stores/postStore';
import { useRouter } from 'vue-router';

interface Post {
  id: number;
  title: string;
  content: string;
}

export default defineComponent({
  name: 'EditPost',
  data() {
    return {
      title: '',
      content: '',
    };
  },
  methods: {
    savePost() {
      const postStore = usePostStore();
      postStore.addPost({
        id: Date.now(),
        title: this.title,
        content: this.content,
      } as Post);
      this.$router.push('/');
    },
    cancelAndReturn() {
      this.$router.push('/');
    },
  },
});
</script>

<style scoped>
.navigation {
  margin: 16px 0;
  display: flex;
  gap: 12px;
}

.nav-button {
  padding: 8px 16px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  font-size: 1em;
}

.cancel-button {
  background-color: #f44336;
  color: white;
}

.cancel-button:hover {
  background-color: #d32f2f;
}

.save-button {
  background-color: #4CAF50;
  color: white;
}

.save-button:hover {
  background-color: #45a049;
}

.editor-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.title-input {
  width: 100%;
  font-size: 1.5em;
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.content-input {
  width: 100%;
  min-height: 400px;
  padding: 8px;
  margin-bottom: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-family: inherit;
  resize: vertical;
}
</style>