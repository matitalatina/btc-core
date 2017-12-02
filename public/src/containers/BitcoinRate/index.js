import React, { Component } from 'react'
import BitcoinRate from '../../components/molecules/BitcoinRate'
import { HTTP_CORE_ENDPOINT } from '../../config'
import RateUpdateListener from '../../services/rateUpdateListener'

class BitcoinRateContainer extends Component {
  constructor(props) {
    super(props)
    this.changeRate = this.changeRate.bind(this)
    this.onUpdateRateStats = this.onUpdateRateStats.bind(this)
    this.changeHistoryVisibility = this.changeHistoryVisibility.bind(this)
    this.state = {
      options: {
        historyVisibility: false,
      },
      availableRates: [],
      history: [],
      stats: {},
      currency: {},
    }
    this.listener = new RateUpdateListener({ onUpdate: this.onUpdateRateStats })
  }

  componentDidMount() {
    return fetch(`${HTTP_CORE_ENDPOINT}/bitcoins/currencies/`)
      .then(response => response.json())
      .then((availableRates) => {
        this.setState({ ...this.state, availableRates })
        return availableRates
      })
  }

  onUpdateRateStats(rateStats) {
    this.setState({ ...this.state, history: [...this.state.history, rateStats.rate], stats: rateStats.stats })
  }

  changeHistoryVisibility(historyVisibility) {
    this.setState({ ...this.state, options: { ...this.state.options, historyVisibility } })
    if (historyVisibility) {
      return this.fetchHistory(this.state.currency.code)
    }
    return Promise.resolve(null)
  }

  changeRate(currencyCode) {
    this.listener.listen(currencyCode)
    const currency = this.state.availableRates.filter(r => r.code === currencyCode)[0]
    this.setState({ ...this.state, currency })
    if (this.state.options.historyVisibility) {
      return this.fetchHistory(currencyCode)
    }
    return Promise.resolve(null)
  }

  fetchHistory(currencyCode) {
    return fetch(`${HTTP_CORE_ENDPOINT}/bitcoins/rates/${currencyCode}/history/`)
      .then(response => response.json())
      .then(history => this.setState({ ...this.state, history }))
  }

  render() {
    return (
      <BitcoinRate
        availableRates={this.state.availableRates}
        history={this.state.history}
        onChangeRate={this.changeRate}
        stats={this.state.stats}
        currency={this.state.currency}
        historyVisibility={this.state.options.historyVisibility}
        onHistoryVisibilityChange={this.changeHistoryVisibility}
      />
    )
  }
}

export default BitcoinRateContainer
