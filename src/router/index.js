import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/common/Home.vue'

import CommonCustomer from '@/components/pages/customer/CommonCustomer.vue'
import CustomerList from '@/components/pages/customer/CustomerList.vue'
import AllOrder from '@/components/pages/order/AllOrder.vue'
import MyOrder from '@/components/pages/order/MyOrder.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      children: [
        {
          path: 'CommonCustomer',
          name: 'commonCustomer',
          component: CommonCustomer
          }, {
          path: 'CustomerList',
          name: 'customerList',
          component: CustomerList
          }, {
          path: 'AllOrder',
          name: 'allOrder',
          component: AllOrder
          }, {
          path: 'MyOrder',
          name: 'myOrder',
          component: MyOrder
          }
      ]
    }
  ]
})
