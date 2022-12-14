import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../../components/UnderlinedTitle';
import Button from '../../components/Button';
import { useNavigate } from 'react-router-dom';

const AdminPanel = (props) => {
  const navigate = useNavigate();
  return (
    <div
      style={{ marginTop: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}
    >
      <UnderlinedTitle>Panel d'administration</UnderlinedTitle>

      <p style={{ textAlign: 'center', marginBottom: '20px', maxWidth: '60%' }}>
        Bienvenue sur le panel d'administration, vous pouvez accéder ici à tous les outils de
        gestion disponibles.
      </p>

      <div style={{ display: 'flex', justifyContent: 'center', gap: '20px' }}>
        <Button text="Utilisateurs" onClick={() => navigate('/admin/users')} />
        <Button text="Demandes Data Scientist" onClick={() => navigate('/admin/grade-requests')} />
      </div>
    </div>
  );
};

AdminPanel.propTypes = {};

export default AdminPanel;
