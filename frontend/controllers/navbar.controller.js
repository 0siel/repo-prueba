(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('NavbarController', ['$scope', '$location', 'AuthService',
      function ($scope, $location, AuthService) {
        $scope.estaLogueado = AuthService.estaLogueado;
        $scope.obtenerUsuario = AuthService.obtenerUsuario;

        $scope.cerrarSesion = function () {
          AuthService.cerrarSesion();
          $location.path('/');
        };
      }
    ]);

})();
