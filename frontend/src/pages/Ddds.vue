<script>
import Crud from '../components/Crud'

export default {
  components: { Crud }
}
</script>

<template>
  <q-page padding>
    <crud
      endpoint="dDD"
      :field="{name: 'Código', value: 'code'}"
      title="DDDs"
      create-title="Novo DDD"
      update-title="Editar DDD"
      no-data-found-message="Nenhum DDD encontrado."
      no-data-created="Nenhum DDD cadastrado."
    />
  </q-page>
</template>
