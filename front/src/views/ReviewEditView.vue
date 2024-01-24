<script setup lang="ts">
import {defineProps, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import { Picture as IconPicture } from '@element-plus/icons-vue';
import { naverApiKey } from '@/stores/naverApiKey';

const props = defineProps({
  reviewId: {
    type: [Number,String],
    require: true,
  }
});

const router = useRouter();
let isOpen = ref(false)
// 검색한 책 정보 저장 변수
const books = ref([]);
const query = ref("");
let start = 1;
let page = 1;
const display = 5;
let total = 9999;

const review = ref({
  title:"",
  content:"",
  starPoint:0,
  createDateTime:"",
  book:{
    title:"",
    author:"",
    publisher:"",
    pubdate:"",
    image:"",
    isbn:""
  },
});

axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('user-token')}`;

// 기존에 작성되어 있던 것을 가져옴
axios.get(`/api/review/${props.reviewId}`).then((response) => {
  review.value = response.data;
});

const edit = () => {
  console.log(review.value)
  axios.patch(`/api/review/${props.reviewId}`, review.value).then(() => {
    router.go(-1);
  })
}

const searchBook = function() {
  isOpen.value = true;
  axios.defaults.headers.common['X-Naver-Client-Id'] = naverApiKey.X_Naver_Client_Id;
  axios.defaults.headers.common['X-Naver-Client-Secret'] = naverApiKey.X_Naver_Client_Secret;
  axios.get(`/naver?query=${query.value}&display=${display}&start=${start}`).then((response) => {
    total = response.data.total
    books.value = []
    response.data.items.forEach((r : any) => {
      books.value.push(r);
    })
  });
  console.log(books);
};

// 책선택 함수
const selectBook = function(book) {
  review.value.book = book
  closeSearch();
};

const closeSearch = function () {
  isOpen.value = false;
  start = 1;
  page = 1;
}

// 책검색 다음 페이지 함수
const nextPage = function () {
  if(start+5 >= total)
    return;
  start += 5;
  page += 1;
  searchBook();
}
// 책검색 이전 페이지 함수
const prevPage = function () {
  if(start - 5 < 1)
    return
  start -= 5
  page -= 1
  searchBook()
}

</script>

<template>
  <!--책 선택 모달 창-->
  <div v-if="isOpen" class="black-bg">
    <div class="white-bg">
      <div style="display: flex; justify-content: end">
        <el-button type="primary" @click="closeSearch">닫기</el-button>
      </div>
      <div style="display: flex; justify-content: center">
        <h2>책을 선택해주세요</h2>
      </div>
      <div v-for="(book, index) in books" :key="index" class="book-info-container">
        <div>
          <el-image style="width: 200px; height: 200px" :src = book.image fit='scale-down'/>
        </div>
        <div class="ms-3" style="width: 300px">
          <p>책 제목: {{book.title}}</p>
          <p>작가 이름: {{book.author}}</p>
          <p>출판사: {{book.publisher}}</p>
          <p>출판일: {{book.pubdate}}</p>
        </div>
        <div class="ms-3">
          <el-button type="primary" @click="selectBook(book)">책 선택</el-button>
        </div>
      </div>
      <div class="mt-3" style="display: flex; justify-content: center">
        <el-button @click="prevPage">이전 페이지</el-button>
        <span class="ms-3 me-3 mt-2">{{page}}</span>
        <el-button @click="nextPage">다음 페이지</el-button>
      </div>
    </div>
  </div>

  <div class="container">
    <div>
      <el-image class="image" fit="scale-down" style="align-content: center; width: 300px;height: 300px;" :src = review.book.image>
        <template #error>
          <div class="image-slot">
            <el-icon><icon-picture /></el-icon>
          </div>
        </template>
      </el-image>
    </div>

    <div style="width: 250px">
      <p>책 제목: {{review.book.title}}</p>
      <p>작가 이름: {{review.book.author}}</p>
      <p>출판사: {{review.book.publisher}}</p>
      <p>출판일: {{review.book.pubdate}}</p>
      <el-rate v-model="review.starPoint" />
    </div>

    <div style="margin-left: 50px; display: flex; align-content: end">
      <el-input v-model="query" placeholder="책 이름을 입력해주세요"></el-input>
      <el-button class="ms-2" type="info" @click="searchBook">책 검색</el-button>
    </div>
  </div>

  <div class="m-lg-5">
    <div class="mt-4">
      <el-input v-model="review.title" placeholder="제목을 입력해주세요"/>
    </div>

    <div class="mt-2">
      <el-input v-model="review.content" type="textarea" rows="15"></el-input>
    </div>

    <div class="mt-2">
      <div class="d-flex justify-content-end">
        <el-button type="primary" @click="edit">글 수정완료</el-button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.book-container {
  display: flex;
  align-items: center;
  height: 300px;
}

.black-bg {
  z-index: 9999;
  width:100%; height:100%;
  background: rgba(0,0,0,0.5);
  position: fixed;

  display:flex; /* Added */
  align-items:center; /* Added */
  justify-content:center; /* Added */
}
.white-bg{
  width: 70%;height: 70%; /* height 값을 auto로 변경 */
  border-radius: 8px;
  background: white;
  padding: 20px;

  overflow: auto;
}

.book-info-container { /* New style for each book info container */
  height: auto;
  display:flex ;
  flex-direction :row ;
  justify-content:center ;
  align-items:center ;
  margin-bottom: 20px;
  margin-left: auto;
  margin-right: auto;
  width:60%;
  border-bottom :1px solid black;
}

.container {
  display: flex;
  align-items: center;
}

.image {
  margin-right: 30px; /* 이미지와 텍스트 사이의 간격 조정 */
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
  /*margin-bottom: 50px;*/
}

.el-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>