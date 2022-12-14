import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../../components/UnderlinedTitle';
import List from '../../components/List';
import ReactModal from 'react-modal';
import { useState } from 'react';
import Button from '../../components/Button';
import ConfirmModal from '../../components/ConfirmModal';
import { useEffect } from 'react';
import Lottie from 'react-lottie';

import warningLottie from '../../assets/lotties/warning.json';
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

const ManageUsers = (props) => {
  const [showModal, setShowModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [modalMode, setModalMode] = useState('');
  const [usersList, setUsersList] = useState();
  const [loadingUsers, setLoadingUsers] = useState(true);
  const [selectValue, setSelectValue] = useState('');
  const [modalValidateLoading, setModalValidateLoading] = useState(false);

  useEffect(() => {
    adminService.getUsers().then((users) => {
      setUsersList(users);
      setLoadingUsers(false);
    });
  }, []);

  // Modal actions
  const handleClose = () => {
    setShowModal(false);
    setSelectedUser(null);
  };

  const handleValidate = () => {
    if (modalMode === 'delete') deleteUser(selectedUser);
    if (modalMode === 'edit') updateRole(selectedUser, selectValue);
  };

  const openModal = (mode, userId) => {
    setSelectedUser(userId);
    setModalMode(mode);
    setShowModal(true);
    if (mode === 'edit') setSelectValue(usersList.find((user) => user.id === userId).role);
  };
  useEffect(() => {
    if (!modalValidateLoading) {
      setShowModal(false);
    }
  }, [modalValidateLoading]);

  // users actions
  const updateRole = (id, role) => {
    setModalValidateLoading(true);
    adminService
      .changeRole(id, role)
      .then((success) => {
        if (!success) return;

        // Update list
        const newList = usersList.map((user) => {
          if (user.id === id) {
            return { ...user, role };
          }
          return user;
        });
        setUsersList(newList);
      })
      .finally(() => {
        setModalValidateLoading(false);
        setSelectValue('');
      });
  };

  const deleteUser = (id) => {
    setModalValidateLoading(true);
    adminService
      .deleteUser(id)
      .then((success) => {
        if (!success) return;

        // Update list
        const newList = usersList.filter((user) => user.id !== id);
        setUsersList(newList);
      })
      .finally(() => {
        setModalValidateLoading(false);
        setSelectValue('');
      });
  };

  //List options
  const columns = [
    {
      field: 'id',
      headerName: '#',
    },
    {
      field: 'name',
      headerName: 'Nom',
    },
    {
      field: 'email',
      headerName: 'Email',
    },
    {
      field: 'role',
      headerName: 'Role',
    },
    {
      field: 'actions',
      headerName: 'Actions',
    },
  ];

  const rows = useMemo(() => {
    return (
      usersList &&
      usersList.map((user) => ({
        id: user.id,
        name: user.firstName + ' ' + user.lastName,
        email: user.email,
        role: UserRolesString[user.role],
        actions: (
          <div className="actions-container">
            <i
              className="icon-button fa-solid fa-pen"
              onClick={() => {
                openModal('edit', user.id);
              }}
            ></i>
            <i
              className="delete-btn icon-button fa-solid fa-trash"
              onClick={() => {
                openModal('delete', user.id);
                // alert('delete user with id : ' + line[0]);
              }}
            ></i>
          </div>
        ),
      }))
    );
  }, [usersList]);

  return (
    <div className="ManageUsers">
      <UnderlinedTitle>Liste des utilisateurs</UnderlinedTitle>
      <Button
        text="Ajouter un utilisateur"
        className="add-user"
        onClick={() => {
          openModal('add');
        }}
      />

      <List columns={columns} rows={rows} className="users-table" loading={loadingUsers} />

      <ConfirmModal
        visible={showModal}
        onClose={handleClose}
        onValidate={handleValidate}
        validationLoading={modalValidateLoading}
        validateText={
          modalMode === 'delete'
            ? 'Confirmer'
            : modalMode === 'edit'
            ? 'Modifier'
            : modalMode === 'add'
            ? 'Ajouter'
            : 'Valider'
        }
        showCancel={modalMode !== 'delete'}
        importantValidation={modalMode === 'delete'}
        title={
          modalMode === 'edit'
            ? "Modification de l'utilisateur"
            : modalMode === 'add'
            ? 'Ajouter un utilisateur'
            : ''
        }
      >
        {modalMode === 'delete' ? (
          <>
            <Lottie options={warningOptions} width={100} height={100} />
            <p>
              Êtes-vous sûr de vouloir supprimer l'utilisateur #{selectedUser}.
              <br />
              Cette action est irreversible.
            </p>
          </>
        ) : modalMode === 'edit' ? (
          <>
            <p style={{ marginBottom: '10px' }}>Vous éditez l'utilisateur #{selectedUser} </p>
            <Select
              label={'Role'}
              value={selectValue}
              onChange={(value) => setSelectValue(value)}
              options={Object.values(UserRoles).map((role) => ({
                value: role,
                label: UserRolesString[role],
              }))}
            />
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

ManageUsers.propTypes = {};

export default ManageUsers;
