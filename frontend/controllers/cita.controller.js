(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('CitaController', ['$scope', '$http', 'API', 'ErrorHelper',
      function ($scope, $http, API, ErrorHelper) {
        $scope.cita = {};
        $scope.enviando = false;
        $scope.exito = null;
        $scope.error = null;

        $scope.enviar = function () {
          $scope.enviando = true;
          $scope.exito = null;
          $scope.error = null;

          var body = {
            nombreCliente:  $scope.cita.nombreCliente,
            nombreMascota:  $scope.cita.nombreMascota,
            numeroTelefono: $scope.cita.numeroTelefono,
            razonCita:      $scope.cita.razonCita,
            fechaCita:      $scope.cita.fechaCita
          };

          $http.post(API.CITAS, body)
            .then(function (res) {
              $scope.exito = '¡Cita agendada con éxito! Tu número de folio es: ' + res.data.id + '. Guárdalo para consultar tu cita.';
              $scope.cita = {};
            })
            .catch(function (err) {
              $scope.error = ErrorHelper.extraer(err, 'Error al agendar la cita. Inténtalo de nuevo.');
            })
            .finally(function () {
              $scope.enviando = false;
            });
        };
      }
    ]);

})();
