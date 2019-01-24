
const create = function() {
    return {
	data: function() {
	    // FOR TEST
	    return {username: 'Adam', password: '123456'};
	},
	template: `
<div>
      <el-input placeholder="在此处输入用户名" v-model="username">
	<template slot="prepend">用户</template>
      </el-input>
      <el-input placeholder="在此处输入密码" v-model="password">
	<template slot="prepend">密码</template>
      </el-input>
      <el-button type="primary" v-on:click="$emit('command-login', {username,password})">登陆</el-button>
</div>
`
    };
}


export { create };


