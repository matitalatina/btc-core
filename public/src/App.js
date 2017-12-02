import 'whatwg-fetch'
import 'react-select/dist/react-select.css'
import React from 'react'
import { ThemeProvider } from 'styled-components'
import HomePage from './components/pages/HomePage'
import theme from './components/themes/default'
import './App.css'

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <HomePage />
    </ThemeProvider>
  )
}


export default App
