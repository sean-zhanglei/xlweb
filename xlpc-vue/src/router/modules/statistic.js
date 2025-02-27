import Layout from '@/layout';

export default {
  path: '/statistic',
  name: 'statistic',
  redirect: '/statistic/product',
  alwaysShow: true,
  component: Layout,
  children: [
    {
      path: 'transaction',
      name: 'transaction',
      meta: {
        title: '交易统计',
      },
      component: () => import('@/views/statistic/transaction/index'),
    },
    {
      path: 'integral',
      name: 'integral',
      meta: {
        title: '积分统计',
      },
      component: () => import('@/views/statistic/integral/index'),
    },
    {
      path: 'order',
      name: 'order',
      meta: {
        title: '订单统计',
      },
      component: () => import('@/views/statistic/order/index'),
    },
    {
      path: 'balance',
      name: 'balance',
      meta: {
        title: '余额统计',
      },
      component: () => import('@/views/statistic/balance/index'),
    },
  ],
};
