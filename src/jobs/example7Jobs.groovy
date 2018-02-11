import com.dslexample.builder.GradleCiJobBuilder

String basePath = 'example7'
List developers = ['dev1@example.com', 'dev2@example.com']

folder(basePath) {
    description 'This example shows how to create jobs using Job builders.'
}

new GradleCiJobBuilder()
    .name("$basePath/gradle-project1")
    .description('An example using a job builder for a Gradle project.')
    .ownerAndProject('myorg/project1')
    .emails(developers)
    .build(this)

new GradleCiJobBuilder()
    .name("$basePath/gradle-project2")
    .description('Another example using a job builder for a Gradle project.')
    .ownerAndProject('myorg/project2')
    .emails(developers)
    .build(this)