(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('DashboardController', ['$scope', '$http', '$location', 'API', 'AuthService', 'EstadoHelper', 'ErrorHelper',
      function ($scope, $http, $location, API, AuthService, EstadoHelper, ErrorHelper) {

        if (!AuthService.estaLogueado()) {
          $location.path('/login');
          return;
        }

        $scope.citas = [];
        $scope.cargando = true;
        $scope.error = null;
        $scope.exito = null;
        $scope.editando = false;
        $scope.citaEditada = {};
        $scope.badgeClass = EstadoHelper.badgeClass;

        function cargarCitas() {
          $scope.cargando = true;
          $http.get(API.CITAS)
            .then(function (res) {
              $scope.citas = res.data;
            })
            .catch(function (err) {
              if (err.status === 401 || err.status === 403) {
                AuthService.cerrarSesion();
                $location.path('/login');
              } else {
                $scope.error = 'Error al cargar las citas.';
              }
            })
            .finally(function () {
              $scope.cargando = false;
            });
        }

        cargarCitas();

        $scope.cambiarEstado = function (cita) {
          if (cita.nuevoEstado === cita.estadoCita) return;

          $scope.exito = null;
          $scope.error = null;

          $http.patch(API.CITAS + '/' + cita.id + '/estado', { estadoCita: cita.nuevoEstado })
            .then(function () {
              cita.estadoCita = cita.nuevoEstado;
              $scope.exito = 'Estado de la cita #' + cita.id + ' actualizado.';
            })
            .catch(function (err) {
              var estadoIntentado = cita.nuevoEstado;
              cita.nuevoEstado = cita.estadoCita;
              var detalle = ErrorHelper.extraer(err, '');
              $scope.error = 'Error al cambiar cita #' + cita.id + ' a ' + estadoIntentado
                + (detalle ? ': ' + detalle : '. Verifica que el estado sea válido e inténtalo de nuevo.');
            });
        };

        $scope.abrirEdicion = function (cita) {
          $scope.citaEditada = {
            id:              cita.id,
            nombreCliente:   cita.nombreCliente,
            nombreMascota:   cita.nombreMascota,
            numeroTelefono:  cita.numeroTelefono,
            razonCita:       cita.razonCita,
            fechaCita:       new Date(cita.fechaCita)
          };
          $scope.editando = true;
        };

        $scope.cerrarEdicion = function () {
          $scope.editando = false;
        };

        $scope.guardarEdicion = function () {
          $scope.error = null;

          var body = {
            nombreCliente:   $scope.citaEditada.nombreCliente,
            nombreMascota:   $scope.citaEditada.nombreMascota,
            numeroTelefono:  $scope.citaEditada.numeroTelefono,
            razonCita:       $scope.citaEditada.razonCita,
            fechaCita:       $scope.citaEditada.fechaCita
          };

          $http.put(API.CITAS + '/' + $scope.citaEditada.id, body)
            .then(function () {
              $scope.exito = 'Cita #' + $scope.citaEditada.id + ' actualizada.';
              $scope.editando = false;
              cargarCitas();
            })
            .catch(function (err) {
              $scope.error = ErrorHelper.extraer(err, 'Error al guardar los cambios.');
            });
        };
      }
    ]);

})();
