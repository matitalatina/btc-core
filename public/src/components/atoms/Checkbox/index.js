import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

const Label = styled.label`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  text-transform: uppercase;
`
const WrapperLabel = styled.span`
  margin-bottom: 0.2rem;
`
const handleChange = (onCheckChange, isChecked) => {
  return () => onCheckChange(!isChecked)
}

const Checkbox = ({ onCheckChange, isChecked, label }) => {
  return (
    <Label>
      <WrapperLabel>{label}</WrapperLabel>
      <input
        type="checkbox"
        checked={isChecked}
        onChange={handleChange(onCheckChange, isChecked)}
      />
    </Label>
  )
}

Checkbox.propTypes = {
  onCheckChange: PropTypes.func.isRequired,
  isChecked: PropTypes.bool.isRequired,
  label: PropTypes.string,
}

export default Checkbox
