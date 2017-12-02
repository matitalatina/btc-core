import styled from 'styled-components'
import { font, palette } from 'styled-theme'

const Select = styled.select`
  font-family: ${font('primary')};
  color: ${palette('foreground', 0)};
`

Select.propTypes = {}

export default Select
