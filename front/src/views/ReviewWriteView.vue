<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";
import { Picture as IconPicture } from '@element-plus/icons-vue';

const isOpen = ref(false)
const books = ref([]);
const query = ref();
const start = ref(1)
const display = ref(5)
const selectedBook = ref({
  title:"",
  author:"",
  publisher:"",
  pubdate:"",
  image:"",
  isbn:""
});

const review = ref({
  title:"",
  content:"",
  starPoint:0,
  book:{},
});

const router = useRouter();

const write = function() {
  axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('user-token')}`;
  axios.post("/api/review",{
    bookId: 2,
    starPoint: 3
  })
      .then(()=>{
        router.replace({name:"home"});
      });
};

const searchBook = function() {
  isOpen.value = true;
  //깃허브에 올릴땐 빼야함
  axios.get(`/naver?query=${query.value}&display=${display.value}&start=${start.value}`).then((response) => {
    books.value = []
    response.data.items.forEach((r : any) => {
      books.value.push(r);
    })
  });
  console.log(books)
};

</script>

<template>

  <div class="black-bag" v-if="isOpen" style="z-index: 9999;">
    <div class="white-bg">
      <h2>Select a Book</h2>
      <ul>
        <li v-for="(book, index) in books" :key="index">
          <div>
            <el-image src={{book.}}>
            </el-image>
          </div>
          <div>
            <p>책 제목: {{selectedBook.title}}</p>
            <p>작가 이름: {{selectedBook.author}}</p>
            <p>출판사: {{selectedBook.publisher}}</p>
            <p>출판일: {{selectedBook.pubdate}}</p>
          </div>
          <button @click="selectBook(book)">Select</button>
        </li>
      </ul>
    </div>
  </div>

  <div class="container">

    <div class="image">
      <el-image style="align-content: center">
        <template #error>
          <div class="image-slot">
            <el-icon><icon-picture /></el-icon>
          </div>
        </template>
      </el-image>
    </div>

    <div style="width: 250px">
      <p>책 제목: {{selectedBook.title}}</p>
      <p>작가 이름: {{selectedBook.author}}</p>
      <p>출판사: {{selectedBook.publisher}}</p>
      <p>출판일: {{selectedBook.pubdate}}</p>
      <el-rate v-model="review.starPoint" />
    </div>

    <div style="margin-left: 50px; display: flex; align-content: end">
      <el-input v-model="query" placeholder="책 이름을 입력해주세요"></el-input>
      <el-button class="ms-2" type="info" @click="searchBook">책 검색</el-button>
    </div>

  </div>


<!--  <div class="mt-2">-->
<!--  <el-input v-model="" placeholder="제목을 입력해주세요"/>-->
<!--  </div>-->

<!--  <div class="mt-2">-->
<!--    <el-input v-model="" type="textarea" rows="15"></el-input>-->
<!--  </div>-->

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
 .container {
   display: flex;
   align-items: center;
 }

.image {
  margin-right: 30px; /* 이미지와 텍스트 사이의 간격 조정 */
  width: 300px;
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 30px;
}
</style>