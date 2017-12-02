import React from 'react'
import styled from 'styled-components'
import PropTypes from 'prop-types'

const Wrapper = styled.div`
  text-align: center;
`

const Label = styled.div`
  text-transform: uppercase;
  margin-bottom: 0.2rem;
`

const Value = styled.div`
  font-weight: 200;
`

const ValueLabel = ({ value, label }) => (
  <Wrapper>
    <Label>{label}</Label>
    <Value>{value || '-'}</Value>
  </Wrapper>
)

ValueLabel.propTypes = {
  inverse: PropTypes.bool,
  value: PropTypes.any,
  label: PropTypes.string.isRequired,
}

export default ValueLabel
