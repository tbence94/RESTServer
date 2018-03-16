# Warehouse - Server

Environment

  - Install Java JDK8 (http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html#javasejdk)
  - Install Maven (https://maven.apache.org/)
  - Install Git (https://git-scm.com/downloads)

Running

  1. Clone the repository: git clone https://github.com/pekmil/RESTServer.git
  2. Run: mvn clean install
  3. Create db.ser file in the project root.
  4. Rename Config.template to Config.java and set the config values.
  5. Start generated JAR file (from the target directory): java -jar RESTServer-1.0-SNAPSHOT-jar-with-dependencies.jar