import React, { useState } from 'react';
import { useEffect } from 'react';
import { Form } from 'react-router-dom';

const StarNotation = (props) => {
  const [rate, setRate] = useState(0);

  useEffect(() => {
    setRate(props.field.value);
    console.log(props);
  }, []);
  return (
    <div
      style={{
        display: props.visible ? 'flex' : 'none',
        textAlign: 'center',
        alignItems: 'center',
        fontSize: '20px',
      }}
    >
      {[...Array(5)].map((item, index) => {
        const givenRating = index + 1;
        return (
          <label key={index}>
            <div
              style={{ cursor: props.blocked ? 'default' : 'pointer' }}
              onClick={() => {
                if (props.blocked) return;
                setRate(givenRating);
                props.form.setFieldValue(props.field.name, givenRating, true);
              }}
            >
              <i
                className="fa-solid fa-star"
                style={{
                  color: givenRating < rate || givenRating === rate ? 'gold' : 'rgb(160,160,160)',
                }}
              ></i>
            </div>
          </label>
        );
      })}
    </div>
  );
};

StarNotation.defaultProps = {
  visible: true,
};

export default StarNotation;
