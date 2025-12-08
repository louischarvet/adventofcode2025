NAME="JunctionBoxesConnector"

${NAME}: all

all: clean compile run

clean:
	mvn clean

compile:
	mvn compile

run:
	mvn javafx:run -Djavafx.args="$(ARG)"

.PHONY: all clean compile