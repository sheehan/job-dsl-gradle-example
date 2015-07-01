[![Build Status](https://buildhive.cloudbees.com/job/sheehan/job/job-dsl-gradle-example/badge/icon)](https://buildhive.cloudbees.com/job/sheehan/job/job-dsl-gradle-example/)

# Jenkins Job DSL Gradle Example 

An example [Job DSL](https://github.com/jenkinsci/job-dsl-plugin) project that uses Gradle for building and testing.

## File structure

    .
    ├── jobs              # DSL script files
    ├── src
    │   └── main
    │       └── groovy    # support classes
    │   └── test
    │       └── groovy    # specs
    └── build.gradle      # build file

## Testing

* `./gradlew test` runs the specs.

[JobScriptsSpec](https://github.com/sheehan/job-dsl-gradle-example/blob/master/src/test/groovy/com/dslexample/JobScriptsSpec.groovy) 
will loop through all DSL files and make sure they don't throw any exceptions when processed.

## Seed job

You can create the example seed job via the Rest API Runner (see below) using the pattern `jobs/seed.groovy`.

Or manually create a job with the same structure:

* Invoke Gradle script → Use Gradle Wrapper: `true`
* Invoke Gradle script → Tasks: `clean test`
* Process Job DSLs → DSL Scripts: `jobs/**/*Jobs.groovy`
* Process Job DSLs → Additional classpath: `src/main/groovy`
* Publish JUnit test result report → Test report XMLs: `build/test-results/**/*.xml`

## Rest API Runner

A gradle task is configured that can be used to create/update jobs via the Jenkins REST API, if desired. Normally
a seed job is used to keep jobs in sync with the DSL, but this runner might be useful if you'd rather process the
DSL outside of the Jenkins environment or if you want to create the seed job from a DSL script.

```./gradlew rest -Dpattern=<pattern> -DbaseUrl=<baseUrl> [-Dusername=<username>] [-Dpassword=<password>]```

* `pattern` - ant-style path pattern of files to include
* `baseUrl` - base URL of Jenkins server
* `username` - Jenkins username, if secured
* `password` - Jenkins password or token, if secured