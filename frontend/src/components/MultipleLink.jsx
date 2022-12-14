import React from 'react';
import PropTypes from 'prop-types';
import { Link, NavLink } from 'react-router-dom';

const MultipleLink = ({ links, to, children }) => {
  return (
    <div className="MultipleLink">
      <NavLink to={to}>
        {children} <i className="fa-solid fa-chevron-down"></i>
      </NavLink>
      <div className="link-menu">
        {links &&
          links.map((link) => (
            <NavLink to={link.to} key={link.name}>
              {link.name}
            </NavLink>
          ))}
      </div>
    </div>
  );
};

MultipleLink.propTypes = {
  to: PropTypes.string,
  children: PropTypes.string.isRequired,
  links: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string.isRequired,
      to: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default MultipleLink;
