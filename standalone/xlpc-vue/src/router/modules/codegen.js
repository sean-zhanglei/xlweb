

import Layout from '@/layout'

const storeRouter = {
    path: '/codegen',
    component: Layout,
    redirect: '/codegen/codegenList',
    name: 'Store',
    meta: {
      title: '代码生成',
      icon: 'clipboard'
    },
    children: [
      {
        path: 'codegenList',
        component: () => import('@/views/codegen/codegenList'),
        name: 'StoreIndex',
        meta: { title: '待生成列表', icon: '' }
      }
    ]
  }

export default storeRouter
