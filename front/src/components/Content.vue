<template>
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView/>
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup lang="ts"></script>

<style scoped>
.content {
  margin: 0;
  padding: 0;
}
</style>