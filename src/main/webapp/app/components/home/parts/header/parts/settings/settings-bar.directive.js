(function () {
    'use strict';

    angular
        .module('homeModule')
        .directive('settingsBar', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/home/parts/header/parts/settings/settings-bar.template.html',
                replace: false
            }
        });
})();