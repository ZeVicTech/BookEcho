<script setup lang="ts">
import {ref, onMounted} from "vue";
import axios from "axios";
import router from "@/router";

type Book = {
  title: string;
  author: string;
  pubdate: string;
  publisher: string;
  image: string;
  isbn: string;
};

type Member = {
  id: number;
  nickName: string;
};

type Review = {
  id: number;
  title: string;
  content: string;
  starPoint: number;
  createDateTime: string;
  book: Book;
  member: Member;
};

const reviews = ref<Review[]>([]);
const isLoading = ref(true);

let pageNo = 1
let isLastPage = false
// let totalPages = 9999

// 리뷰 전체 조회 요청
const fetchData = () =>{
  axios.get(`/api/review?page=${pageNo}&sort=createDateTime,desc`).then((response) => {
    isLoading.value = true;
    reviews.value = response.data.review;
    console.log(response.data.review)
    isLastPage = response.data.last;
    isLoading.value = false;
  });
}

onMounted(()=>{
  console.log(import.meta.env)
  fetchData();
});

const nextPage = function () {
  if(isLastPage)
    return;
  pageNo+=1;
  fetchData();
}
// 책검색 이전 페이지 함수
const prevPage = function () {
  if(pageNo==1)
    return;
  pageNo-=1;
  fetchData();
}


//리뷰 디테일 화면으로 이동
const moveToReviewRead = () => {
  router.push({name:"read"})
}

</script>

<template>
  <div v-if="isLoading">Loading...</div>
  <div v-else>
    <ul class="">
      <li v-for="review in reviews" :key="review.id" @click="moveToReviewRead">
        <div class="d-flex justify-content-center align-items-center">
          <div>
            <el-image style="width: 250px; height: 250px;"  fit="scale-down" :src="review.book.image"></el-image>
          </div>
          <div class="flex align-self-start" style="width: 500px;">
            <div class="title">
              <router-link :to="{name:'read',params: {reviewId: review.id}}">
                {{review.title}}
              </router-link>
            </div>
            <div>
              <el-rate
                  v-model="review.starPoint"
                  disabled
                  text-color="#ff9900"
              />
            </div>
            <div>
              <el-text class="text-ellipsis">{{review.content}}</el-text>
            </div>
            <div>
              <div class="sub d-flex">
                <div>{{review.member.nickName}}</div>
                <div class="create-date">{{review.createDateTime.substring(0,10)}}</div>
              </div>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>

  <div class="mt-3 mb-5" style="display: flex; justify-content: center">
    <el-button @click="prevPage">이전 페이지</el-button>
    <span class="ms-3 me-3 mt-2">{{pageNo}}</span>
    <el-button @click="nextPage">다음 페이지</el-button>
  </div>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  /*padding: 0;*/
}

li {
  margin-bottom: 100px;

  .title{
    a{
      width: 500px;
      font-size: 2.5rem;
      color: #383838;
      text-decoration: none;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    &:hover {
      text-decoration: underline;
    }
  }

  .content{
    font-size: 1.3rem;
    margin-top: 8px;
    color: #6b6b6b;
  }

  &:last-child{
    margin-bottom: 0;
  }
}

  .sub{
    margin-top: 8px;
    font-size: 0.8rem;
    color: #7b7b7b;

    .create-date{
      margin-left: 10px;
    }
}

.item-box{
  height: 250px;
}

.text-ellipsis {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

</style>
