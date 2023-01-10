import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/common/Home.vue'
import Customer from '@/views/Customer.vue'
import Order from '@/views/Order.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/Customer',
          name: 'Customer',
          component: Customer
        },{
          path: '/Order',
          name: 'Order',
          component: Order
        }
      ]
    }
  ]
})
