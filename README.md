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

Склонируйте проект в удобную для вас директорию

`cd ~`  
`git clone https://github.com/helper2424/unity_policy_server.git`  

### 3. Логирование

По-умолчанию логи пишутся в файл `/var/log/unity_policy_server/policy.log` , поэтому необходимо создать каталог `/var/log/unity_policy_server` :

`mkdir -p /var/log/unity_policy_server` 

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

### 5. Init скрипты

В каталоге init.d лежат примеры скриптов для автоматического запуска сервера. Вам необходимо прописать пути до jar файла и перенести необходимый вам скрипт в init.d каталог, пример для opensuse/debian/ubuntu

`cd init.d/opensuse_debian`  
`vi vi unity_policy_server.example`

Устанавливаете путь до jar файла

`sudo cp unity_policy_server.example /etc/init.d/unity_policy_server`
`sudo chmod +x /etc/init.d/unity_policy_server`  
`sudo chown root:root /etc/init.d/unity_policy_server`  

Далее необходимо добавить скрипт каталоги дла автозапуска (пример для дебиан):

`sudo update-rc.d unity_policy_server defaults`

### 6. Проверка

Если все прошло удачно, то на команду

`nc <your_server_ip> 843`  

Будет возвращаться полиси для юнити плагина, если же этого не происходит, то проверьте не занят ли 843 порт на вашем сервере, и открыт ли 843 порт файрволом:

`netstat -tupln | grep 843` - должен показать только нами запущенный сервер  
`nmap <your_server_ip> -Pn -p 843`  - с удаленной машины должен показать что-то вроде  

`Host is up (0.054s latency).`  
`PORT    STATE SERVICE`  
`843/tcp open  unknown`  


## Сборка кастомной версии 

Если вы решили что-то изменить в сервере, то для сборки используйте мавен, команда для сборки

`mvn  clean compile assembly:single`  

Результируюйщий jar архив должен замениться в каталоге  `target`


