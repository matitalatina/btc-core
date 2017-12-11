import React from 'react'
import PropTypes from 'prop-types'
import Select from 'react-select'
import styled from 'styled-components'
import HistoryChart from '../../atoms/HistoryChart'
import Checkbox from '../../atoms/Checkbox'
import Stripe from '../../atoms/Stripe'
import LabelValue from '../../atoms/LabelValue'
import CurrencyFormatter from '../../atoms/CurrencyFormatter'

const Wrapper = styled.div``
const WrapperSpaced = styled.div`
  margin: 1rem 0;
`
const BitcoinRate = ({ availableRates, history, stats, onChangeRate, currency, historyVisibility, onHistoryVisibilityChange }) => {
  const options = availableRates.map(r => ({ label: r.name, value: r.code }))
  const changeRate = val => onChangeRate(val.value)
  let historySection = null
  if (historyVisibility) {
    historySection = (
      <WrapperSpaced>
        <HistoryChart history={history} currency={currency} />
      </WrapperSpaced>
    )
  }
  const selectedCurrency = {
    value: currency.code,
    label: currency.name,
  }
  console.log(currency.code)
  const formattedCurrency = value => <CurrencyFormatter currencyCode={currency.code} value={value} />

  return (
    <Wrapper>
      <Stripe inverse>
        <Select
          options={options}
          onChange={changeRate}
          value={selectedCurrency}
          style={{ minWidth: '200px' }}
        />
        <Checkbox
          label="History"
          isChecked={historyVisibility}
          onCheckChange={onHistoryVisibilityChange}
        />
        <LabelValue label="Min" value={formattedCurrency(stats.min)} />
        <LabelValue label="Max" value={formattedCurrency(stats.max)} />
        <LabelValue label="Avg" value={formattedCurrency(stats.avg)} />
        <LabelValue label="Variance" value={formattedCurrency(stats.variance)} />
      </Stripe>
      {historySection}
    </Wrapper>
  )
}

BitcoinRate.propTypes = {
  availableRates: PropTypes.arrayOf(PropTypes.object),
  history: PropTypes.arrayOf(PropTypes.object),
  currency: PropTypes.object.isRequired,
  stats: PropTypes.object,
  onChangeRate: PropTypes.func.isRequired,
  historyVisibility: PropTypes.bool.isRequired,
  onHistoryVisibilityChange: PropTypes.func.isRequired,
}

export default BitcoinRate
