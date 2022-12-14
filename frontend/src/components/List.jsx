import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Loader from './Loader';

const List = ({ columns, rows, className, loading }) => {
  return (
    <table className={classNames('List', className)}>
      <thead>
        <tr>{columns && columns.map((column, i) => <th key={i}>{column.headerName}</th>)}</tr>
      </thead>
      <tbody>
        {loading ? (
          <tr style={{ borderBottom: 'none' }}>
            <td style={{ gridColumn: '1/' + (columns.length + 1) }}>
              <Loader size={150} />
            </td>
          </tr>
        ) : rows ? (
          rows.map((row) => (
            <tr key={row.id}>
              {columns &&
                columns.map((col) => <td key={row.id + '-' + row[col.field]}>{row[col.field]}</td>)}
            </tr>
          ))
        ) : (
          <tr>
            <td style={{ gridColumn: '1/' + (columns.length + 1) }}>Aucune données</td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

List.propTypes = {
  className: PropTypes.string,
  loading: PropTypes.bool,
  rows: PropTypes.arrayOf(PropTypes.object),
  columns: PropTypes.arrayOf(
    PropTypes.shape({
      field: PropTypes.string.isRequired,
      headerName: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default List;
