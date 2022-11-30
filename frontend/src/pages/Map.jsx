import React, { useMemo } from 'react';
import { Link } from 'react-router-dom';
import {
  MapContainer,
  LayersControl,
  LayerGroup,
  TileLayer,
  Polyline,
  CircleMarker,
  Popup,
  Marker,
} from 'react-leaflet';
import * as L from 'leaflet';
import bus from '../assets/pictures/bus.png';
import axios from 'axios';
import { useState, useEffect } from 'react';
import StarNotation from '../components/StarNotation';
import Input from '../components/Input';
import Button from '../components/Button';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';
import commentService from '../services/comment.service';
import { config } from '../config/config';

const lineURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=irigo_gtfs_lines&q=&rows=118&facet=route_short_name&facet=route_long_name&facet=route_type';
const stopURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=bus-tram-circulation-passages&q=&rows=2000&facet=mnemoligne&facet=nomligne&facet=dest&facet=mnemoarret&facet=nomarret&facet=numarret';
const busURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=bus-tram-position-tr&q=&rows=2000&facet=novh&facet=mnemoligne&facet=nomligne&facet=dest';

const southWest = L.latLng(47.39, -0.66);
const northEast = L.latLng(47.58, -0.44);
const bounds = L.latLngBounds(southWest, northEast);
const position = [47.472062, -0.55253];
const colorLine = [
  [''],
  ['#1FA22E'],
  ['#006AB2'],
  ['#EE7F00'],
  ['#93117E'],
  ['#FFDD00'],
  ['#555540'],
  ['#8E96C7'],
  ['#BD7419'],
  ['#E2007A'],
  ['#91C581'],
  ['#41A3D7'],
  ['#DE84B1'],
];

const useFetch = (url) => {
  const [datad, setDatas] = useState(null);

  useEffect(() => {
    axios.get(url).then((response) => {
      setDatas(response.data);
    });
  }, [url]);
  return datad;
};

const svgIcon = (color) => {
  return L.divIcon({
    html: `
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" >
    <path
      d="M256 0C390.4 0 480 35.2 480 80V96l0 32c17.7 0 32 14.3 32 32v64c0 17.7-14.3 32-32 32l0 160c0 17.7-14.3 32-32 32v32c0 17.7-14.3 32-32 32H384c-17.7 0-32-14.3-32-32V448H160v32c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32l0-32c-17.7 0-32-14.3-32-32l0-160c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h0V96h0V80C32 35.2 121.6 0 256 0zM96 160v96c0 17.7 14.3 32 32 32H240V128H128c-17.7 0-32 14.3-32 32zM272 288H384c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H272V288zM112 400c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zm288 0c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zM352 80c0-8.8-7.2-16-16-16H176c-8.8 0-16 7.2-16 16s7.2 16 16 16H336c8.8 0 16-7.2 16-16z"
      fill=${color}
    />
  </svg>
`,
    iconSize: [34, 34],
    iconAnchor: [17, 17],
    className: 'bus-icon',
  });
};

const Map = (props) => {
  let lineData = useFetch(lineURL);
  let listLine = useMemo(() => {
    if (lineData) {
      let posLine = null;
      let posL = null;
      let list = [];
      for (let line of lineData.records) {
        posLine = line.fields.shape.coordinates;
        posL = posLine && posLine.map((posArrayL) => posArrayL.map((posL) => [posL[1], posL[0]]));

        if (Number(line.fields.route_short_name) <= 12) {
          list.push({
            posL,
            color: line.fields.route_color,
            name: line.fields.route_long_name,
            number: line.fields.route_short_name,
          });
        }
      }
      list = list.sort((a, b) => {
        let fa = a.number,
          fb = b.number;

        if (fa < fb) {
          return -1;
        }
        if (fa > fb) {
          return 1;
        }
        return 0;
      });
      fillLegend(list);
      return list;
    }
  }, [lineData]);

  let stopData = useFetch(stopURL);
  let nextStop = useMemo(() => {
    if (stopData) {
      let list = [];
      for (let stop of stopData.records) {
        list.push({
          posStop: stop.fields.coordonnees,
          numberLine: stop.fields.mnemoligne,
          nameStation: stop.fields.nomarret,
          nameLine: stop.fields.codeparcours,
          nextDest: stop.fields.dest,
          arriveTime: stop.fields.arriveetheorique.split('T')[1].split('+')[0],
          date: stop.fields.arriveetheorique.split('T')[0],
        });
      }
      return list;
    }
  }, [stopData]);

  const [busData, setBusData] = useState(null);
  useEffect(() => {
    const loadBus = () => {
      axios.get(busURL).then((res) => setBusData(res.data));
    };
    loadBus();

    const timer = setInterval(loadBus, 30000);
    return () => clearInterval(timer);
  }, []);

  let bus = useMemo(() => {
    if (busData) {
      let list = [];
      for (let currentBus of busData.records) {
        let dateArret = new Date(currentBus.fields.harret);
        if (currentBus.fields.dest != 'PAS EN SERVICE') {
          list.push({
            posBus: currentBus.fields.coordonnees,
            numberLine: currentBus.fields.mnemoligne,
            color: colorLine[parseFloat(currentBus.fields.mnemoligne)],
            nextStation: currentBus.fields.nomarret,
            nameLine: currentBus.fields.nomligne,
            dest: currentBus.fields.dest,
            arriveTime: dateArret.getHours() + ':' + dateArret.getMinutes(),
            retard: Math.round(currentBus.fields.ecart / 60),
            date: currentBus.fields.harret.split('T')[0],
          });
        }
      }
      return list;
    }
  }, [busData]);

  return (
    <div id="ContainerMapPage">
      <div>
        <MapContainer id="Map" center={position} zoom={15} minZoom={12.5} maxBounds={bounds}>
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          <LayersControl position="topright">
            <LayersControl.Overlay name={'station'}>
              <LayerGroup>
                {nextStop &&
                  nextStop.map((stop, i) => {
                    return (
                      <CircleMarker
                        key={i}
                        center={stop.posStop}
                        pathOptions={{ color: 'blue' }}
                        radius={15}
                      >
                        <Popup>
                          arret: {stop.nameStation} <br />
                          nom de la ligne: {stop.nameLine}({stop.numberLine}) <br />
                          prochain passage: {stop.arriveTime} <br />
                          destination: {stop.nextDest} <br />
                          date: {stop.date}
                        </Popup>
                      </CircleMarker>
                    );
                  })}
              </LayerGroup>
            </LayersControl.Overlay>
            <LayersControl.Overlay name={'bus'}>
              <LayerGroup>
                {bus &&
                  bus.map((currBus, i) => {
                    return (
                      <Marker key={i} icon={svgIcon(currBus.color)} position={currBus.posBus}>
                        <Popup>
                          prochain arret: {currBus.nextStation} <br />
                          nom de la ligne: {currBus.nameLine}({currBus.numberLine}) <br />
                          destination: {currBus.dest} <br />
                          Arrivée au prochain arret: {currBus.arriveTime} <br />
                          retard estimer: {currBus.retard} <br />
                          date: {currBus.date}
                        </Popup>
                      </Marker>
                    );
                  })}
              </LayerGroup>
            </LayersControl.Overlay>
            <div className="separator"></div>
            {listLine &&
              listLine.map((line, i) => {
                return (
                  <LayersControl.Overlay key={i} name={'(' + line.number + ')' + line.name}>
                    <LayerGroup>
                      <Polyline positions={line.posL} color={'#' + line.color} />
                    </LayerGroup>
                  </LayersControl.Overlay>
                );
              })}
          </LayersControl>
        </MapContainer>
      </div>
      <div id="legend">
        <h2>Légende Map</h2>
        <div id="bus">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style={{ width: '1rem' }}>
            <path
              d="M256 0C390.4 0 480 35.2 480 80V96l0 32c17.7 0 32 14.3 32 32v64c0 17.7-14.3 32-32 32l0 160c0 17.7-14.3 32-32 32v32c0 17.7-14.3 32-32 32H384c-17.7 0-32-14.3-32-32V448H160v32c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32l0-32c-17.7 0-32-14.3-32-32l0-160c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h0V96h0V80C32 35.2 121.6 0 256 0zM96 160v96c0 17.7 14.3 32 32 32H240V128H128c-17.7 0-32 14.3-32 32zM272 288H384c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H272V288zM112 400c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zm288 0c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zM352 80c0-8.8-7.2-16-16-16H176c-8.8 0-16 7.2-16 16s7.2 16 16 16H336c8.8 0 16-7.2 16-16z"
              fill="white"
            />
          </svg>{' '}
          Bus
        </div>
        <Legend></Legend>
      </div>
    </div>
  );
};

Map.propTypes = {};

export default Map;

//define class to containe legend's elements
class Item {
  number;
  name;
  color;

  constructor(number, name, color) {
    this.number = number;
    this.color = color;
    this.name = name;
  }
}
//creation list for legend
let legend = [];
//called when map is updated
function fillLegend(listLine) {
  legend = []; //list is empty
  try {
    for (let i = 0; i < listLine.length; i++) {
      //for each element we fill legend
      legend.push(new Item(listLine[i].number, listLine[i].name, listLine[i].color));
    }
  } catch (e) {} //prevent the code from crashing when list is undefined
}

const initialValues_data = {
  notation: '',
  comment: '',
};

// const [data, setData] = useState(initialValues_data);

// //define component to display legend content
// const AUTH_API_URL = config.API_URL + '/comments/';
// const ValueFormik = async () => {
//   let res = await axios.get(AUTH_API_URL + id);
//   setData(res.data.payload);
// };

var Legend = () => {
  const [displayNotation, setDisplayNotation] = useState({ ...legend.map(() => false) });

  const handleSent = (numberLine) => (values) => {
    commentService.handleSent(values.notation, values.comment, numberLine);
  };
  return (
    <ul>
      {legend.map((ligne) => (
        <li style={{ cursor: 'pointer' }} value={ligne.name} key={ligne.name}>
          <div
            onClick={() =>
              setDisplayNotation({
                ...displayNotation,
                [ligne.number - 1]: !displayNotation[ligne.number - 1],
              })
            }
          >
            <span id="bullet" style={{ color: '#' + ligne.color }}>
              {' '}
              -{' '}
            </span>
            <span id="number">{ligne.number} </span>
            {ligne.name}
          </div>

          <Formik onSubmit={handleSent(ligne.number)} initialValues={initialValues_data}>
            {/* initialValues={data ? initialValues_data : ValueFormik()} */}
            <Form>
              <div style={{ display: displayNotation[ligne.number - 1] ? 'inline-block' : 'none' }}>
                <Field component={StarNotation} name="notation"></Field>
                <div style={{ padding: '10px' }}>
                  <Field
                    label="commentaire ..."
                    name="comment"
                    className="input"
                    component={FormInput}
                  />
                </div>

                <div className="btn-wrapper" style={{ padding: '5px' }}>
                  <Button type="submit" text="envoyer" />
                </div>
              </div>
            </Form>
          </Formik>
        </li>
      ))}
    </ul>
  );
};
