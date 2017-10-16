// If you want, you can define your seed job in the DSL and create it via the REST API.
// See https://github.com/sheehan/job-dsl-gradle-example#rest-api-runner

String basePath = 'job-dsl-gradle-example'

folder(basePath) {
    description 'Examples for Job DSL with Gradle build'
}

job("$basePath/seed") {
    scm {
        github 'sheehan/job-dsl-gradle-example'
    }
    triggers {
        scm 'H/5 * * * *'
    }
    steps {
        gradle 'clean test'
        dsl {
            external 'jobs/**/*Jobs.groovy'
            additionalClasspath 'src/main/groovy'
        }
    }
    publishers {
        archiveJunit 'build/test-results/**/*.xml'
    }
}
