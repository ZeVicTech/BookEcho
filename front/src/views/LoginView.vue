<script setup lang='ts'>
import { ref } from 'vue';
import axios from "axios";
import {useRouter} from "vue-router";
import { userStore } from "@/stores/userStore";

const router = useRouter();

const form = ref({
  loginId: '',
  password: ''
});

const login = () => {
    axios.post("/api/auth/login",form.value).then((response)=>{
      userStore.nickName = response.data['nickName']
      userStore.accessToken = response.data['accessToken']
      //TODO: 새로고침할때 다시 연결해줘야 되는 정보를 로컬스토리지에 담아놨음
      localStorage.setItem('user-nickName', response.data['nickName'])
      localStorage.setItem('user-accessToken', response.data['accessToken']);
      router.replace({name:"home"});
    })
};
</script>

<template>
  <el-form :model="form" class="container mt-5" label-width="120px">

    <el-form-item label="아이디">
      <el-input v-model="form.loginId"></el-input>
    </el-form-item>

    <el-form-item label="비밀번호">
      <el-input type="password" v-model.number="form.password"></el-input>
    </el-form-item>

    <div style="margin-left: 110px">
      <el-button type='primary' @click='login()'>로그인</el-button>
    </div>
  </el-form>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 100px;
  height: 100vh; /* viewport height */
}
</style>
