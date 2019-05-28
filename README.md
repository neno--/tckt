# tckt

**tckt** consists of a client component **tckc** and server component **tcks** built to analyze multi-threaded access to a shared resource.

## Build
`./gradlew bootJar`

### jar locations:
- `/tckt/tcktc/build/libs/tcktc.jar`
- `/tckt/tckts/build/libs/tckts.jar`

### conf locations:
- `/tckt/tcktc/build/resources/main/application.yml`
- `/tckt/tckts/build/resources/main/application.yml`

## Test
`./gradlew check`

## Usage

### Client
`java -jar tckc.jar`

### Server
`java -jar tcks.jar`

## License
[MIT](https://choosealicense.com/licenses/mit/)
