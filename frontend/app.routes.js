(function () {
  'use strict';

  angular.module('patitasApp')
    .config(['$routeProvider', function ($routeProvider) {
      $routeProvider
        .when('/', {
          templateUrl: 'views/home.html',
          controller: 'HomeController'
        })
        .when('/agendar', {
          templateUrl: 'views/cita.html',
          controller: 'CitaController'
        })
        .when('/consultar', {
          templateUrl: 'views/consultar.html',
          controller: 'ConsultarController'
        })
        .when('/login', {
          templateUrl: 'views/login.html',
          controller: 'LoginController'
        })
        .when('/dashboard', {
          templateUrl: 'views/dashboard.html',
          controller: 'DashboardController'
        })
        .otherwise({ redirectTo: '/' });
    }]);

})();
