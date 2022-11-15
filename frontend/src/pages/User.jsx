import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const User = (props) => {
  return (
    <div>
      <h2>Users list</h2>
      <Link to="/Home">Home</Link>
      <Link to="/map">Map Page</Link>
      <Link to="/about">About project</Link>
    </div>
  );
};

User.propTypes = {};

export default User;
