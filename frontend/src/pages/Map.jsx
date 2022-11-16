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
import bus from '../pictures/bus.png';
import axios from 'axios';
import { useState } from 'react';
import { useEffect } from 'react';

const lineURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=irigo_gtfs_lines&q=&rows=118&facet=route_short_name&facet=route_long_name&facet=route_type';
const stationURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=horaires-theoriques-et-arrets-du-reseau-irigo-gtfs&q=&rows=2000&facet=stop_id&facet=stop_name&facet=wheelchair_boarding';
const stopURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=bus-tram-circulation-passages&q=&rows=2000&facet=mnemoligne&facet=nomligne&facet=dest&facet=mnemoarret&facet=nomarret&facet=numarret';
const busURL =
  'https://data.angers.fr/api/records/1.0/search/?dataset=bus-tram-position-tr&q=&rows=2000&facet=novh&facet=mnemoligne&facet=nomligne&facet=dest';

const southWest = L.latLng(47.39, -0.66);
const northEast = L.latLng(47.58, -0.44);
const bounds = L.latLngBounds(southWest, northEast);
const busIcon = L.icon({
  iconUrl: bus,

  iconSize: [45, 50], // size of the icon
  iconAnchor: [22, 50], // point of the icon which will correspond to marker's location
  popupAnchor: [0, 0], // point from which the popup should open relative to the iconAnchor
});
const position = [47.472062, -0.55253];
const colorLine = [
  ['#'],
  ['#1FA22E'],
  ['#006AB2'],
  ['#EE7F00'],
  ['#93117E'],
  ['#FFDD00'],
  ['#555540'],
  ['#'],
  ['#BD7419'],
  ['#E2007A'],
  ['#'],
  ['#41A3D7'],
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
      console.log('refresh buses');
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
        list.push({
          posBus: currentBus.fields.coordonnees,
          numberLine: currentBus.fields.mnemoligne,
          nextStation: currentBus.fields.nomarret,
          nameLine: currentBus.fields.nomligne,
          dest: currentBus.fields.dest,
          arriveTime: dateArret.getHours() + ':' + dateArret.getMinutes(),
          retard: Math.round(currentBus.fields.ecart / 60),
          date: currentBus.fields.harret.split('T')[0],
        });
      }
      return list;
    }
  }, [busData]);

  return (
    <div>
      <h2>Map Pages</h2>
      <Link to="/Home">Home</Link>
      <Link to="/about">About project</Link>
      <Link to="/users">User</Link>

      <MapContainer id="Map" center={position} zoom={15} minZoom={12.5} maxBounds={bounds}>
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <LayersControl position="topright" style="text-align:left">
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
          <LayersControl.Overlay name={'station'}>
            <LayerGroup>
              {nextStop &&
                nextStop.map((stop, i) => {
                  return (
                    <CircleMarker
                      key={i}
                      center={stop.posStop}
                      pathOptions={{ fillColor: 'blue' }}
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
                    <Marker key={i} position={currBus.posBus} icon={busIcon}>
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
        </LayersControl>
      </MapContainer>
    </div>
  );
};

Map.propTypes = {};

export default Map;
