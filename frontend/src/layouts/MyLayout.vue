<script>
import { openURL } from 'quasar'

export default {
  name: 'MyLayout',
  data () {
    return {
      leftDrawerOpen: this.$q.platform.is.desktop
    }
  },
  methods: {
    openURL
  }
}
</script>

<template>
  <q-layout view="lHh Lpr lFf">
    <q-layout-header>
      <q-toolbar
        color="primary"
        :glossy="$q.theme === 'mat'"
        :inverted="$q.theme === 'ios'"
      >
        <q-btn flat round dense icon="home" to="/" size="lg" />
        <q-toolbar-title>
          <div class="row">
            <q-icon name="phone" class="q-pr-sm" />
            <div>FaleMais</div>
          </div>
        </q-toolbar-title>
        <q-page-sticky position="bottom-left" :offset="[10, 10]">
          <q-fab
            icon="settings"
            direction="up"
            color="primary"
          >
            <q-fab-action color="blue" class="white" icon="home" @click="$router.push('/')">
              <q-tooltip anchor="center left" self="center right" :offset="[20, 0]">Home</q-tooltip>
            </q-fab-action>
            <q-fab-action color="blue" class="white" icon="attach_money" @click="$router.push('/tarifas')">
              <q-tooltip anchor="center left" self="center right" :offset="[20, 0]">Tarifas</q-tooltip>
            </q-fab-action>
            <q-fab-action color="blue" class="white" icon="phone" @click="$router.push('/planos')">
              <q-tooltip anchor="center left" self="center right" :offset="[20, 0]">Planos FaleMais</q-tooltip>
            </q-fab-action>
            <q-fab-action color="blue" class="white" icon="phone_forwarded" @click="$router.push('/ddds')">
              <q-tooltip anchor="center left" self="center right" :offset="[20, 0]">DDDs</q-tooltip>
            </q-fab-action>
          </q-fab>
        </q-page-sticky>
      </q-toolbar>
    </q-layout-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>
