# Jenkins Job DSL Plugin Gradle Example

An example Job DSL project that uses Gradle for building and testing.

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

* Build
    * Invoke Gradle script
        * Use Gradle Wrapper: `true`
        * Tasks: `clean test workspace`
    * Process Job DSLs
        * DSL Scripts: `build/workspace/*.groovy`
* Post-build
    * Publish JUnit test result report
        * Test report XMLs: `build/test-results/**/*.xml`