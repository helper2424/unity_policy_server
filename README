Установка unity policy сервера.
1. Установить java 1.7 или выше http://www.freetechie.com/blog/installing-oracle-sun-java-jrejdk-1-7-update-7-on-opensuse-12-2-x86_64/
2. Установить maven http://maven.apache.org/download.cgi (не ниже 2)
3. Склонировать себе репозиторий:

$ git clone git@dev.x01d.com:helper2424/unity_policy_server.git
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

$ cd ..
$ sudo cp unity_policy_server /etc/init.d/unity_policy_server
$ cd /etc/init.d
$ sudo chown root:root unity_policy_server
$ sudo chmod +x unity_policy_server
$ sudo service unity_policy_server start