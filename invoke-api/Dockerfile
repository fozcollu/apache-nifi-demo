FROM golang:1.17

WORKDIR /app

COPY . /app

RUN go build -o main /app/.

EXPOSE 8082
## Our start command which kicks off
## our newly created binary executable
CMD ["/app/main"]