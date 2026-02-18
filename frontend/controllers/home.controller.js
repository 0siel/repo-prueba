(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('HomeController', ['$scope', function ($scope) {
      $scope.nombreClinica = 'Veterinaria Patitas';
      $scope.mensaje = 'Cuidamos a tu mascota como parte de nuestra familia';

      $scope.servicios = [
        { nombre: 'Consulta General',  descripcion: 'Revisión completa del estado de salud de tu mascota con nuestros veterinarios.' },
        { nombre: 'Vacunación',        descripcion: 'Esquemas de vacunación para perros y gatos. Protege a tu compañero.' },
        { nombre: 'Cirugía',           descripcion: 'Procedimientos quirúrgicos con equipo moderno y personal capacitado.' },
        { nombre: 'Urgencias',         descripcion: 'Atención de emergencias las 24 horas, los 7 días de la semana.' },
        { nombre: 'Estética Canina',   descripcion: 'Baño, corte de pelo y cuidado integral para que tu mascota luzca genial.' },
        { nombre: 'Laboratorio',       descripcion: 'Análisis clínicos y estudios de diagnóstico para un cuidado preciso.' }
      ];
    }]);

})();
