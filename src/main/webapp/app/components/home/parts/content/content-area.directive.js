(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('contentArea', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/content/content-area.template.html',
                controller: 'contentAreaController',
                replace: false
            }
        })

})();