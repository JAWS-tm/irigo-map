import React from 'react';
import PropTypes from 'prop-types';
import heroImg from '../assets/pictures/hero-bus-illustration.png';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import PopupError from '../components/PopupError';

const Home = (props) => {
  return (
    <div className="Home">
      <div className="header">
        <div className="hero-section">
          <div className="hero-data">
            <h2 className="title">Bienvenue sur IrigoMap</h2>
            <p className="description">
              Nous sommes 3 étudiants en dernière année de Bachelor Ingénierie Informatique et
              Electronique. Dans le cadre de notre formation, nous avons été amené à créer ce site
              pour un enseignement intitulé Projet Génie Logiciel. Bonne visite !
            </p>
            <div className="buttons-wrapper">
              <Button onClick={PopupError} text="Carte" />
            </div>
          </div>
          <div>
            <img src={heroImg} alt="" />
          </div>
        </div>
        <div className="card-section">
          <div className="card-list">
            <div className="card">
              <i className="fa-solid fa-clock"></i>
              <h3>Affichage en temps réel</h3>
            </div>
            <div className="card">
              <i className="fa-solid fa-bus"></i>
              <h3>Trajets des bus</h3>
            </div>
            <div className="card">
              <i className="fa-solid fa-train-tram"></i>
              <h3>Trajets des tramway</h3>
            </div>
            <div className="card">
              <i className="fa-solid fa-list"></i>
              <h3>Vos stations</h3>
            </div>
          </div>
        </div>
      </div>
      <section className="about-section">
        <UnderlinedTitle>À savoir</UnderlinedTitle>
        <p className="description">
          Attention, dans le cadre de notre projet nous récolotons les informations que vous
          avez/allez rentrer dans l'inscription mais aussi dans les demandes Data Scientist et
          Administrateur
        </p>
      </section>
    </div>
  );
};

Home.propTypes = {};

export default Home;
