<script setup lang = "ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

const props = defineProps({
  reviewId: {
    type: [Number,String],
    require: true,
  }
});

const review = ref({
  id:0,
  title:"",
  content:"",
  starPoint:"",
  createDateTime:"",
  book:{
    title: "",
    author: "",
    pubdate: "",
    publisher: "",
    image: "",
    isbn: ""
  },
  member:{
    id:0,
    nickName:""
  },
  comments:[
      {
        id:0,
        content:'',
        createDateTime:'',
        member:{
          id:0,
          nickName:''
        }
      }
  ]
})

// 전송용 댓글 변수
const submitCommnet = ref({
  content:""
})

const moveToEdit = () => {
  router.push({name:"edit", params: {reviewId: props.reviewId}});
}

const deleteReview = () => {
  axios.delete(`/api/review/${props.reviewId}`).then(() => {
    router.replace({name: 'home'});
  });
}

//댓글 등록 함수
const writeComment = () => {
  axios.post(`/api/review/${props.reviewId}/comment`,submitCommnet.value).then(() => {
    fetchData();
    submitCommnet.value.content='';
  });
}

//댓글 삭제 함수
const deleteComment = (commentId) => {
  axios.delete(`/api/comment/${commentId}`).then(() => {
    fetchData();
  });
}

//리뷰 데이터 갱신 함수
const fetchData = () =>{
  axios.get(`/api/review/${props.reviewId}`).then((response) => {
    review.value = response.data;
    console.log(review.value)
  });
}

onMounted(()=>{
  fetchData();
});

</script>

<template>
  <!--책 정보 화면-->
  <div class="m-lg-5">
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
        <el-rate disabled v-model="review.starPoint"/>
      </div>
    </div>

    <!--리뷰 내용 화면-->
    <div>
      <h2 class="mt-4 title">{{review.title}}</h2>
      <div class="sub d-flex">
        <div>{{review.member.nickName}}</div>
        <div class="create-date">{{review.createDateTime.substring(0,10)}}</div>
      </div>
      <div class="mt-3 content">{{review.content}}</div>
      <div class="mt-3 d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit">수정</el-button>
        <el-button type="danger" @click="deleteReview">삭제</el-button>
      </div>
    </div>

    <hr class="mt-3 mb-3">

    <h2>댓글</h2>
    <!--댓글 등록 화면-->
    <div>
      <el-input
        v-model='submitCommnet.content'
        :autosize='{ minRows: 3}'
        type='textarea'
        placeholder='댓글 내용을 입력해주세요'
        />
      <div class="mt-2 d-flex justify-content-end">
        <el-button type="primary" @click="writeComment">댓글 등록</el-button>
      </div>
    </div>

    <!--댓글 목록 화면-->
    <div>
      <ul v-if="review.comments.length > 0">
        <li class="mb-5" v-for="comment in review.comments" :key="comment.id">
          <div class="sub d-flex">
            <div>{{comment.member.nickName}}</div>
            <div class="create-date">{{comment.createDateTime.substring(0,10)}}</div>
          </div>
          <div class="d-flex justify-content-between">
            <div class="comment mt-2">{{comment.content}}</div>
            <el-button type="danger" @click="deleteComment(comment.id)">삭제</el-button>
          </div>
        </li>
      </ul>
    </div>

  </div>

</template>

<style scoped lang="scss">
.container {
  display: flex;
  align-items: center;
}

.title{
  margin: 0;
  font-size: 3rem;
  font-weight: 600;
  color: #383838;
}

.content{
  font-size: 1rem;
  margin-top: 4px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub{
  margin-top: 10px;
  font-size: 1rem;
  color: #7b7b7b;

  .create-date{
    margin-left: 10px;
  }
}

ul{
  list-style:none;
  padding-left: 0;
}

.comment{
  white-space: break-spaces;
  color: #616161;
}

</style>