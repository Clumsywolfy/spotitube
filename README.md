# Build
mvn clean package && docker build -t org.example/spotitube .

# RUN

docker rm -f spotitube || true && docker run -d -p 8080:8080 -p 4848:4848 --name spotitube org.example/spotitube 