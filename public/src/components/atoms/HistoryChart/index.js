import React from 'react'
import PropTypes from 'prop-types'
import ReactHighstock from 'react-highcharts/ReactHighstock'

const HistoryChart = ({ history, currency }) => {
  const data = history.map(h => [h.stamp, h.rate])
  const config = {
    rangeSelector: {
      selected: 1,
    },
    title: {
      text: currency.name,
    },
    series: [{
      name: currency.code,
      data,
      tooltip: {
        valueDecimals: 2,
      },
    }],
  }
  return (
    <ReactHighstock config={config} />
  )
}

HistoryChart.propTypes = {
  history: PropTypes.array,
  currency: PropTypes.object,
}

export default HistoryChart
