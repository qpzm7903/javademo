<template>
    <div>
      <h1>博客首页</h1>
      <div class="post-list">
        <!-- 列表头部 -->
        <div class="post-header">
          <span class="post-id sortable" @click="sort('id')">
            ID
            <span class="sort-icon">
              {{ getSortIcon('id') }}
            </span>
          </span>
          <span class="post-title-header sortable" @click="sort('title')">
            文章名称
            <span class="sort-icon">
              {{ getSortIcon('title') }}
            </span>
          </span>
          <span class="post-author">作者</span>
          <span class="post-summary">摘要</span>
          <span class="post-actions">操作</span>
        </div>
        
        <!-- 列表内容 -->
        <div v-for="post in sortedPosts" :key="post.id" class="post-item">
          <span class="post-id">#{{ post.id }}</span>
          <router-link :to="`/post/${post.id}`" class="post-title">
            {{ post.title }}
          </router-link>
          <span class="post-author">{{ post.author || '匿名' }}</span>
          <span class="post-summary">{{ getSummary(post.content) }}</span>
          <div class="post-actions">
            <button 
              @click="deletePost(post.id)" 
              class="nav-button delete-button"
            >
              删除
            </button>
          </div>
        </div>
      </div>
      <router-link to="/edit" class="nav-button write-button" @click="handleClick">
        写文章
      </router-link>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref, computed } from 'vue';
  import { usePostStore } from '../stores/postStore';
  import { useRouter } from 'vue-router'
  
  export default defineComponent({
    setup() {
      const postStore = usePostStore();
      const router = useRouter()
      const sortField = ref('id')
      const sortOrder = ref('asc') // 'asc' 或 'desc'

      const deletePost = (id: number) => {
        if (confirm('确定要删除这篇文章吗？')) {
          postStore.deletePost(id)
        }
      }

      const handleClick = () => {
        console.log('链接被点击了')
        router.push('/edit')
      }

      // 获取文章摘要
      const getSummary = (content: string) => {
        if (!content) return '暂无内容'
        return content.slice(0, 50) + (content.length > 50 ? '...' : '')
      }

      const sort = (field) => {
        if (sortField.value === field) {
          // 如果点击的是当前排序字段，则切换排序顺序
          sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc'
        } else {
          // 如果点击的是新字段，则设置为升序
          sortField.value = field
          sortOrder.value = 'asc'
        }
      }

      const getSortIcon = (field) => {
        if (sortField.value !== field) return '⇅'
        return sortOrder.value === 'asc' ? '↑' : '↓'
      }

      const sortedPosts = computed(() => {
        const posts = [...postStore.posts]
        return posts.sort((a, b) => {
          const aValue = a[sortField.value]
          const bValue = b[sortField.value]
          
          if (sortOrder.value === 'asc') {
            return aValue > bValue ? 1 : -1
          } else {
            return aValue < bValue ? 1 : -1
          }
        })
      })

      return {
        sortedPosts,
        deletePost,
        handleClick,
        getSummary,
        sort,
        getSortIcon
      };
    }
  });
  </script>

  <style scoped>
  .post-list {
    margin: 20px 0;
    border: 1px solid #ddd;
    border-radius: 4px;
    width: 100%;
    table-layout: fixed;
  }

  .post-header, .post-item {
    display: grid;
    grid-template-columns: 80px 200px 120px 1fr 100px;
    gap: 16px;
    padding: 12px;
    align-items: center;
  }

  .post-header {
    background-color: #f5f5f5;
    border-bottom: 1px solid #ddd;
    font-weight: bold;
  }

  .post-item {
    border-bottom: 1px solid #ddd;
  }

  .post-item:last-child {
    border-bottom: none;
  }

  .post-id {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .post-title-header,
  .post-title {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .post-title {
    text-decoration: none;
    color: #1976D2;
  }

  .post-title:hover {
    text-decoration: underline;
  }

  .post-author {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .post-summary {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #666;
  }

  .post-actions {
    text-align: center;
  }

  .nav-button {
    padding: 6px 12px;
    border-radius: 4px;
    text-decoration: none;
    display: inline-block;
    border: none;
    cursor: pointer;
    font-size: 0.9em;
  }

  .write-button {
    background-color: #4CAF50;
    color: white;
    margin-top: 20px;
  }

  .write-button:hover {
    background-color: #45a049;
  }

  .delete-button {
    background-color: #f44336;
    color: white;
  }

  .delete-button:hover {
    background-color: #d32f2f;
  }

  .sortable {
    cursor: pointer;
    user-select: none;
    display: flex;
    align-items: center;
    gap: 4px;
  }

  .sortable:hover {
    color: #1976D2;
  }

  .sort-icon {
    font-size: 0.8em;
    color: #666;
  }

  .sortable:hover .sort-icon {
    color: #1976D2;
  }

  /* 确保图标和文字在同一行 */
  .post-id, .post-title-header {
    display: flex;
    align-items: center;
    gap: 4px;
  }
  </style>