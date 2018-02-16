<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>

<!-- Точка входа в приложение -->
<html lang="ru" ng-app="ngSpringIngot" ng-controller="AppController">

    <head>
        <meta charset="utf-8">
        <base href="/ng-spring-ingot/">
        <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico">
        <title>NgSpringIngot</title>

        <!-- jQuery -->
        <script src="vendor/jquery/dist/jquery.js"></script>
        <script src="vendor/jquery-ui/jquery-ui.min.js"></script>

        <link rel="stylesheet" type="text/css" href="vendor/angular-ui-grid/ui-grid.css">

        <!-- AngularJS  -->
        <script src="vendor/angular/angular.min.js"></script>
        <script src="vendor/angular-ui-router/release/angular-ui-router.min.js"></script>
        <script src="vendor/angular-modal-service/dst/angular-modal-service.min.js"></script>

        <!-- Либы  -->
        <script src="vendor/angular-ui-grid/ui-grid.min.js"></script>


        <!-- Скрипты приложения -->
        <script src="app/app.js"></script>
        <script src="app/components/home/home.controller.js"></script>

        <!-- Хедер и его возможные составляющие -->
        <script src="app/components/home/parts/header/header-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/help/help-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/logout/logout-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/settings/settings-bar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/toolbar/header-toolbar.directive.js"></script>
        <script src="app/components/home/parts/header/parts/user/user-bar.directive.js"></script>

        <!-- Грид пользователей -->
        <script src="app/components/grids/user/user-grid.directive.js"></script>
        <script src="app/components/grids/user/user-grid.controller.js"></script>

        <script src="app/components/home/parts/sidebar/side-bar.directive.js"></script>
        <script src="app/components/home/parts/sidebar/side-bar.controller.js"></script>

        <script src="app/components/home/parts/sidebar/navigation/appmodulelist/app-module-list.directive.js"></script>
        <script src="app/components/home/parts/sidebar/navigation/appmodulelist/app-module-list.controller.js"></script>

        <script src="app/components/home/parts/footer/footer-bar.directive.js"></script>

        <!-- Модуль с сервисами -->
        <script src="app/components/services/user.service.js"></script>
        <script src="app/components/services/app-module.service.js"></script>

        <!-- Дизайн -->
        <script src="assets/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="vendor/angular-ui-grid/ui-grid.css">
        <link rel="stylesheet" type="text/css" href="assets/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="assets/css/main.css">

    </head>

    <body>
        <ui-view ui-i18n="{{_lang}}" name="main"></ui-view>
    </body>

</html>