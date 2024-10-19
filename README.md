## Client utils

1. Client codegen based on server WSDL (need java-8 for wsimport)
```bash
wsimport -clientjar lib/soap-client.jar http://localhost:35500/YofikWebService.wsdl\?wsdl
```

2. Start client

```bash
./gradlew build && java -jar ./build/libs/web-services-client-1.0-SNAPSHOT.jar
```