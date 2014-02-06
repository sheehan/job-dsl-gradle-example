# Jenkins Job DSL Gradle Example

An example [Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project that uses Gradle for building and testing.

## File structure

    .
    ├── gradle            # wrapper
    ├── jobs              # DSL script files
    ├── src
    │   └── main
    │       └── groovy    # support classes
    │   └── test
    │       └── groovy    # specs
    ├── build.gradle      # build file
    ├── gradlew           # wrapper
    └── gradlew.bat       # wrapper

## Commands

* `./gradlew test` runs the specs.
* `./gradlew workspace` copies src files and DSL scripts to `build/workspace`.

## Seed job configuration settings

* Invoke Gradle script → Use Gradle Wrapper: `true`
* Invoke Gradle script → Tasks: `clean test workspace`
* Process Job DSLs → DSL Scripts: `build/workspace/*.groovy`
* Publish JUnit test result report → Test report XMLs: `build/test-results/**/*.xml`
