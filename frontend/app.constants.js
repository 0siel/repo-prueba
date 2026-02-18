(function () {
  'use strict';

  angular.module('patitasApp')
    .constant('API', {
      CITAS: '/api/citas',
      LOGIN: '/auth/login'
    })
    .constant('ESTADOS', {
      PENDIENTE: 'PENDIENTE',
      ATENDIDA:  'ATENDIDA',
      CANCELADA: 'CANCELADA'
    });

})();
