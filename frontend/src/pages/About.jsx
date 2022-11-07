import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const About = (props) => {
  return (
    <div>
      <h2>About Pages</h2>
      <p>PGL default project structure</p>
      <Link to="/">Home</Link>
    </div>
  );
};

About.propTypes = {};

export default About;
