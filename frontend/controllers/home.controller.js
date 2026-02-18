(function () {
  'use strict';

  angular.module('patitasApp')
    .controller('HomeController', ['$scope', function ($scope) {
      $scope.nombreClinica = 'Veterinaria Patitas';
      $scope.mensaje = 'Cuidamos a tu mascota como parte de nuestra familia';

      $scope.servicios = [
        { nombre: 'Consulta General',  descripcion: 'Revision completa del estado de salud de tu mascota con nuestros veterinarios.' },
        { nombre: 'Vacunacion',        descripcion: 'Esquemas de vacunacion para perros y gatos. Protege a tu companero.' },
        { nombre: 'Cirugia',           descripcion: 'Procedimientos quirurgicos con equipo moderno y personal capacitado.' },
        { nombre: 'Urgencias',         descripcion: 'Atencion de emergencias las 24 horas, los 7 dias de la semana.' },
        { nombre: 'Estetica Canina',   descripcion: 'Bano, corte de pelo y cuidado integral para que tu mascota luzca genial.' },
        { nombre: 'Laboratorio',       descripcion: 'Analisis clinicos y estudios de diagnostico para un cuidado preciso.' }
      ];
    }]);

})();
