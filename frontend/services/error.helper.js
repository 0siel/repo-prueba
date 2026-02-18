(function () {
  'use strict';

  angular.module('patitasApp')
    .factory('ErrorHelper', [function () {
      return {
        extraer: function (err, fallback) {
          if (err.data) {
            return err.data.message || err.data.error || fallback;
          }
          return fallback;
        }
      };
    }]);

})();
