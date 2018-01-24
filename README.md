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

**Создание проекта**
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
    git NgSpringIngot -f origin master
```
Можно работать дальше :) Я оставляю градле-враппер под контролем версий чтобы приложение всегда можно было собрать без gradle и IDE из консоли.

**Типы зависимостей в грэдле:**
* compile - зависимости, необходимые во время компиляции проекта
* testCompile - зависимости, необходимые для компиляции тестов
* provided - зависимости, необходимые во время компиляции проекта, но не поставляетмы вместе с war (предполагается их наличие внутри web-контейнера)

**Управление зависимостями в gradle**

Основная проблема заключаеться в управлении версиями всефозхможных составляющих Spring так, чтобы одно не конфликтовало с другим, включая транзитивные зависимости.
Я знаю что есть Spring Boot, но существование вэб-приложения в виде исполняемого jar в контексте энтерпрайза не очень. У заказчика на одном томкате может курутится не одно приложение.

Пойдем немного другим путем.
В мавене есть концепция bom-файлов (bill of materials) - это специальный вид pom-файла, который используется для управления версиями зависимостей проекта и предаставляет единый механизм задания и обновления версий зависимостей.

Губо говоря, bom это своего рода родительский pom-файл, который используется для пресета, позволяя кофигурировать:
* Версию java и другие свойства
* Версии ряда зависимостей проекта
* Дефолтная конфигурация плагинов

Собственно, в Spring Boot это выглядит следующим образом:
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.0.RELEASE</version>
    </parent>
```
Раскруиваем цепочку дальше... Помник Spring Boot Dependencies определяет дефолтный менеджмент зависимостей для всех Spring Boot проектов (пример):
```
    <properties>
        <activemq.version>5.13.4</activemq.version>
        ...
        <ehcache.version>2.10.2.2.21</ehcache.version>
        <ehcache3.version>3.1.1</ehcache3.version>
        ...
        <h2.version>1.4.192</h2.version>
        <hamcrest.version>1.3</hamcrest.version>
        <hazelcast.version>3.6.4</hazelcast.version>
        <hibernate.version>5.0.9.Final</hibernate.version>
        <hibernate-validator.version>5.2.4.Final</hibernate-validator.version>
        <hikaricp.version>2.4.7</hikaricp.version>
        <hikaricp-java6.version>2.3.13</hikaricp-java6.version>
        <hornetq.version>2.4.7.Final</hornetq.version>
        <hsqldb.version>2.3.3</hsqldb.version>
        <htmlunit.version>2.21</htmlunit.version>
        <httpasyncclient.version>4.1.2</httpasyncclient.version>
        <httpclient.version>4.5.2</httpclient.version>
        <httpcore.version>4.4.5</httpcore.version>
        <infinispan.version>8.2.2.Final</infinispan.version>
        <jackson.version>2.8.1</jackson.version>
        ....
        <jersey.version>2.23.1</jersey.version>
        <jest.version>2.0.3</jest.version>
        <jetty.version>9.3.11.v20160721</jetty.version>
        <jetty-jsp.version>2.2.0.v201112011158</jetty-jsp.version>
        <spring-security.version>4.1.1.RELEASE</spring-security.version>
        <tomcat.version>8.5.4</tomcat.version>
        <undertow.version>1.3.23.Final</undertow.version>
        <velocity.version>1.7</velocity.version>
        <velocity-tools.version>2.0</velocity-tools.version>
        <webjars-hal-browser.version>9f96c74</webjars-hal-browser.version>
        <webjars-locator.version>0.32</webjars-locator.version>
        <wsdl4j.version>1.6.3</wsdl4j.version>
        <xml-apis.version>1.4.01</xml-apis.version>
    </properties>
```

Как можно заметить из примера выше, Spring Boot Dependencies содержат большую часть зависимостей, необходимых для создания типичного Spring web-проекта. Версия любой зависимости может быть переопределена в помнике проекта.
BOM Spring Boot Parent Starter наследует (объявляет как родительский) от Spring Boot Dependencies, а также пресетить ряд плагинов, версию java, кодировку и другое. Таким образом использование бома Spring Boot Parent Starter позволяет легко определить версии зависимостей.

Схема такова:
1. Заюзать Spring Boot Parent Starter
2. В секции dependencies указываем зависиомти, зависимости входящие в bom, без версий.

Использовать механизм bom-ов в системе сборки gradle позволяет ``dependency-management-plugin``.
 
Применяем плагин:
```
    buildscript {
      repositories {
        jcenter()
      }
      dependencies {
        classpath "io.spring.gradle:dependency-management-plugin:0.5.1.RELEASE"
      }
    }
    
    apply plugin: "io.spring.dependency-management"
```

Импортирем bom:
```
    dependencyManagement {
      imports {
        mavenBom 'io.spring.platform:platform-bom:1.1.1.RELEASE'
      }
    }
```

Профит:
```
    dependencies {
        compile 'org.springframework:spring-core'
    }
```
В чем отличе от Spring Boot plugin: этот плагин тоже позволяет использовать зависимости без версий, но не влияет на транзитивные зависимости.
Начиная с Spring Boot 1.3, вместо pring Boot plugin использует dependency-management-plugin. Для более ранних версий, они могут сосуществовать.

Полезные ссылки по теме:
1. [Better dependency management for Gradle](https://spring.io/blog/2015/02/23/better-dependency-management-for-gradle)
2. [Introduction to Spring Boot Starter Parent](http://www.springboottutorial.com/spring-boot-starter-parent)

**Запуск приложения на томкате:**

Последовательность действий относительно Intellij IDEA:
0. Ставим томкат на локальную машину
1. Идем Run -> Edit Configurations... -> + -> Tomcat Server -> Local -> Виззард открывается
2. Здаем путь до томката, если сам не подхватился \ порт \ версию JRE
3. Идем на владку Deployment и добавляем артефакт (идея сама предложит через кнопку Fix). Нужно выбрать war (exploded). Типа развернутый.
4. Вовращаемся на вкладку Server и в поляк On Update Action \ On Frame deactivation выбрать Update classes and resources
5. Ну и на before build должен быть задан гредловый такск сборки проекта

Пункты 3.,4. активируют так называемый режим хот свапа, при котором фронт и часть бека (контроллеры) редеплоятся автоматом при измении их кода. В купе с watch ангуляра и отладчиком javascript идеи, это все позволяет организовать очень эффективную работу.
Томкат приходится перезапускать только при измениях в сервисах и доменном слое, БД. Это все работает только в случае с exploded варником. 


**Как добавить AngularJS в проект**
Почему AngularJS - его проще засунуть в проект чтоб сейчас не тратить сейчас много времени на настройку. По AngularJS 2.0 сделаю отдельный проект.  
Можно пойти простым путем... Заюзать ng-boilerplate. Но он сконфигурирован на все случаи жизни и тянет за собой очень много лишнего.
Можно его конечно проредить, но это не просто. Проще добавить все минимально необходимое самому:
1. На машине нужно установить node.js (npm ставится с ним)
2. Ставим менеджер зависимостей bower: npm install bower (Вроде бы нынче нужно мигрировать на Yarn)
3. Добавляем в проект package.json - в нем будут описаны всякие зависимости для разработки + команды запуска
4. Добавляем в проект файл bower.json - внем описваются чисто зависимости фронта и .bowerrc.json - настроки самого боувера (в нем также если что надо задавать настроки прокси). 
5. Завсисимости фронта будут жить в папке webapp\vendor - она должна быть в .gitignore, чтоб не индексировалось
6. Само ангуляр приложение в папке webapp\app
7. Статика (css, fonts, img, js - напрмер скрипты, необходимые для работы дизайна): webapp\assets\css|fonts|img|js

Больше особо и ничего не нужно. Есть еще много чего: юнит тесты для фронта (karma), интеграционное тестирование... но пока не до этого.
Поставить все можно выполнив npm install.
