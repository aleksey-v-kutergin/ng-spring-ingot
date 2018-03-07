(function () {
    'use strict';

    angular
        .module('coreModule')
        .factory('userSessionService', function () {
            var service = {};
            service.SESSION_TOKEN_KEY = 'NG_INGOT_USER_TOKEN_KEY';

            service.isSessionValid = function () {
              return !!sessionStorage.getItem(service.SESSION_TOKEN_KEY);
            };

            return service;
        });

})();