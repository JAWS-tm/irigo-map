import React, { useEffect, useState } from 'react';
import { Link, NavLink, useLocation } from 'react-router-dom';
import classNames from 'classnames';
import Logo from './Logo';
import { useAuth } from '../hooks/auth';
import { useSelector } from 'react-redux';
import { selectUserRole } from '../store/slices/authSlice';
import { UserRoles } from '../constants';
import MultipleLink from '../components/MultipleLink';
import Hamburger from 'hamburger-react';

const linksList = [
  { name: 'Accueil', to: '/', props: { end: true } },
  { name: 'Aide', to: '/help' },
];

function Navbar(props) {
  const [stickyClass, setStickyClass] = useState('');
  const [toggleNav, setToggleNav] = useState(false);

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

  let location = useLocation();

  useEffect(() => {
    // close on page change
    setToggleNav(false);
  }, [location]);

  const isAuth = useAuth();
  const isAdmin = useSelector(selectUserRole) === UserRoles.ADMIN;

  return (
    <nav className={classNames('Navbar', stickyClass)}>
      <div>
        <Logo />
      </div>
      <div className="burger-toggle">
        <Hamburger toggled={toggleNav} onToggle={() => setToggleNav(!toggleNav)} />
      </div>
      <div className={'links-list ' + (toggleNav ? 'opened' : '')}>
        {linksList.map((link, i) => (
          <NavLink to={link.to} key={i} {...link.props}>
            {link.name}
          </NavLink>
        ))}
        <div className="separator"></div>
        {isAuth ? (
          <>
            {isAdmin && (
              <MultipleLink
                to={'/admin'}
                links={[
                  { name: 'Utilisateurs', to: '/admin/users' },
                  { name: 'Demandes de promotion', to: '/admin/data-scientists' },
                ]}
              >
                Administration
              </MultipleLink>
            )}
            <NavLink to={'/about'}>Outils</NavLink>
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
