import iHeaderI18n from '@/layouts/basic-layout/header-i18n'
import { mapState } from 'vuex'

export default {
  components: { iHeaderI18n },
  computed: {
    ...mapState('/system/layout', [
      'showI18n'
    ])
  }
}
