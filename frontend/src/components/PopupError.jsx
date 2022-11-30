import PropTypes from 'prop-types';

const PopupError = () => {
  return alert('En cours de création');
};
PopupError.PropTypes = {
  message: PropTypes.string.isRequired,
};

export default PopupError;
