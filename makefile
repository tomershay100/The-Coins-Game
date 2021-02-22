compile: bin
	javac -d bin -cp biuoop-1.4.jar src/*.java
jar:
	jar cfm0 Coins.jar MANIFEST.MF -C bin .
run:
	java -jar Coins.jar
bin:
	mkdir bin