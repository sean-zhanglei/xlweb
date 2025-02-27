

import Layout from '@/layout'

const financialRouter = {
  path: '/financial',
  component: Layout,
  redirect: '/financial',
  name: 'Financial',
  meta: {
    title: '财务',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'commission',
      component: () => import('@/views/financial/index'),
      name: 'Commission',
      meta: { title: '财务操作', icon: '' },
      alwaysShow: true,
      children: [
        {
          path: 'template',
          component: () => import('@/views/financial/commission/withdrawal/index'),
          name: 'commissionTemplate',
          meta: { title: '申请提现', icon: '' }
        }
      ]
    },
    {
      path: 'record',
      component: () => import('@/views/financial/record/index'),
      name: 'financialRecord',
      meta: { title: '财务记录', icon: '' },
      alwaysShow: true,
      children: [
        {
          path: 'charge',
          component: () => import('@/views/financial/record/charge/index'),
          name: 'Charge',
          meta: { title: '充值记录', icon: '' }
        },
        {
          path: 'monitor',
          component: () => import('@/views/financial/record/monitor/index'),
          name: 'Monitor',
          meta: { title: '资金监控', icon: '' }
        },
        {
          path: 'billingRecords',
          name: `billingRecords`,
          meta: {
            title: '账单记录',icon: ''
          },
          component: () => import('@/views/financial/record/billingRecords/index'),
        },
      ]
    },
    {
      path: 'brokerage',
      component: () => import('@/views/financial/brokerage/index'),
      name: 'Brokerage',
      meta: { title: '佣金记录', icon: '' }
    },
    {
      path: 'balance',
      name: 'balance',
      meta: {
        title: '余额记录',icon: ''
      },
      component: () => import('@/views/financial/balance/index'),
    },
  ]
}

export default financialRouter
