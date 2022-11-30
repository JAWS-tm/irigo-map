import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';

const Help = (props) => {
  return (
    <div className="Help">
      <UnderlinedTitle className="title">Aide</UnderlinedTitle>
      <div id="Middle_title">
        <p>Vous trouverez ici des éléments d'aide pour naviger sur notre site</p>
        <br />
      </div>
      <div className="description">
        <div id="title_help">
          <h3>Je n'arrive pas à accéder à la carte</h3>
          <br />
          <p>Pour accéder à la carte, vous devez d'abord vous identifier ou créer un compte</p>
        </div>
        <div id="title_help">
          <h3>Comment devenir un Data Scientist ou un Administrateur ?</h3>
          <br />
          <p>
            Vous devez vous connecter, ensuite rendez vous sur l'onglet "Outils" dans la barre de
            navigation.
          </p>
          <p>Une fois connecté choisisez la demande que vous souhaitez</p>
        </div>
      </div>
      <div className="description">
        <div id="title_help">
          <h3>Comment avoir accès aux informations des stations / bus ?</h3>
          <br />
          <p>Rendez vous sur l'onglet "Carte" de la barre de navigation.</p>
          <p>
            Une fois sur la page de la carte, affichez dans les layers les bus / stations. Pour
            afficher les informations, vous devez cliquer sur la station ou le bus en question.
          </p>
        </div>
      </div>
    </div>
  );
};

Help.propTypes = {};

export default Help;
