import PropTypes from 'prop-types';

const PopupError = () => {
  return alert('Loading');
};
PopupError.PropTypes = {
  message: PropTypes.string.isRequired,
};

export default PopupError;
