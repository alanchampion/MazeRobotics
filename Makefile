SRC := $(wildcard src/*.java)
OUT := bin
CLASSES := $(wildcard $(OUT)/*.class)

LIST := $(SRC:src/%.java=$(OUT)/%.class)

all:
	javac -d $(OUT) src/*.java

clean: 
	rm $(OUT)/*.class