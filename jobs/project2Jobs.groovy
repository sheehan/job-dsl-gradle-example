import com.dslexample.GradleCiJobBuilder
import com.dslexample.GrailsCiJobBuilder
import javaposse.jobdsl.dsl.DslFactory

List developers = ['dev3@example.com', 'dev4@example.com']
String gitUrl = 'git@github.com:example/example2.git'

new GradleCiJobBuilder(
        name: 'example-gradle-job',
        description: 'An example using a job builder for a Gradle project.',
        gitUrl: gitUrl,
        tasks: 'clean test',
        mailerRecipients: developers
).build(this as DslFactory)

new GrailsCiJobBuilder(
    name: 'another-example-grails-job',
    description: 'Another example using a job builder for a Grails project.',
    gitUrl: gitUrl
).build(this as DslFactory)