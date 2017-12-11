import React from 'react'
import styled from 'styled-components'
import PropTypes from 'prop-types'

const Wrapper = styled.span``

const CurrencyFormatter = ({ value, currencyCode }) => {
  const formattedCurrency = value ? new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: currencyCode,
    minimumFractionDigits: 3,
  }).format(value) : '-'
  return (
    <Wrapper>{formattedCurrency}</Wrapper>
  )
}

CurrencyFormatter.propTypes = {
  value: PropTypes.number,
  currencyCode: PropTypes.string,
}

export default CurrencyFormatter
