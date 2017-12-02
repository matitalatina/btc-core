import PropTypes from 'prop-types'
import styled from 'styled-components'
import { font, palette } from 'styled-theme'

const Small = styled.small`
  font-family: ${font('primary')};
  color: ${props => props.reverse ? palette('background', 0) : palette('foreground', 0)};
  opacity: 0.5;
  text-align: ${props => props.centered ? 'center' : 'auto'};
  font-size: 0.6rem;
`

Small.propTypes = {
  reverse: PropTypes.bool,
  centered: PropTypes.bool,
}

export default Small
