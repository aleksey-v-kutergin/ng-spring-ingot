(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('headerToolbar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/toolbar/header-toolbar.template.html',
                replace: false
            }
        });

})();