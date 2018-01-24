(function () {
    'use strict';

    angular.module('homeModule', [])
        .controller('HomeController', function ($scope, $http) {
            $http.get('api/hello')
                .success(function (data) {
                    $scope._helloMessage = data;
                })
                .error(function (error) {
                    console.log(error);
                });
        });

})();