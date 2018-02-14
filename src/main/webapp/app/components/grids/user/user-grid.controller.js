(function () {
    'use strict';
    
    angular
        .module('userGrid')
        .controller('userGridController', function ($scope, userService) {
            debugger;
            var gridApi = null;
            $scope.columns = [
                { field: 'id',                name: 'id',                 displayName: 'Идентификатор', type: 'number', visible: false },
                { field: 'name',              name: 'name',               displayName: 'ФИО' },
                { field: 'email',             name: 'email',              displayName: 'Электронная почта' },
                { field: 'roleType',          name: 'roleType',           displayName: 'Роль' },
                { field: 'registrationDate',  name: 'registrationDate',   displayName: 'Дата регистрации' },
                { field: 'isPasswordExpired', name: 'isPasswordExpired',  displayName: 'Просрочен ли пароль' }
            ];
            $scope.grid = {
                columnDefs: $scope.columns,
                enableRowSelection: true,
                enableRowHeaderSelection: false,
                multiSelect: false,
                enableHorizontalScrollbar: 0,
                enableVerticalScrollbar: 0,
                onRegisterApi: function (api) {
                    // Чтобы было проще обращаться к апи грида
                    gridApi = api;
                }
            };

            var fetchUsers = function () {
                userService.getAllUsers().then(function (response) {
                    $scope.grid.data = response.data;
                }, function (error) {
                    console.log(error);
                });
            };

            fetchUsers();
        });
    
})();