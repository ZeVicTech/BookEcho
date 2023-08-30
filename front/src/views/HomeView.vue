<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import router from "@/router";

const reviews = ref([]);

// 리뷰 전체 조회 요청
axios.get("/api/review?page=1&sort=createDateTime,desc").then((response) => {
  response.data.forEach((r : any) => {
    reviews.value.push(r);
  });
});

//
const moveToReviewRead = () => {
  router.push({name:"read"})
}

</script>

<template>
  <ul>
    <li v-for="review in reviews" :key="review.id" @click="moveToReviewRead">

      <el-row :gutter="20" style="margin-left: 25rem">
        <el-col span="1">
            <el-image style="width: 250px; height: 250px" :fit="'scale-down'" src="https://shopping-phinf.pstatic.net/main_4064133/40641337634.20230823071535.jpg"></el-image>
        </el-col>

        <el-col span="3" style="width: 400px">
          <el-row>
            <div class="title">
              <router-link :to="{name:'read',params: {reviewId: review.id}}">
                {{review.title}}
              </router-link>
            </div>
          </el-row>

          <el-row>
            <el-rate
                v-model="review.starPoint"
                disabled
                text-color="#ff9900"
            />
          </el-row>

          <el-row>
            <el-text class="text-ellipsis">{{review.content}}</el-text>
          </el-row>

          <el-row>
            <div class="sub d-flex">
              <div>{{review.member.nickName}}</div>
              <div class="create-date">{{review.createDateTime.substring(0,10)}}</div>
            </div>
          </el-row>
        </el-col>
      </el-row>


    </li>
  </ul>
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
      font-size: 2.5rem;
      color: #383838;
      text-decoration: none;
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
