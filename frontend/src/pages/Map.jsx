import React, { useMemo } from 'react';
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
import { useState } from 'react';
import { useEffect } from 'react';
import mapService from '../services/map.service';
import moment from 'moment';
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton';

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
  const [busData, setBusData] = useState(null);
  const [linesData, setLinesData] = useState(null);
  const [stopsData, setStopsData] = useState(null);

  useEffect(() => {
    mapService.getStops().then((stops) => setStopsData(stops));
    mapService.getLines().then((lines) => setLinesData(lines));

    const loadBus = () => {
      mapService.getBus().then((buses) => {
        setBusData(buses);
      });
    };
    loadBus();

    const timer = setInterval(loadBus, 30000);
    return () => clearInterval(timer);
  }, []);

  const busList = useMemo(() => {
    if (!busData) return;

    return busData.map((bus) => {
      return {
        ...bus,
        color: colorLine[bus.lineNb],
        delay: Math.round(bus.delay / 60),
        nextStopTime: moment(bus.nextStopTime).fromNow(),
      };
    });
  }, [busData]);

  let linesList = useMemo(() => {
    if (!linesData) return;
    linesData.sort((lineA, lineB) => {
      let idA = lineA.lineId,
        idB = lineB.lineId;

      if (idA < idB) return -1;
      if (idA > idB) return 1;
      return 0;
    });

    return linesData;
  }, [linesData]);

  return (
    <div id="ContainerMapPage">
      <div>
        <MapContainer id="Map" center={position} zoom={15} minZoom={12.5} maxBounds={bounds}>
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          <LayersControl position="topright">
            <LayersControl.Overlay name={'Arrêts'}>
              <LayerGroup>
                {stopsData &&
                  stopsData.map((stop) => (
                    <CircleMarker
                      key={stop.id}
                      center={stop.coordinates}
                      pathOptions={{ color: 'blue' }}
                      radius={15}
                    >
                      <Popup>
                        Nom de l'arrêt: {stop.name} <br />
                        {/* nom de la ligne: {stop.nameLine}({stop.numberLine}) <br /> */}
                        {/* prochain passage: {stop.arriveTime} <br /> */}
                        {/* destination: {stop.nextDest} <br /> */}
                        {/* date: {stop.date} */}
                      </Popup>
                    </CircleMarker>
                  ))}
              </LayerGroup>
            </LayersControl.Overlay>
            <LayersControl.Overlay name={'Bus'}>
              <LayerGroup>
                {busList &&
                  busList.map((bus, i) => (
                    <Marker key={i} icon={svgIcon(bus.color)} position={bus.coordinates}>
                      <Popup>
                        Ligne: ({bus.lineNb}) - {bus.lineName} <br />
                        Destination: {bus.destination} <br />
                        Prochain arret: {bus.nextStopName} {bus.nextStopTime}
                        <br />
                        {/* Arrivée au prochain arret: {bus.nextStopTime} <br /> */}
                        Retard estimé: {bus.delay} minute{Math.abs(bus.delay) > 1 && 's'} <br />
                      </Popup>
                    </Marker>
                  ))}
              </LayerGroup>
            </LayersControl.Overlay>
            <div className="separator"></div>
            {linesList &&
              linesList.map((line) => (
                <LayersControl.Overlay
                  key={line.lineId}
                  name={'(' + line.lineId + ') ' + line.lineName}
                >
                  <LayerGroup>
                    <Polyline positions={line.coordinates} color={'#' + line.lineColor} />
                  </LayerGroup>
                </LayersControl.Overlay>
              ))}
          </LayersControl>
        </MapContainer>
      </div>
      <Legend lines={linesList} />
    </div>
  );
};

Map.propTypes = {};

export default Map;

//define component to display legend content
var Legend = ({ lines }) => {
  return (
    <div id="legend">
      <h2>Légende Map</h2>

      <div className="legend-grid">
        <div className="icon">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style={{ width: '1rem' }}>
            <path
              d="M256 0C390.4 0 480 35.2 480 80V96l0 32c17.7 0 32 14.3 32 32v64c0 17.7-14.3 32-32 32l0 160c0 17.7-14.3 32-32 32v32c0 17.7-14.3 32-32 32H384c-17.7 0-32-14.3-32-32V448H160v32c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32l0-32c-17.7 0-32-14.3-32-32l0-160c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h0V96h0V80C32 35.2 121.6 0 256 0zM96 160v96c0 17.7 14.3 32 32 32H240V128H128c-17.7 0-32 14.3-32 32zM272 288H384c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H272V288zM112 400c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zm288 0c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zM352 80c0-8.8-7.2-16-16-16H176c-8.8 0-16 7.2-16 16s7.2 16 16 16H336c8.8 0 16-7.2 16-16z"
              fill="white"
            />
          </svg>
        </div>
        <span className="label">BUS</span>

        {lines &&
          lines.map((line) => (
            <React.Fragment key={line.lineId}>
              <div className="icon">
                <div className="line" style={{ backgroundColor: '#' + line.lineColor }}>
                  {line.lineId}
                </div>
              </div>
              <span className="label">{line.lineName}</span>
            </React.Fragment>
          ))}
        {!lines && (
          <SkeletonTheme baseColor="#272727" highlightColor="#323232">
            {[...Array(5)].map((_, i) => (
              <React.Fragment key={i}>
                <Skeleton circle width={20} height={20} />
                <Skeleton count={1.5} />
              </React.Fragment>
            ))}
          </SkeletonTheme>
        )}
      </div>

      {/* <div id="bus">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style={{ width: '1rem' }}>
          <path
            d="M256 0C390.4 0 480 35.2 480 80V96l0 32c17.7 0 32 14.3 32 32v64c0 17.7-14.3 32-32 32l0 160c0 17.7-14.3 32-32 32v32c0 17.7-14.3 32-32 32H384c-17.7 0-32-14.3-32-32V448H160v32c0 17.7-14.3 32-32 32H96c-17.7 0-32-14.3-32-32l0-32c-17.7 0-32-14.3-32-32l0-160c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h0V96h0V80C32 35.2 121.6 0 256 0zM96 160v96c0 17.7 14.3 32 32 32H240V128H128c-17.7 0-32 14.3-32 32zM272 288H384c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H272V288zM112 400c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zm288 0c17.7 0 32-14.3 32-32s-14.3-32-32-32s-32 14.3-32 32s14.3 32 32 32zM352 80c0-8.8-7.2-16-16-16H176c-8.8 0-16 7.2-16 16s7.2 16 16 16H336c8.8 0 16-7.2 16-16z"
            fill="white"
          />
        </svg>{' '}
        Bus
        <ul>
          {lines &&
            lines.map((line) => (
              <li key={line.lineId}>
                <span id="bullet" style={{ color: '#' + line.lineColor }}>
                  {' '}
                  -{' '}
                </span>
                <span id="number">{line.lineId} </span>
                {line.lineName}
              </li>
            ))}
        </ul>
      </div> */}
    </div>
  );
};
