# Typical Angular Spring web application setup
  
Данный репозиторий содержит проект-болванку CRUD вэб-приложения в стеке:

Backend:
1. Spring: Boot, MVC, Data, Security 
2. Hibernate
3. H2 Database c диалектом PostgreSQL
4. 

Frontend: 
1. Angular (Первый)
2. Bootstrap
3. Google Maps API v.3
4.

Сборка:
* gradle

Приложение реализует:
1. Базовую парольную анутификацию
2. Простейшую авторизацию на основе ролей
3. Возможность создания объектов на картах Google и сохранения их в базе данных


## Subtle moments of set up

** Создание проекта **
Тут ничего сложного, открываем Intellij IDEA. Выбираем создать новый проект -> Gradle -> Отмечаем java, web.

В мастере важно отметить следующие галочки:
* Use auto-import
* Create directories for empty content roots automatically
* Use default gradle wrapper

После создания проекта помечаем папку src как Sources root, а папку test как Test sources root.

Дальше открываем терминал (``Alt + F12``) и инициализируем git-репозиторий: ``git init``
Через ``Ctrl + Alt + A`` добавляем нужные файлы под контроль версий.

Ну и:
```
git commit -m "Initial commit"
git remote add NgSpringIngot https://github.com/aleksey-v-kutergin/ng-spring-ingot.git
git push NgSpringIngot master
```
Можно работать дальше :) Я оставляю градле-враппер под контролем версий чтобы приложение всегда можно было собрать без gradle и IDE из консоли.