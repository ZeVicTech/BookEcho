<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";

const isOpen = ref(false)
const books = ref([])
const title = ref("")
const content = ref("")

const router = useRouter();

const write = function() {
  axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('user-token')}`;
  axios.post("/api/review",{
    bookId: 2,
    title: title.value,
    content: content.value,
    starPoint: 3
  })
      .then(()=>{
        router.replace({name:"home"});
      });
}

</script>

<template>

  <div class="black-bag" v-if="isOpen" style="z-index: 9999;">
    <div class="white-bg">
      <span class="close" @click="closeModal"></span>
      <h2>Select a Book</h2>
      <ul>
        <li v-for="(book, index) in books" :key="index">
          {{ book.title }}
          <button @click="selectBook(book)">Select</button>
        </li>
      </ul>
    </div>
  </div>

  <div class="mt-2">
  <el-input v-model="title" placeholder="제목을 입력해주세요"/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write">글 작성완료</el-button>
    </div>
  </div>
</template>

<style scoped>
.black-bag {
  width:100%; height:100%;
  background: rgba(0,0,0,0.5);
  position: fixed;
}
.white-bg{
  width: 50%; height: 50%;
  background: white;
  border-right: 8px;
  padding: 20px;

  position: absolute;
  top:50%;
  left:50%;
  transform : translate(-50%,-50%);
}
</style>