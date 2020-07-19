FROM adoptopenjdk/openjdk11:aarch64-ubuntu-jre11u-nightly

WORKDIR /home

ADD ../notifications/build/libs/realtchecker-notifications.jar realtchecker-notifications.jar

ENTRYPOINT [ "sh", "-c", "exec java $JAVA_OPTS -Duser.timezone=UTC -Dspring.profiles.active=$PROFILE_OPTS -jar realtchecker-notifications.jar"]
