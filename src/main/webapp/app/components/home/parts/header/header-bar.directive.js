(function () {
    'use strict';

    angular
        .module('headerBarModule', [])
        .directive('headerBar', function () {
           return {
               restrict: 'E',
               templateUrl: 'app/components/home/parts/header/header-bar.template.html',
               replace: false
           }
        });

})();