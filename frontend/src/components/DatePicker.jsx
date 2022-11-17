import React, { useRef } from 'react';
import PropTypes from 'prop-types';
import Input from './Input';
import moment from 'moment';

const DatePicker = ({ field, form }) => {
  const dayRef = useRef(1);
  const monthRef = useRef('');
  const yearRef = useRef('');

  const clamp = (num, min, max) => Math.min(Math.max(num, min), max);

  const limitInput = (e) => {
    const target = e.target;
    const max = target.max;
    const min = target.min;
    const nextVal = +(target.value + e.key);

    // Ignore if not number
    if (e.key === 'e' || e.key === '+' || e.key === '-' || e.key === '.' || e.key === ',')
      e.preventDefault();

    if (isNaN(e.key)) return;

    e.preventDefault();
    target.value = clamp(nextVal, min, max);
  };

  const autoFocus = (e) => {
    const target = e.target;
    const inputLength = target.value.length;
    const current = target.getAttribute('data-id');

    if (e.keyCode == 9) return;
    if (inputLength >= target.max.toString().length) {
      if (current === 'day') monthRef.current.focus();
      else if (current === 'month') yearRef.current.focus();
      else if (current === 'year') target.blur();
    } else if (inputLength === 0) {
      if (current === 'month') dayRef.current.focus();
      else if (current === 'year') monthRef.current.focus();
    }
  };

  const updateDateValue = () => {
    const day = dayRef.current.value;
    const month = monthRef.current.value;
    const year = yearRef.current.value;
    let momentDate = '';

    if (day && month && year) {
      form.setFieldTouched(field.name, true);

      const date = moment(`${year}-${month}-${day}`, 'YYYY-MM-DD');
      if (date.isValid()) {
        momentDate = date.utc().format();
      }
    }
    form.setFieldValue(field.name, momentDate, true);
  };

  return (
    <div className="date-picker">
      <div className="input-row">
        <Input
          label="Jour"
          type="number"
          min={0}
          max={31}
          ref={dayRef}
          onKeyUp={autoFocus}
          onKeyDown={limitInput}
          onChange={updateDateValue}
          data-id="day"
          autoComplete="bday-day"
        />
        <Input
          label="Mois"
          type="number"
          min={0}
          max={12}
          ref={monthRef}
          onKeyUp={autoFocus}
          onKeyDown={limitInput}
          onChange={updateDateValue}
          data-id="month"
          autoComplete="bday-month"
        />
        <Input
          label="Année"
          type="number"
          min={0}
          max={new Date().getFullYear()}
          ref={yearRef}
          onKeyUp={autoFocus}
          onKeyDown={limitInput}
          onChange={updateDateValue}
          data-id="year"
          autoComplete="bday-year"
        />
      </div>
      {form.touched[field.name] && form.errors[field.name] && (
        <div className="error">{form.errors[field.name]}</div>
      )}
    </div>
  );
};

DatePicker.propTypes = {};

export default DatePicker;
