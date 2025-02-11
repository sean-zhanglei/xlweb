

import Layout from '@/layout'

const operationRouter = {
  path: '/operation',
  component: Layout,
  redirect: '/operation/setting',
  name: 'Operation',
  meta: {
    title: '设置',
    icon: 'clipboard',
    roles: ['admin']
  },
  children: [
    {
      path: 'setting',
      name: 'setting',
      component: () => import('@/views/systemSetting/setting'),
      meta: {
        title: '系统设置',
        icon: 'clipboard'
      }
    },
    {
      path:'notification',
      name:'notification',
      component: () => import('@/views/systemSetting/notification'),
      meta:{
        title:'消息通知',
        icon:'clipboard'
      }
    },
    {
      path: 'roleManager',
      name: 'RoleManager',
      component: () => import('@/views/systemSetting/administratorAuthority'),
      meta: {
        title: '管理权限',
        icon: 'clipboard',
        roles: ['admin']
      },
      children: [
        {
          path: 'identityManager',
          component: () => import('@/views/systemSetting/administratorAuthority/identityManager'),
          name: 'identityManager',
          meta: { title: '角色管理', icon: '' }
        },
        {
          path: 'adminList',
          component: () => import('@/views/systemSetting/administratorAuthority/adminList'),
          name: 'adminList',
          meta: { title: '管理员列表', icon: '' }
        },
        {
          path: 'promiseRules',
          component: () => import('@/views/systemSetting/administratorAuthority/permissionRules'),
          name: 'promiseRules',
          meta: { title: '权限规则', icon: '' }
        }
      ]
    },
    {
      path: 'deliverGoods',
      name: 'deliverGoods',
      alwaysShow: true,
      component: () => import('@/views/systemSetting/deliverGoods'),
      meta: {
        title: '发货设置',
        roles: ['admin']
      },
      children: [
        {
          path: 'takeGoods',
          component: () => import('@/views/systemSetting/deliverGoods/takeGoods'),
          name: 'takeGoods',
          meta: { title: '提货设置', noCache: true,roles: ['admin'] },
          children: [
            {
              path: 'deliveryAddress',
              component: () => import('@/views/systemSetting/deliverGoods/takeGoods/deliveryAddress'),
              name: 'deliveryAddress',
              meta: { title: '提货点', icon: '' }
            },
            {
              path: 'collateOrder',
              component: () => import('@/views/systemSetting/deliverGoods/takeGoods/collateOrder'),
              name: 'collateOrder',
              meta: { title: '核销订单', icon: '' }
            },
            {
              path: 'collateUser',
              component: () => import('@/views/systemSetting/deliverGoods/takeGoods/collateUser'),
              name: 'collateUser',
              meta: { title: '核销员', icon: '' }
            },
          ]
        },
        {
          path: 'freightSet',
          component: () => import('@/views/systemSetting/deliverGoods/freightSet'),
          name: 'freightSet',
          meta: { title: '运费模板', noCache: true }
        },
      ]
    },
  ]
}

export default operationRouter  //collate
