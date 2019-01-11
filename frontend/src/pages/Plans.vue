<script>
import Crud from '../components/Crud'

export default {
  components: { Crud }
}
</script>

<template>
  <q-page padding>
    <crud
      endpoint="plan"
      title="Planos"
      :field="{name: 'Minutos Grátis', value: 'minutes'}"
      create-title="Novo Plano"
      update-title="Editar Plano"
      no-data-found-message="Nenhum plano encontrado."
      no-data-created="Nenhum plano cadastrado."
    />
  </q-page>
</template>
