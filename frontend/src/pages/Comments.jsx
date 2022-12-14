import { Field, Formik } from 'formik';
import React, { useMemo, useState } from 'react';
import { useEffect } from 'react';
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton';
import { Form } from 'react-router-dom';
import Button from '../components/Button';
import FormInput from '../components/FormInput';
import StarNotation from '../components/StarNotation';
import commentService from '../services/comment.service';
import mapService from '../services/map.service';

const Comments = (props) => {
  const [linesData, setLinesData] = useState();
  const [listComm, setListComm] = useState([]);
  const [selectedLine, setSelectedLine] = useState(-1);

  useEffect(() => {
    mapService.getLines().then((lines) => setLinesData(lines));
  }, []);
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

  const requestComm = (numberLine) => {
    commentService.getAllUsersCommentsByNumberLine(numberLine).then((comments) => {
      setListComm(comments);
    });
  };

  return (
    <div className="commentPage">
      <div className="legendPt">
        {linesList &&
          linesList.map((line) => (
            <React.Fragment key={line.lineId}>
              <div
                className="gridComm"
                onClick={() => {
                  setSelectedLine(line.lineId);
                  requestComm(line.lineId);
                }}
              >
                <div className="icon">
                  <div className="lineComm" style={{ backgroundColor: '#' + line.lineColor }}>
                    {line.lineId}
                  </div>
                </div>
                <span className="label">{line.lineName}</span>
              </div>
            </React.Fragment>
          ))}
        {!linesList && (
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
      <div className="commentPt">
        <div style={{ display: selectedLine === -1 ? 'none' : 'block' }}>
          {listComm &&
            listComm.map((comm) => (
              <div className="commentBlock" key={comm.user.id}>
                <div className="entête">
                  <div className="name">
                    {comm.user.firstName} {comm.user.lastName} :
                  </div>
                </div>
                <div className="corp">
                  <div className="commentary">{comm.commentary}</div>
                  <div className="notation">
                    <StarNotation field={{ value: comm.notation }} blocked={true} />
                  </div>
                </div>
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

Comments.propTypes = {};

export default Comments;
