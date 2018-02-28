(function () {
    'use strict';

    angular
        .module('errorModule')
        .directive('errorContent', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/error/content/error-content.template.html',
                controller: 'errorContentController',
                replace: false
            }
        });

})();