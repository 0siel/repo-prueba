(function () {
  'use strict';

  angular.module('patitasApp')
    .factory('AuthService', [function () {
      var TOKEN_KEY = 'jwt_token';
      var USER_KEY  = 'username';

      return {
        guardarToken: function (token, username) {
          localStorage.setItem(TOKEN_KEY, token);
          localStorage.setItem(USER_KEY, username);
        },
        obtenerToken: function () {
          return localStorage.getItem(TOKEN_KEY);
        },
        obtenerUsuario: function () {
          return localStorage.getItem(USER_KEY);
        },
        estaLogueado: function () {
          return !!localStorage.getItem(TOKEN_KEY);
        },
        cerrarSesion: function () {
          localStorage.removeItem(TOKEN_KEY);
          localStorage.removeItem(USER_KEY);
        }
      };
    }]);

})();
