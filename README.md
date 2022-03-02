# GitHub template for Kotlin course

This template contains everything to make the Practical Work.  

To run the project, you have to open a terminal at the root of the project and run

```shell
./gradlew run
```

To execute all unit test, you have to open a terminal at the root of the project and run
```shell
./gradlew test
```

Reports are located at `build/reports` and test results are located 

This is the template file structure. (Create files or directories if needed)
```text
.
├── README.md
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── kotlin
    │   │   └── fr
    │   │       └── iem
    │   │           └── main.kt
    │   └── resources
    │       └── json
    │            ├── iron_man.json
    │            └── response.json
    └── test
        ├── kotlin
        │   │   │   └── fr
        │   │       └── iem
        │   │           └── ExampleTest.kt 
        └── resources
```
