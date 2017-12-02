import React from 'react'
import styled from 'styled-components'
import { font, palette } from 'styled-theme'
import { ifProp } from 'styled-tools'
import PropTypes from 'prop-types'

const Wrapper = styled.div`
  font-family: ${font('primary')};
  color: ${ifProp('inverse', palette('background', 0), palette('foreground', 0))};
  background-color: ${ifProp('inverse', palette('foreground', 0), palette('background', 0))};
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  align-content: center;
  justify-content: space-between;
  margin: auto -1000px;
  padding: 1rem 1000px;
  border-top: 1px solid ${palette('foreground', 0)};
  border-bottom: 1px solid ${palette('foreground', 0)};
`

const Stripe = ({ children, inverse }) => (
  <Wrapper inverse={inverse}>
    {children}
  </Wrapper>
)

Stripe.propTypes = {
  inverse: PropTypes.bool,
  children: PropTypes.any.isRequired,
}

export default Stripe
