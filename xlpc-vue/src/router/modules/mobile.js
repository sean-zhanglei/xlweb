

import Layout from '@/layout'

const mobileRouter = {
  path: '/mobile',
  component: Layout,
  redirect: '/dashboard',
  name: 'Mobile',
  alwaysShow: true,
  meta: {
    title: '移动端',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'orderCancellation',
      component: () => import('@/views/mobile/orderCancellation/index.vue'),
      name: 'OrderCancellation',
      meta: { title: '订单核销', icon: '' }
    },
    {
      path: 'orderStatistics',
      component: () => import('@/views/mobile/orderStatistics/index.vue'),
      name: 'OrderStatistics',
      meta: { title: '订单统计' }
    },
    {
      path: 'orderList/:types?',
      component: () => import('@/views/mobile/orderStatistics/orderList.vue'),
      name: 'OrderList',
      meta: { title: '订单列表' }
    },
    {
      path: 'orderDelivery/:oid/:id?',
      component: () => import('@/views/mobile/orderStatistics/orderDelivery.vue'),
      name: 'OrderDelivery',
      meta: { title: '订单发货' }
    },
    {
      path: 'orderDetail/:id?/:goname?',
      component: () => import('@/views/mobile/orderStatistics/orderDetail.vue'),
      name: 'OrderDetail',
      meta: { title: '订单详情' }
    },
    {
      path: 'orderStatisticsDetail/:type/:time?',
      component: () => import('@/views/mobile/orderStatistics/Statistics.vue'),
      name: 'OrderStatisticsDetail',
      meta: { title: '订单数据统计' }
    },
    {
      path: 'productAdd/:id?/:isDisabled?',
      component: () => import('@/views/mobile/productAdd/index.vue'),
      name: 'productAdd',
      meta: { title: '添加商品', icon: '' }
    },
    {
      path: 'productManage',
      component: () => import('@/views/mobile/productManage/index.vue'),
      name: 'productManage',
      meta: { title: '商品管理', icon: '' }
    }
  ]
}

export default mobileRouter
