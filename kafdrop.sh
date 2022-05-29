cd ~/code

cd tools
cd kafdrop
ls

java --add-opens=java.base/sun.nio.ch=ALL-UNNAMED \
    -jar kafdrop-3.30.0.jar \
    --kafka.brokerConnect=localhost:9092