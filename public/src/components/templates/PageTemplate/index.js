// https://github.com/diegohaz/arc/wiki/Atomic-Design#templates
import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'
import { palette } from 'styled-theme'
import Content from '../../atoms/Content'

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  box-sizing: border-box;
  background-color: ${palette('background', 0)};
  color: ${palette('foreground', 0)};
`

const Header = styled.header``

const FilledContent = Content.extend`
  flex: 1;
`
const ContentCentered = FilledContent.extend`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
`

const Footer = styled.footer`
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
`

const PageTemplate = ({
  header, hero, sponsor, children, footer, contentCentered = false, ...props
}) => {
  return (
    <Wrapper {...props}>
      {header && <Header>{header}</Header>}
      {contentCentered ? <ContentCentered>{children}</ContentCentered> : <FilledContent>{children}</FilledContent>}
      {footer && <Footer>{footer}</Footer>}
    </Wrapper>
  )
}

PageTemplate.propTypes = {
  header: PropTypes.node,
  hero: PropTypes.node,
  sponsor: PropTypes.node,
  footer: PropTypes.node,
  children: PropTypes.any.isRequired,
  contentCentered: PropTypes.bool,
}

export default PageTemplate
