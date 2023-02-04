// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router/index.js'
import store from '@/store';
import axios from 'axios'

// 导入组件库
import ElementUI from 'element-ui'
// 导入组件样式
import 'element-ui/lib/theme-chalk/index.css';
// 配置ElementUI插件
Vue.use(ElementUI)
// Change internal http to axios
Vue.prototype.$http = axios 
axios.defaults.baseURL="http://localhost:8086/lib"

// Add axios interceptors for request
axios.interceptors.request.use(config => {
  // For every request, a token is bought
  var token = sessionStorage.getItem('token')
  config.headers['Authorization']=token // Request header with token
  return config
},error=>{
  return Promise.reject(error)
})

/**User router hook to handle */
router.beforeEach((to, from, next) => {
  const username = sessionStorage.getItem('username')
  if (to.name !== 'login' && !username) next({ name: 'login'})
  else next()
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
