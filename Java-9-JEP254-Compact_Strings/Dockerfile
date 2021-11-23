FROM i386/openjdk:8u212-alpine
#FROM openjdk:8u242
#FROM openjdk:15.0.2
COPY TransactionsStringMain.java ./
RUN javac TransactionsStringMain.java
ENTRYPOINT ["java", "-d32", "-showversion", "TransactionsStringMain"]
