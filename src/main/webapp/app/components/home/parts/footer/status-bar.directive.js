(function () {
    'use strict';

    angular
        .module('statusBarModule', [])
        .directive('statusBar', function () {
           return {
               restrict: 'E',
               templateUrl: 'app/components/home/parts/footer/status-bar.template.html',
               replace: false
           }
        });

})();