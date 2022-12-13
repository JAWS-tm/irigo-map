import React from 'react';
import PropTypes from 'prop-types';
import ReactModal from 'react-modal';
import Button from './Button';

const ConfirmModal = ({
  visible,
  onClose,
  onValidate,
  validateText,
  validationLoading,
  importantValidation,
  children,
  title,
  showCancel,
}) => {
  return (
    <ReactModal
      isOpen={visible}
      onRequestClose={onClose}
      overlayClassName="modal-overlay"
      className="modal-content"
      ariaHideApp={false}
    >
      <div className="modal-header">
        <h2>{title}</h2>
        <i className="close-btn icon-button fa-solid fa-xmark" onClick={onClose}></i>
      </div>
      <div className="content">{children}</div>
      <div className="action-buttons">
        <Button
          text={validateText || 'Confirmer'}
          className={importantValidation ? 'important' : ''}
          onClick={onValidate}
          loading={validationLoading}
        />
        {showCancel && <Button text="Annuler" secondary={true} onClick={onClose} />}
      </div>
    </ReactModal>
  );
};

ConfirmModal.propTypes = {
  visible: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  onValidate: PropTypes.func.isRequired,
  validateText: PropTypes.string,
  validationLoading: PropTypes.bool,
  children: PropTypes.element,
  title: PropTypes.string,
  importantValidation: PropTypes.bool,
  showCancel: PropTypes.bool,
};

export default ConfirmModal;
