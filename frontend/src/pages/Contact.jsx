import React from 'react';
import PropTypes from 'prop-types';
import Input from '../components/Input';
import TextArea from '../components/TextArea';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import PopupError from '../components/PopupError';

const Contact = (props) => {
  return (
    <div className="Contact">
      <UnderlinedTitle>Contactez-nous</UnderlinedTitle>
      <form action="#">
        <div className="row">
          <Input label="Nom" className="input" />
          <Input label="Email" className="input" />
        </div>
        <div>
          <TextArea label="Message" className="message-input" />
        </div>
        <div className="btn-wrapper">
          <Button text="Envoyer" onClick={PopupError} />
        </div>
      </form>
    </div>
  );
};

Contact.propTypes = {};

export default Contact;
