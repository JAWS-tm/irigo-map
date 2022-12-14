import React, { useRef } from 'react';
import PropTypes from 'prop-types';
import Input from './Input';
import moment from 'moment';
import { useState } from 'react';

const DatePicker = ({ field, form }) => {
  const dayRef = useRef('');
  const monthRef = useRef('');
  const yearRef = useRef('');
  const [lastValues, setLastValues] = useState({
    day: null,
    month: null,
    year: null,
  });

  // const clamp = (num, min, max) => Math.min(Math.max(num, min), max);

  const validateInput = (e) => {
    var event = e || window.event;

    var key = event.key;
    let keyCode = event.keyCode || e.which;

    var regex = /[0-9]/;
    if (
      !regex.test(key) &&
      keyCode !== 8 &&
      keyCode !== 9 &&
      keyCode !== 13 &&
      keyCode !== 46 &&
      keyCode !== 37 &&
      keyCode !== 39
    ) {
      event.returnValue = false;
      if (event.preventDefault) event.preventDefault();
    } else {
      // sauvegarde des anciennes valeurs
      setLastValues({
        day: dayRef.current.value,
        month: monthRef.current.value,
        year: yearRef.current.value,
      });
    }
  };

  const autoFocus = (e) => {
    const target = e.target;
    const inputLength = target.value.length;
    const current = target.getAttribute('data-id');

    if (e.keyCode == 9) return;
    // pas de switch si pas de changement
    if (lastValues[current] === target.value) return;

    if (inputLength >= target.maxLength) {
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
          type="text"
          min={0}
          max={31}
          maxLength={2}
          ref={dayRef}
          onKeyUp={autoFocus}
          onKeyDown={validateInput}
          onChange={updateDateValue}
          data-id="day"
          autoComplete="bday-day"
        />
        <Input
          label="Mois"
          type="text"
          min={0}
          max={12}
          maxLength={2}
          ref={monthRef}
          onKeyUp={autoFocus}
          onKeyDown={validateInput}
          onChange={updateDateValue}
          data-id="month"
          autoComplete="bday-month"
        />
        <Input
          label="Année"
          type="text"
          min={0}
          max={new Date().getFullYear()}
          maxLength={4}
          ref={yearRef}
          onKeyUp={autoFocus}
          onKeyDown={validateInput}
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
