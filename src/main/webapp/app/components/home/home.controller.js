(function () {
    'use strict';

    angular.module('homeModule', [])
        .controller('homeController', function ($scope, $http) {
            $http.get('api/user/all')
                .success(function (data) {
                    debugger;
                    var msg = 'Database users:';
                    data.forEach(function (user) {
                        msg += "\n\t" + JSON.stringify(user);
                    });
                    $scope._message = msg;
                })
                .error(function (error) {
                    console.log(error);
                });
        });

})();