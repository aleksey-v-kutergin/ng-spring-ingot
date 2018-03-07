(function () {
    'use strict';

    angular
        .module('ngSpringIngot', [
                'ui.router',
                'coreModule',
                'authModule',
                'userModule',
                'appNavigationModule',
                'homeModule',
                'geoModule',
                'analyticsModule',
                'errorModule'
        ])
        .config(function ($httpProvider,
                          $locationProvider,
                          $stateProvider,
                          $urlRouterProvider) {
            
            // Html5 mode превращает angularjs routes из вида example.com/#!/home в вид example.com/home
            // (все href ссылки должны также указывать на url без hashbang).
            $locationProvider.html5Mode(true);
            $locationProvider.hashPrefix('!');

            $urlRouterProvider.otherwise('/login');
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
                }).state('login', {
                    url: '/login',
                    views: {
                        'main': {
                            templateUrl: 'app/components/auth/login/page/login-page.template.html',
                            controller: 'loginPageController'
                        },
                        'navigation': {
                            templateUrl: 'app/components/auth/login/page/login-page.template.html'
                        }
                    }
                })
        })
        .controller('AppController', function ($scope) {
            $scope._lang = 'ru';
        });
})();