(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('footerBar', function () {
           return {
               restrict: 'E',
               templateUrl: 'app/components/home/parts/footer/footer-bar.template.html',
               replace: false
           }
        });

})();