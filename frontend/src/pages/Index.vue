
<script>
import MoneyFilter from '../mixins/MoneyFilter'

const getIdOf = (href) => href.substring(href.lastIndexOf('/') + 1)

export default {
  mixins: [ MoneyFilter ],
  data: () => ({
    ddds: [],
    plans: [],
    origin: null,
    destiny: null,
    timeInMinutes: null,
    plan: null,
    withPlan: 0,
    withoutPlan: 0
  }),
  asyncComputed: {
    async result () {
      if (this.timeInMinutes) {
        const originId = getIdOf(this.origin)
        const destinyId = getIdOf(this.destiny)
        const planId = getIdOf(this.plan)
        return (await this.$axios.get(`/api/calculator/${originId}/${destinyId}/${planId}/${this.timeInMinutes}`)).data
      }
      return null
    }
  },
  async mounted () {
    this.ddds = (await this.$axios.get('/api/dDDs')).data._embedded.dDDs.map(ddd => ({label: `${ddd.code}`, value: ddd._links.dDD.href}))
    this.plans = (await this.$axios.get('/api/plans')).data._embedded.plans.map(plan => ({label: `FaleMais ${plan.minutes}`, value: plan._links.plan.href}))

    this.origin = this.ddds[0].value
    this.destiny = this.ddds[0].value
    this.plan = this.plans[0].value
  }
}
</script>

<template>
  <q-page padding class="column justify">
    <q-list highlight>
      <q-list-header>Calcule o valor da sua ligação.</q-list-header>
      <q-item>
        <q-item-side icon="phone_forwarded" />
        <q-item-main>
          <q-select
            hide-underline
            class="q-ma-none full-width"
            float-label="Origem (DDD)"
            v-model="origin"
            :options="ddds"
          />
        </q-item-main>
      </q-item>
      <q-item>
        <q-item-side icon="phone_callback" />
        <q-item-main>
          <q-select
            hide-underline
            class="q-ma-none full-width"
            float-label="Destino (DDD)"
            v-model="destiny"
            :options="ddds"
          />
        </q-item-main>
      </q-item>
      <q-item>
        <q-item-side icon="phone" />
        <q-item-main>
          <q-select
            hide-underline
            class="q-ma-none full-width"
            float-label="Plano FaleMais"
            v-model="plan"
            :options="plans"
          />
        </q-item-main>
      </q-item>
      <q-item>
        <q-item-side icon="access_time" />
        <q-item-main>
          <q-input
            class="q-ma-none full-width"
            type="number"
            suffix="minuto(s)"
            float-label="Tempo da ligação"
            v-model="timeInMinutes"
            clearable
          />
        </q-item-main>
      </q-item>
    </q-list>
    <q-list highlight>
      <q-list-header>Resultado</q-list-header>
      <q-item>
        <q-item-side icon="mood" />
        <q-item-main>
          <q-item-tile label>Com Plano FaleMais</q-item-tile>
          <q-item-tile sublabel v-if="result">{{result.withPlan | money}}</q-item-tile>
          <q-item-tile sublabel v-else>R$ -</q-item-tile>
        </q-item-main>
      </q-item>
      <q-item>
        <q-item-side icon="mood_bad" />
        <q-item-main>
          <q-item-tile label>Sem Plano FaleMais</q-item-tile>
          <q-item-tile sublabel v-if="result">{{result.withoutPlan | money}}</q-item-tile>
          <q-item-tile sublabel v-else>R$ -</q-item-tile>
        </q-item-main>
      </q-item>
    </q-list>
  </q-page>
</template>
