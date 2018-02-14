(function () {
    'use strict';

    angular
        .module('homeModule', [
                'headerBarModule',
                'sideBarModule',
                'footerBarModule',
                'userGrid'
        ])
        .controller('homeController', function ($scope, $http) {

        });

})();