(function () {
  'use strict';

  angular.module('patitasApp')
    .factory('EstadoHelper', ['ESTADOS', function (ESTADOS) {
      var classMap = {};
      classMap[ESTADOS.PENDIENTE] = 'badge-pendiente';
      classMap[ESTADOS.ATENDIDA]  = 'badge-atendida';
      classMap[ESTADOS.CANCELADA] = 'badge-cancelada';

      return {
        badgeClass: function (estado) {
          return classMap[estado] || '';
        }
      };
    }]);

})();
