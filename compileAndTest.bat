javac -d .\out *.java -cp .\lib\junit-platform-console-standalone-1.10.0.jar
java -jar .\lib\junit-platform-console-standalone-1.10.0.jar --class-path .\out\ --scan-class-path
cmd /k