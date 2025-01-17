

import defaultSettings from '@/settings'
import Cache from '@/plugins/cache'
const { showSettings, tagsView, fixedHeader, sidebarLogo,topNav,sideTheme,navIcon } = defaultSettings
const storageSetting = Cache.local.has('layout-setting') ? Cache.local.getJSON('layout-setting') : '';
const state = {
  theme: storageSetting.theme || '#409EFF',
  sideTheme:storageSetting.sideTheme || sideTheme,
  topNav:storageSetting.topNav === undefined ? topNav : storageSetting.topNav,
  showSettings: showSettings,
  tagsView: storageSetting.tagsView === undefined ? tagsView : storageSetting.tagsView,
  fixedHeader: storageSetting.fixedHeader === undefined ? fixedHeader : storageSetting.fixedHeader,
  sidebarLogo: storageSetting.sidebarLogo === undefined ? sidebarLogo : storageSetting.sidebarLogo,
  navIcon: storageSetting.navIcon === undefined ? navIcon : storageSetting.navIcon
}
const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

