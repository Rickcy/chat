VERSION=
NAME=

include .env
.PHONY: build build-and-push push

build:
	docker build --build-arg FILES=${FILES} -t ${NAME}:${VERSION} .

build-and-push: build
	docker push ${NAME}:${VERSION}

push:
	docker push ${NAME}:${VERSION}

docker-run: build
	docker run -p 8080:8080 -t ${NAME}:${VERSION}


