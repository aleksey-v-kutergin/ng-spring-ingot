(function () {
    'use strict';

    angular
        .module('coreModule', [])
        .service('userService', function ($http) {
            this.apiUrlRoot = 'api';

            this.getAllUsers = function () {
                return $http.get(this.apiUrlRoot + '/user/all');
            }
            
        });

})();