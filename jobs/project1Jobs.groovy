import com.dslexample.GradleCiJobBuilder
import com.dslexample.GrailsCiJobBuilder
import javaposse.jobdsl.dsl.DslFactory

List developers = ['dev1@example.com', 'dev2@example.com']
String gitUrl = 'git@github.com:example/example1.git'

job('example-job') {
    description 'An example for the Gradle project.'
}

job('another-example-job') {
    description 'An example for the Gradle project.'
}

new GrailsCiJobBuilder(
    name: 'example-grails-job',
    description: 'An example using a job builder for a Grails project.',
    gitUrl: 'git@github.com:example/example.git'
).build(this as DslFactory)

new GradleCiJobBuilder(
    name: 'yet-another-example-gradle-job',
    description: 'Example job description',
    gitUrl: gitUrl,
    tasks: 'clean test',
    mailerRecipients: developers
).build(this as DslFactory)