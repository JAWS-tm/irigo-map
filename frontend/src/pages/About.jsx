import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const About = (props) => {
  return (
    <div>
      <h2>About Pages</h2>
      <Link to="/Home">Home</Link>
      <Link to="/map">Map Page</Link>
      <Link to="/users">User</Link>
    </div>
  );
};

About.propTypes = {};

export default About;
