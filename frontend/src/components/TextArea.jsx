import classNames from 'classnames';
import React from 'react';

const TextArea = ({ label, className }) => {
  return (
    <div className={classNames('input-outlined', className)}>
      <textarea className="field" type="text" placeholder=" " />
      <span className="label">{label}</span>
    </div>
  );
};

TextArea.propTypes = {};

export default TextArea;
