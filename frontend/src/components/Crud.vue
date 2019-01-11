<script>
import HttpStatus from '../helpers/HttpStatus'

export default {
  props: [
    'endpoint',
    'title',
    'createTitle',
    'updateTitle',
    'noDataFoundMessage',
    'noDataCreated',
    'field'
  ],
  data: () => ({
    loading: false,
    filter: '',
    newItem: '',
    resultList: []
  }),
  async mounted () {
    this._initialize = () => {
      this.newItem = ''
    }
    this._search = async () => {
      this.loading = true
      try {
        const { data } = await this.$axios.get(`/api/${this.endpoint}s`)
        this.resultList = data._embedded[`${this.endpoint}s`]
      } catch (err) {
        console.error({ err })
      } finally {
        this.loading = false
      }
    }
    this._initialize()
    this._search()
  },
  methods: {
    async create () {
      try {
        const params = {}
        params[this.field.value] = this.newItem
        await this.$axios.post(`/api/${this.endpoint}s`, params)
        this._initialize()
        this._search()
      } catch (err) {
        if (err.response.status === HttpStatus.CONFLIT) {
          this.$q.notify({
            message: 'Já existe. Tente outro valor.',
            type: 'negative',
            icon: 'error',
            position: 'top'
          })
        }
        console.error({ err })
      }
    },
    async update (item, href) {
      try {
        await this.$axios.put(href, item)
      } catch (err) {
        console.error({ err })
      }
    },
    async remove (href) {
      try {
        await this.$q.dialog({
          title: 'Atenção',
          message: 'Deseja realmente excluir?',
          ok: 'Sim',
          cancel: 'Não'
        })
        try {
          await this.$axios.delete(href)
        } catch (e) {
          if (e.response.status === HttpStatus.CONFLIT) {
            this.$q.notify({
              message: 'Não pode excluir, pois está em uso.',
              type: 'negative',
              icon: 'error',
              position: 'top'
            })
          }
          console.error({ e })
        }
        this._search()
      } catch (err) {
        // Fechou o dialog
      }
    }
  }
}
</script>

<template>
  <q-table
    :title="title"
    :data="resultList"
    :columns="[{
      name: field.value,
      label: field.name,
      align: 'left',
      sortable: true,
      style: 'width: 90%'
    }]"
    :filter="filter"
    :no-data-label="noDataCreated"
    :no-results-label="noDataFoundMessage"
    row-key="id"
    :loading="loading"
    loading-label="Carregando"
  >
    <template slot="top-right" slot-scope="props">
      <q-search
        clearable
        color="secondary"
        class="q-mr-lg"
        placeholder="Procurar"
        v-model="filter"
      />
      <q-btn round color="primary" icon="add">
        <q-popup-edit
          v-model="newItem"
          :title="createTitle"
          label-set="Inserir"
          label-cancel="Cancelar"
          buttons
          @save="create()"
        >
          <q-field
            :label="field.name"
            orientation="vertical"
          >
            <q-input v-model="newItem"/>
          </q-field>
        </q-popup-edit>
      </q-btn>
    </template>

    <q-tr slot="body" slot-scope="props" :props="props">
      <q-td>
        <span class="q-ma-lg">
          <q-chip lg color="primary">{{ props.row[field.value] }}</q-chip>
          <q-popup-edit
            v-model="props.row[field.value]"
            :title="updateTitle"
            label-set="Atualizar"
            label-cancel="Cancelar"
            buttons
            @save="update(props.row, props.row._links[endpoint].href)"
          >
            <q-input v-model="props.row[field.value]"/>
          </q-popup-edit>
        </span>
      </q-td>
      <q-td align="right">
        <q-btn
          round
          color="primary"
          icon="delete"
          @click="remove(props.row._links[endpoint].href)"
        />
      </q-td>
    </q-tr>
  </q-table>
</template>
