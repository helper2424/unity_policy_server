## Установка

### 1. Java
Для работы сервера треубется версия java  1.7 или выше, если на сервере не установлена java, то необходимо установить серверный jre 1.7  или выше.

#### Важно, не используйте Open JDK, используйте только Oracle sun jre

Инструкция как устанавливать jre на linux http://ru.wikihow.com/%D1%83%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%B8%D1%82%D1%8C-Oracle-Java-JRE-%D0%BD%D0%B0-Ubuntu-Linux (! при установке качайте server  jre (подобная ссылка http://www.oracle.com/technetwork/java/javase/downloads/server-jre7-downloads-1931105.html ) )

`java -version`

Должно выдавать что-то подобное

`java version "1.8.0_11"`  
`Java(TM) SE Runtime Environment (build 1.8.0_11-b12)`  
`Java HotSpot(TM) 64-Bit Server VM (build 25.11-b03, mixed mode)`

### 2. Клонирование проекта

Склонируйте проектв в удобную для вас директорию

`cd ~`  
`git clone https://github.com/helper2424/unity_policy_server.git`  

### 3. Логирование

По-умолчанию логи пишутся в файл `/var/log/unity_policy_server/policy.log` , поэтому необходимо создать каталог `/var/log/unity_policy_server` :

`mkdir -p `/var/log/unity_policy_server` 

### 4. Проверка работоспособности

Cервер использует порт 843, для того, чтобы его занять необходимы привилегии суперпользователя. Для проверки работоспособности сервера необходимо выполнить следующие команды:

`cd unity_policy_server/target`  
`sudo java -jar unity_policy_server-1.0-SNAPSHOT-jar-with-dependencies.jar`

После чего демон должен запуститься, для проверки того что сервер работает в другой консоли надо выполнить команду

`nc 127.0.0.1 843`

Если сервер работает корректно, то результатом работы последенй команды будет следующее:

`<?xml version="1.0"?>
<cross-domain-policy>
  <allow-access-from domain="*" to-ports="*"/>
</cross-domain-policy>`

## Сборка кастомной версии 

Установка unity policy сервера.
1. Установить java 1.7 или выше http://www.freetechie.com/blog/installing-oracle-sun-java-jrejdk-1-7-update-7-on-opensuse-12-2-x86_64/
2. Установить maven http://maven.apache.org/download.cgi (не ниже 2)
3. Склонировать себе репозиторий:

$ cd unity_policy_server

4. Необходимо скомпилировать jar файл:

$ mvn  clean compile assembly:single

5. Запустить jar

$ cd target
$ sudo java -jar unity_policy_server-1.0-SNAPSHOT-jar-with-dependencies.jar

* сервер использует порт 843, для того, чтобы его занять необходимы привилегии суперпользователя

6*. Для запуска на ubuntu/debian/opensuse/suse можно использовать скрипт для запуска unity_policy_server в корне каталога.
Для его работы понадобится утилита sudo. В файле необходимо заменить:
JAR - путь до сгенерированного в 4 пункте jar архиве

скрипт скопировать в /etc/init.d/

`$ cd ..
$ sudo cp unity_policy_server /etc/init.d/unity_policy_server
$ cd /etc/init.d
$ sudo chown root:root unity_policy_server
$ sudo chmod +x unity_policy_server
$ sudo service unity_policy_server start`
