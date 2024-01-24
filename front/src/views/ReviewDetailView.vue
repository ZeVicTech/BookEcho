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
})

const moveToEdit = () => {
  router.push({name:"edit", params: {reviewId: props.reviewId}})
}

onMounted(()=>{
  axios.get(`/api/review/${props.reviewId}`).then((response) => {
    review.value = response.data
    });
});

</script>

<template>

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

  <div class="m-lg-5">
    <h2 class="mt-4 title">{{review.title}}</h2>

    <div class="sub d-flex">
      <div>{{review.member.nickName}}</div>
      <div class="create-date">{{review.createDateTime.substring(0,10)}}</div>
    </div>

    <div class="mt-3 content">{{review.content}}</div>

    <div class="mt-3 d-flex justify-content-end">
      <el-button type="warning" @click="moveToEdit">수정</el-button>
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
</style>