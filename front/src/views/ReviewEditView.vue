<script setup lang="ts">
import {useRouter} from "vue-router";
import {defineProps, ref} from "vue";
import axios from "axios";

const router = useRouter();

const review = ref({
  id:0,
  title:"1",
  content: "1",
})

const props = defineProps({
  reviewId: {
    type: [Number,String],
    require: true,
  }
});

axios.defaults.headers.common['Authorization'] = `${localStorage.getItem('user-token')}`;

// 기존에 작성되어 있던 것을 가져옴
axios.get(`/api/review/${props.reviewId}`).then((response) => {
  review.value = response.data;
});

const edit = () => {
  axios.patch(`/api/posts/${props.reviewId}`, review.value).then(() => {
    router.replace({name:"home"});
  })
}

</script>

<template>
  <div>
    <el-input v-model="review.title"/>
  </div>

  <div class="mt-2">
    <el-input v-model="review.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>

</template>


<style scoped>

</style>