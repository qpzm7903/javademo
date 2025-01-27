<template>
  <div class="container py-4">
    <!-- Header -->
    <header class="pb-3 mb-4 border-bottom">
      <div class="d-flex justify-content-between align-items-center">
        <h1 class="h3 mb-0">我的博客</h1>
        <router-link to="/edit" class="btn btn-primary" @click="handleClick">
          <i class="bi bi-plus-lg"></i> 写文章
        </router-link>
      </div>
    </header>

    <!-- Main Content -->
    <main>
      <div class="card shadow-sm">
        <div class="card-body p-0">
          <div class="table-responsive">
            <table class="table table-hover mb-0">
              <thead class="table-light">
                <tr>
                  <th class="sortable" @click="sort('id')" style="width: 80px;">
                    ID 
                    <i class="bi" :class="getSortIconClass('id')"></i>
                  </th>
                  <th class="sortable" @click="sort('title')" style="width: 200px;">
                    文章名称
                    <i class="bi" :class="getSortIconClass('title')"></i>
                  </th>
                  <th style="width: 120px;">作者</th>
                  <th>摘要</th>
                  <th style="width: 100px;" class="text-center">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in sortedPosts" :key="post.id">
                  <td class="align-middle">#{{ post.id }}</td>
                  <td class="align-middle">
                    <router-link :to="`/post/${post.id}`" class="text-decoration-none">
                      {{ post.title }}
                    </router-link>
                  </td>
                  <td class="align-middle">{{ post.author || '匿名' }}</td>
                  <td class="align-middle text-muted">{{ getSummary(post.content) }}</td>
                  <td class="text-center">
                    <button 
                      @click="deletePost(post.id)" 
                      class="btn btn-danger btn-sm"
                      title="删除"
                    >
                      <i class="bi bi-trash"></i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer class="pt-5 my-5 text-muted border-top">
      Created with Bootstrap · {{ new Date().getFullYear() }}
    </footer>
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

    const getSortIconClass = (field) => {
      if (sortField.value !== field) return 'bi-arrow-down-up'
      return sortOrder.value === 'asc' ? 'bi-arrow-up' : 'bi-arrow-down'
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
      getSortIconClass
    };
  }
});
</script>

<style scoped>
.sortable {
  cursor: pointer;
  user-select: none;
}

.sortable:hover {
  background-color: rgba(0, 0, 0, 0.05);
}

/* 添加 Bootstrap Icons */
@import url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css");

/* 自定义样式 */
.table th {
  font-weight: 500;
}

.table td {
  max-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>