import React from 'react';
import PropTypes from 'prop-types';
import { Outlet } from 'react-router-dom';

const ContentLayout = (props) => {
  return (
    <div className="content-layout">
      <Outlet />
    </div>
  );
};

ContentLayout.propTypes = {};

export default ContentLayout;
