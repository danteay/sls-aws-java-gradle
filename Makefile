
install:
	@npm install
.PHONY: install

build:
	@./gradlew clean build
.PHONY: build

test:
	@./gradlew test
.PHONY: test

run: build
	@./node_modules/.bin/sls offline start
.PHONY: build