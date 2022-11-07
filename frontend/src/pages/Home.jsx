import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

const Home = (props) => {
  return (
    <div>
      <h2>Home page</h2>
      <Link to="/about">About project</Link>
    </div>
  );
};

Home.propTypes = {};

export default Home;
