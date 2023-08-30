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
    console.log(response)
    console.log(localStorage.getItem("user-token"))
    review.value = response.data
    });
});

</script>

<template>

  <el-row>
    <el-col>
      <h2 class="title">{{review.title}}</h2>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="sub d-flex">
        <div>{{review.book.title}}</div>
        <div class="create-date">{{review.createDateTime.substring(0,10)}}</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{review.content}}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title{
  margin: 0;
  font-size: 2rem;
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
  font-size: 0.8rem;
  color: #7b7b7b;

  .create-date{
    margin-left: 10px;
  }

}
</style>