import React, { useEffect, useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import classNames from 'classnames';
import Logo from './Logo';
import { useAuth } from '../hooks/auth';

const linksList = [
  { name: 'Accueil', to: '/', props: { end: true } },
  { name: 'Contact', to: '/contact' },
  { name: 'À propos', to: '/about' },
];

function Navbar(props) {
  const [stickyClass, setStickyClass] = useState('');

  useEffect(() => {
    window.addEventListener('scroll', stickNavbar);

    return () => {
      window.removeEventListener('scroll', stickNavbar);
    };
  }, []);

  const stickNavbar = () => {
    if (window !== undefined) {
      window.scrollY > 25 ? setStickyClass('sticky') : setStickyClass('');
    }
  };

  const isAuth = useAuth();

  return (
    <nav className={classNames('Navbar', stickyClass)}>
      <div>
        <Logo />
      </div>
      <div className="links-list">
        {linksList.map((link, i) => (
          <NavLink to={link.to} key={i} {...link.props}>
            {link.name}
          </NavLink>
        ))}
        <div className="separator"></div>
        {isAuth ? (
          <>
            <NavLink to={'/map'}>Carte</NavLink>
            <NavLink to={'/logout'}>Déconnexion</NavLink>
          </>
        ) : (
          <>
            <NavLink to={'/sign-in'}>Connexion</NavLink>
            <NavLink className="signup" to={'/sign-up'}>
              Inscription
            </NavLink>
          </>
        )}
      </div>
    </nav>
  );
}

Navbar.propTypes = {};

export default Navbar;
