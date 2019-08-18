@echo on
echo Hello this is a test batch file
pause

jjtree PascalSpim.jjt
pause
javacc PascalSpim.jj
pause
javac *.java
