import React from 'react';
import heroImg from '../assets/pictures/hero-bus-illustration.png';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';

const Home = () => {
  const goToAbout = () => {
    location.hash = '';
    location.hash = '#about';
  };

  return (
    <div className="Home">
      <div className="header">
        <div className="hero-section">
          <div className="hero-data">
            <h2 className="title">Bienvenue sur IrigoMap</h2>
            <p className="description">
              Bienvenue sur IrigoMap, votre outil incontournable pour explorer les transports en
              commun d'Angers en un clin d'œil. Découvrez les horaires, les itinéraires et les
              arrêts de bus et de tramway de la ville, le tout avec simplicité et efficacité.
              Planifiez vos déplacements en toute sérénité grâce à IrigoMap !
            </p>
            <div className="buttons-wrapper">
              <Button onClick={goToAbout} text="En savoir plus" />
            </div>
          </div>
          <div className="hero-img">
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
      <section className="about-section" id="about">
        <UnderlinedTitle>À savoir</UnderlinedTitle>
        <p className="description">
          IrigoMap est un projet réalisé par des étudiants de l'ESEO d'Angers dans le cadre d'un
          projet de fin d'année. L'objectif est d'afficher en temps réel toutes les informations sur
          les transports en commun de la ville d'Angers.
        </p>
      </section>
    </div>
  );
};

Home.propTypes = {};

export default Home;
