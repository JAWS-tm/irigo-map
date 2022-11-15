import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import '../assets/styles/Home.css';

const Home = (props) => {
  return (
    <div className="Head">
      <h2 className="title">Home page</h2>
      <Link to="/map">Map Page</Link>
      <Link to="/about">About project</Link>
      <Link to="/users">User</Link>
    </div>
  );
};

Home.propTypes = {};

export default Home;
