(function () {
    'use strict';

    angular
        .module('ngSpringIngot', [
            'ui.router',
            'ngMaterial',
            'ngMdIcons',
            'homeModule'
        ])
        .config(function ($httpProvider,
                          $locationProvider,
                          $stateProvider,
                          $urlRouterProvider) {
            
            // Html5 mode превращает angularjs routes из вида example.com/#!/home в вид example.com/home
            // (все href ссылки должны также указывать на url без hashbang).
            $locationProvider.html5Mode(true);
            $locationProvider.hashPrefix('!');

            $urlRouterProvider.otherwise('/home');
            $stateProvider
                .state('home', {
                    url: '/home',
                    views: {
                        'main': {
                            templateUrl: 'app/components/home/home.template.html',
                            controller: 'homeController'
                        },
                        'navigation': {
                            templateUrl: 'app/components/home/home.template.html'
                        }
                    }
                })
        })
        .controller('AppController', function ($scope) {
            $scope._lang = 'ru';
        });
})();