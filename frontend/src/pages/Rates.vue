<script>
import HttpStatus from '../helpers/HttpStatus'
import MoneyFilter from '../mixins/MoneyFilter'

export default {
  mixins: [ MoneyFilter ],
  data: () => ({
    loading: false,
    isInserting: false,
    filter: '',
    newItem: { origin: {}, destiny: {} },
    resultList: [],
    ddds: []
  }),
  async mounted () {
    this.ddds = (await this.$axios.get('/api/dDDs/no-link')).data.map(ddd => ({label: ddd.code, value: ddd.id}))
    this._search = async () => {
      this.loading = true
      try {
        this.resultList = (await this.$axios.get('/api/rates')).data
      } catch (err) {
        console.error({ err })
      } finally {
        this.loading = false
      }
    }
    this._search()
  },
  methods: {
    async create () {
      try {
        await this.$axios.post('/api/rates', this.newItem)
        this._search()
        this.isInserting = false
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

    async update (item) {
      try {
        await this.$axios.put('/api/rates', item)
        await this._search()
      } catch (err) {
        console.error({ err })
      }
    },

    async remove (id) {
      try {
        await this.$q.dialog({
          title: 'Atenção',
          message: 'Deseja realmente excluir?',
          ok: 'Sim',
          cancel: 'Não'
        })
        try {
          await this.$axios.delete(`/api/rates/${id}`)
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
  <q-page padding>
    <q-table
      title="Tarifas"
      :data="resultList"
      :columns="[
        {
          name: 'origin',
          label: 'Origem',
          align: 'left',
          sortable: true,
          style: 'width: 30%'
        },
        {
          name: 'destiny',
          label: 'Destino',
          align: 'left',
          sortable: true,
          style: 'width: 30%'
        },
        {
          name: 'value',
          label: 'Valor (R$)',
          align: 'left',
          sortable: true,
          style: 'width: 30%'
        }
      ]"
      :filter="filter"
      no-data-label="Nenhuma tarifa cadastrada."
      no-results-label="Nenhuma tarifa encontrada"
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
        <q-btn
          round
          color="primary"
          icon="add"
          @click="isInserting = true"
        >
          <q-modal
            v-model="isInserting"
            content-classes="q-pa-lg"
            @hide="isInserting = false"
          >
            <div class="q-display-1 q-mb-md">Nova Tarifa</div>
            <q-select
              v-model="newItem.origin.id"
              float-label="Origem"
              :options="ddds"
              clearable
              class="q-ma-none full-width"
            />
            <q-select
              v-model="newItem.destiny.id"
              float-label="Destino"
              :options="ddds"
              clearable
              class="q-ma-none full-width"
            />
            <q-input
              type="number"
              prefix="R$"
              float-label="Valor"
              v-model="newItem.value"
              clearable
              class="q-ma-none full-width"
            />
            <q-btn
              color="primary"
              @click="create()"
              label="Inserir"
              class="q-mt-lg full-width"
            />
          </q-modal>
        </q-btn>
      </template>

      <q-tr slot="body" slot-scope="props" :props="props">
        <q-td>
          <span class="q-ma-lg">
            <q-chip lg color="primary">
              {{ props.row.origin.code }}
              <q-popup-edit v-model="props.row.origin.id">
                <q-select
                  @input="update(props.row)"
                  v-model="props.row.origin.id"
                  :options="ddds"
                />
              </q-popup-edit>
            </q-chip>
          </span>
        </q-td>
        <q-td>
          <span class="q-ma-lg">
            <q-chip lg color="primary">
              {{ props.row.destiny.code }}
              <q-popup-edit v-model="props.row.destiny.id">
                <q-select
                  @input="update(props.row)"
                  v-model="props.row.destiny.id"
                  :options="ddds"
                />
              </q-popup-edit>
            </q-chip>
          </span>
        </q-td>
        <q-td>
          <span class="q-ma-lg">
            <q-chip lg color="primary">
              {{ props.row.value | money }}
              <q-popup-edit v-model="props.row.value">
                <q-input
                  prefix="R$"
                  v-model="props.row.value"
                  type="number"
                  @input="update(props.row)"
                />
              </q-popup-edit>
            </q-chip>
          </span>
        </q-td>
        <q-td align="right">
          <q-btn
            round
            color="primary"
            icon="delete"
            @click="remove(props.row.id)"
          />
        </q-td>
      </q-tr>
    </q-table>
  </q-page>
</template>
