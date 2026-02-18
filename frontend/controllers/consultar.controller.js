(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('ConsultarController', ['$scope', '$http', 'API', 'EstadoHelper', 'ErrorHelper',
      function ($scope, $http, API, EstadoHelper, ErrorHelper) {
        $scope.folio = null;
        $scope.cita = null;
        $scope.error = null;
        $scope.badgeClass = EstadoHelper.badgeClass;

        $scope.buscar = function () {
          $scope.cita = null;
          $scope.error = null;

          $http.get(API.CITAS + '/' + $scope.folio)
            .then(function (res) {
              $scope.cita = res.data;
            })
            .catch(function (err) {
              $scope.error = (err.status === 404)
                ? 'No se encontro ninguna cita con el folio ' + $scope.folio + '.'
                : ErrorHelper.extraer(err, 'Error al buscar la cita. Intentalo de nuevo.');
            });
        };
      }
    ]);

})();
