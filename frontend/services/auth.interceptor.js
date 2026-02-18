(function () {
  'use strict';

  angular.module('patitasApp')
    .factory('AuthInterceptor', ['AuthService', function (AuthService) {
      return {
        request: function (config) {
          var token = AuthService.obtenerToken();
          if (token) {
            config.headers.Authorization = 'Bearer ' + token;
          }
          return config;
        }
      };
    }])
    .config(['$httpProvider', function ($httpProvider) {
      $httpProvider.interceptors.push('AuthInterceptor');
    }]);

})();
