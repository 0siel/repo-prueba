(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('LoginController', ['$scope', '$http', '$location', 'API', 'AuthService',
      function ($scope, $http, $location, API, AuthService) {
        $scope.credenciales = {};
        $scope.enviando = false;
        $scope.error = null;

        if (AuthService.estaLogueado()) {
          $location.path('/dashboard');
        }

        $scope.login = function () {
          $scope.enviando = true;
          $scope.error = null;

          $http.post(API.LOGIN, $scope.credenciales)
            .then(function (res) {
              AuthService.guardarToken(res.data.token, res.data.username);
              $location.path('/dashboard');
            })
            .catch(function (err) {
              $scope.error = (err.status === 401)
                ? 'Usuario o contraseña incorrectos.'
                : 'Error al iniciar sesión. Inténtalo de nuevo.';
            })
            .finally(function () {
              $scope.enviando = false;
            });
        };
      }
    ]);

})();
