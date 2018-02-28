(function () {
    'use strict';

    angular
        .module('userModule')
        .directive('requestsContent', function () {
            return {
                restrict: 'E',
                templateUrl: 'app/components/user/content/requests/requests-content.template.html',
                controller: 'requestsContentController',
                replace: false
            }
        });

})();