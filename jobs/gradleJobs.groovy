import com.dslexample.GradleCiJobBuilder
import javaposse.jobdsl.dsl.DslFactory

List developers = ['dev3@example.com', 'dev4@example.com']
String gitUrl = 'git@github.com:example/example.git'

new GradleCiJobBuilder(
        name: 'example-gradle-job',
        description: 'An example using a job builder for a Gradle project.',
        gitUrl: 'git@github.com:example/example.git',
        tasks: 'clean test',
        mailerRecipients: ['dev1@example.com']
).build(this as DslFactory)

new GradleCiJobBuilder(
        name: 'another-example-gradle-job',
        description: 'Another example using a job builder for a Gradle project.',
        gitUrl: 'git@github.com:example/example2.git',
        tasks: 'clean test',
        mailerRecipients: ['dev1@example.com', 'dev2@example.com']
).build(this as DslFactory)

[
        [name: 'yet-another-example-gradle-job', description: 'Example job description'],
        [name: 'final-example-gradle-job', description: 'Another job description']
].each {
    String name = it.name
    String description = it.description
    new GradleCiJobBuilder(
            name: "$name",
            description: "$description",
            gitUrl: gitUrl,
            tasks: 'clean test',
            mailerRecipients: developers
    )
}