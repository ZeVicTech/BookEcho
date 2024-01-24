<template>
  <el-header class="header">
    <el-menu class="menu" mode="horizontal" router :default-active="$route.path">
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write" >리뷰 작성</el-menu-item>
      <el-menu-item index="/login">로그인</el-menu-item>
      <el-menu-item index="/signup">회원가입</el-menu-item>
      <el-text class="memberInfo" style="margin-left: auto" v-if="userStore.nickName !== ''"> {{ userStore.nickName }} 님</el-text>
      <el-text class="memberInfo" style="margin-left: auto" v-else> 로그인 해주세요 </el-text>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts">
// let nickName = localStorage.getItem("user-nickName")
import {userStore} from "@/stores/userStore";
</script>

<style scoped>
.header{
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.memberInfo{
  font-size: 1rem;
  margin-left: auto;
  margin-right: 10px;
}
.menu {
  flex-grow: 1;
}
</style>