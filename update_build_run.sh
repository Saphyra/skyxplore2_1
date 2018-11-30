git pull
mvn clean package
nohup java $* -jar target/skyxplore-1.0-SNAPSHOT.jar > /dev/null &