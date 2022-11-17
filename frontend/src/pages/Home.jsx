import React from 'react';
import PropTypes from 'prop-types';
import heroImg from '../assets/pictures/hero-bus-illustration.png';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';

const Home = (props) => {
  return (
    <div className="Home">
      <div className="header">
        <div className="hero-section">
          <div className="hero-data">
            <h2 className="title">Bienvenue sur IrigoMap</h2>
            <p className="description">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus eveniet nemo rem
              eaque laudantium.
            </p>
            <div className="buttons-wrapper">
              <Button text="Essayer" />
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
        <UnderlinedTitle>À propos</UnderlinedTitle>
        <p className="description">
          Lorem ipsum dolor, sit amet consectetur adipisicing elit. Consequuntur, nobis? Placeat
          maxime illo est repudiandae dignissimos voluptates inventore sunt vero earum, iste
          necessitatibus reprehenderit fuga aperiam quas voluptatum aut sapiente.
        </p>
      </section>
    </div>
  );
};

Home.propTypes = {};

export default Home;
