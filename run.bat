set classpath=lib/jfreechart-1.0.13-swt.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13.jar;lib/jcommon-1.0.16.jar;lib/guava-18.0.jar;lib/json-simple-1.1.1.jar;lib/Panel.jar;.;
javac -d . *.java
java keywordcover.MainProgram
pause