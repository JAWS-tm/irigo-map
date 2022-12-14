import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../../components/UnderlinedTitle';
import List from '../../components/List';
import { useState } from 'react';
import Button from '../../components/Button';
import ConfirmModal from '../../components/ConfirmModal';
import { useEffect } from 'react';
import Lottie from 'react-lottie';

import warningLottie from '../../assets/lotties/warning.json';
import addLottie from '../../assets/lotties/add.json';
import Select from '../../components/Select';
import adminService from '../../services/admin.service';
import { useMemo } from 'react';
import { UserRoles, UserRolesString } from '../../constants';

const warningOptions = {
  loop: true,
  autoplay: true,
  animationData: warningLottie,
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice',
  },
};

const addOptions = {
  loop: true,
  autoplay: true,
  animationData: addLottie,
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice',
  },
};

const ManageRequests = (props) => {
  const [showModal, setShowModal] = useState(false);
  const [modalMode, setModalMode] = useState('');
  const [requestsList, setRequestsList] = useState();
  const [loadingRequests, setLoadingRequests] = useState(true);
  const [selectedRequest, setSelectedRequest] = useState();
  const [modalValidateLoading, setModalValidateLoading] = useState(false);

  useEffect(() => {
    adminService.getRequests().then((res) => {
      console.log(res);
      if (res.status === 'OK') setRequestsList(res.payload);
      setLoadingRequests(false);
    });
  }, []);

  // Modal actions
  const handleClose = () => {
    setShowModal(false);
  };

  const handleValidate = () => {
    if (modalMode === 'remove') removeRequest(selectedRequest);
    if (modalMode === 'validate') validateRequest(selectedRequest);
  };

  const openModal = (mode, requestId) => {
    setSelectedRequest(requestId);
    setModalMode(mode);
    setShowModal(true);
  };

  useEffect(() => {
    if (!modalValidateLoading) {
      setShowModal(false);
    }
  }, [modalValidateLoading]);

  const deleteRequest = (id) => {
    setRequestsList(requestsList.filter((request) => request.id !== id));
  };

  // requests actions
  const validateRequest = (id) => {
    setModalValidateLoading(true);
    adminService
      .validateRequest(id)
      .then(() => {
        deleteRequest(id);
      })
      .finally(() => setModalValidateLoading(false));
  };

  const removeRequest = (id) => {
    setModalValidateLoading(true);
    adminService
      .removeRequest(id)
      .then(() => {
        deleteRequest(id);
      })
      .finally(() => setModalValidateLoading(false));
  };

  //List options
  const columns = [
    {
      field: 'id',
      headerName: '#',
    },
    {
      field: 'user',
      headerName: 'Utilisateur',
    },
    {
      field: 'job',
      headerName: 'Profession',
    },
    {
      field: 'company',
      headerName: 'Entreprise',
    },
    {
      field: 'description',
      headerName: 'Message',
    },
    {
      field: 'actions',
      headerName: 'Actions',
    },
  ];

  const rows = useMemo(() => {
    return (
      requestsList &&
      requestsList.map((request) => ({
        id: request.id,
        user: request.user.firstName + ' ' + request.user.lastName + ` (#${request.user.id})`,
        job: request.job,
        company: request.company,
        description: request.description,
        actions: (
          <div className="actions-container">
            <i
              className="validate-btn icon-button fa-solid fa-circle-check"
              onClick={() => {
                openModal('validate', request.id);
              }}
            ></i>
            <i
              className="remove-btn icon-button fa-solid fa-trash"
              onClick={() => {
                openModal('remove', request.id);
              }}
            ></i>
          </div>
        ),
      }))
    );
  }, [requestsList]);

  return (
    <div className="ManageRequests">
      <UnderlinedTitle>Liste des demandes Data Scientist</UnderlinedTitle>

      <List columns={columns} rows={rows} className="requests-table" loading={loadingRequests} />

      <ConfirmModal
        visible={showModal}
        onClose={handleClose}
        onValidate={handleValidate}
        validationLoading={modalValidateLoading}
        validateText={modalMode === 'delete' ? 'Confirmer' : 'Valider'}
        showCancel={false}
        importantValidation={modalMode === 'remove'}
        title={''}
      >
        {modalMode === 'remove' ? (
          <>
            <Lottie options={warningOptions} width={100} height={100} />
            <p>
              Êtes-vous sûr de vouloir supprimer la demande numéro {selectedRequest}.
              <br />
              Cette action est irreversible.
            </p>
          </>
        ) : modalMode === 'validate' ? (
          <>
            <Lottie options={addOptions} width={100} height={100} />
            <p>
              Êtes-vous sûr de vouloir valider la demande numéro {selectedRequest}.
              <br />
              L'utilisateur recevra un mail l'informant de l'acceptation de sa demande.
            </p>
          </>
        ) : modalMode === 'add' ? (
          <>Renseignez les données</>
        ) : (
          <p>bug</p>
        )}
      </ConfirmModal>
    </div>
  );
};

ManageRequests.propTypes = {};

export default ManageRequests;
