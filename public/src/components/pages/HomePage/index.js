import React from 'react'
import PageTemplate from '../../templates/PageTemplate'
import Small from '../../atoms/Small'
import Link from '../../atoms/Link'
import BitcoinRateContainer from '../../../containers/BitcoinRate'
import Content from '../../atoms/Content'

const Logo = Content.extend`
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-size: 3rem;
  font-weight: 100;
`

const HomePage = () => {
  return (
    <PageTemplate
      header={
        <Logo>₿itcoin rates</Logo>
      }
      footer={
        <Small transparent centered>
          Made with <span role="img" aria-label="Heart" aria-labelledby="">❤️</span> by <Link href="https://www.mattianatali.it" target="_blank">Mattia Natali</Link> · <Link href="https://github.com/matitalatina/btc-core" target="_blank">Source Code</Link>
        </Small>
      }
    >
      <BitcoinRateContainer />
    </PageTemplate>
  )
}

export default HomePage
