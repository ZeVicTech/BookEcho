<template>
  <el-header class="header">
    <!--        <RouterLink to="/">Home</RouterLink>-->
    <!--        <RouterLink to="/write">리뷰 작성</RouterLink>-->

    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write" >리뷰 작성</el-menu-item>
      <el-menu-item index="/login">로그인</el-menu-item>
      <el-menu-item index="/signup">회원가입</el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts">

</script>

<style scoped>
.header{
  padding: 0;
}
</style>