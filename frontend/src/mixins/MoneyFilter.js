const MoneyFilter = {
  filters: {
    money (value) {
      return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(value)
    }
  }
}

export default MoneyFilter
