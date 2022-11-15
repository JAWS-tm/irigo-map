import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Input from '../components/Input';
import Button from '../components/Button';
import RadioGroup from '../components/Radio/RadioGroup';
import RadioOption from '../components/Radio/RadioOption';
import { useState } from 'react';

const Register = (props) => {
  const [gender, setGender] = useState();
  const [travelHabits, setTravelHabits] = useState();
  const [transportFrequency, setTransportFrequency] = useState();

  return (
    <div className="Register">
      <UnderlinedTitle>Inscription</UnderlinedTitle>
      <form action="#">
        <div className="row">
          <Input label="Prénom *" className="input" />
          <Input label="Nom *" className="input" />
        </div>

        <Input label="Email *" className="input" type={'email'} />

        <div className="row">
          <Input label="Mot de passe *" className="input" type="password" />
          <Input label="Confirmation *" className="input" type="password" />
        </div>

        <div className="row-title">
          <p>Date de naissance *</p>
        </div>
        <div className="row">
          <Input label="Jour" className="input" type="number" inputProps={{ min: 1, max: 31 }} />
          <Input label="Mois" className="input" type="number" inputProps={{ min: 1, max: 12 }} />
          <Input
            label="Année"
            className="input"
            type="number"
            inputProps={{ min: 1, max: new Date().getFullYear() }}
          />
        </div>

        {/* <span>Genre</span> */}
        <div className="row-title">
          <p>Genre *</p>
        </div>
        <RadioGroup name="gender" onChange={(e) => setGender(e.target.value)} value={gender}>
          <RadioOption label="Homme" value="MALE" />
          <RadioOption label="Femme" value="FEMALE" />
          <RadioOption label="Autre" value="OTHER" />
          <RadioOption label="Non précisé" value="UNKNOWN" />
        </RadioGroup>

        <div className="row-title">
          <p>Habitudes de déplacement</p>
        </div>
        <RadioGroup
          name="travel-habits"
          onChange={(e) => setTravelHabits(e.target.value)}
          value={travelHabits}
        >
          <RadioOption label="À pied" value="FOOT" />
          <RadioOption label="Vélo" value="BIKE" />
          <RadioOption label="Voiture" value="CAR" />
          <RadioOption label="Transports" value="TRANSPORT" />
        </RadioGroup>

        <div className="row-title">
          <p>Fréquence d'utilisation des transports</p>
        </div>
        <RadioGroup
          name="transport-frequency"
          onChange={(e) => setTransportFrequency(e.target.value)}
          value={transportFrequency}
          className="transport-radios"
        >
          <RadioOption label="Quotidien" value="DAILY" />
          <RadioOption label="Hebdomadaire" value="WEEKLY" />
          <RadioOption label="Mensuel" value="MONTHLY" />
          <RadioOption label="Épisodique" value="EPISODIC" />
          <RadioOption label="Jamais" value="NEVER" />
        </RadioGroup>

        <div className="btn-wrapper">
          <Button text="M'inscrire" />
        </div>
      </form>
    </div>
  );
};

Register.propTypes = {};

export default Register;
