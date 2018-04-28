package ru.rusquant.ngingot.domain.types;

/**
 *    При помощи таких перечислений можно абстрагироваться от названий ролей в БД.
 *    Ну или хотябы локализовать использование строковых констант.
 **/
public enum AuthorityType {
    ROLE_ADMIN,
    ROLE_GEO_FILLER,
    ROLE_ACCOUNT_CHECKER,
}
