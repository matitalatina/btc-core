import React from 'react'
import PropTypes from 'prop-types'
import Select from 'react-select'
import styled from 'styled-components'
import HistoryChart from '../../atoms/HistoryChart'
import Checkbox from '../../atoms/Checkbox'
import Stripe from '../../atoms/Stripe'
import LabelValue from '../../atoms/LabelValue'

const Wrapper = styled.div``
const WrapperSpaced = styled.div`
  margin: 1rem 0;
`
const BitcoinRate = ({ availableRates, history, stats, onChangeRate, currency, historyVisibility, onHistoryVisibilityChange }) => {
  const options = availableRates.map(r => ({ label: r.name, value: r.code }))
  const changeRate = val => onChangeRate(val.value)
  let historySection = null
  if (historyVisibility) {
    const historyRender = history.map((h, i) => (
      <li key={i}>{h.rate} - {new Date(h.stamp).toISOString()}</li>
    ))
    historySection = (
      <WrapperSpaced>
        <HistoryChart history={history} currency={currency} />
        {historyRender}
      </WrapperSpaced>
    )
  }
  const selectedCurrency = {
    value: currency.code,
    label: currency.name,
  }

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
        <LabelValue label="Min" value={stats.min} />
        <LabelValue label="Max" value={stats.max} />
        <LabelValue label="Avg" value={stats.avg} />
        <LabelValue label="Variance" value={stats.variance} />
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
