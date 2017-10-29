import com.dslexample.builder.GradleCiJobBuilder
import com.dslexample.builder.GrailsCiJobBuilder

String basePath = 'example7'
List developers = ['dev1@example.com', 'dev2@example.com']

folder(basePath) {
    description 'This example shows how to create jobs using Job builders.'
}

new GrailsCiJobBuilder()
    .name("$basePath/grails-project1")
    .description('An example using a job builder for a Grails project.')
    .ownerAndProject('myorg/project1')
    .emails(developers)
    .build(this)

new GrailsCiJobBuilder()
    .name("$basePath/grails-project2")
    .description('Another example using a job builder for a Grails project.')
    .ownerAndProject('myorg/project2')
    .emails(developers)
    .build(this)

new GradleCiJobBuilder()
    .name("$basePath/gradle-project1")
    .description('Example job description')
    .ownerAndProject('myorg/project3')
    .tasks('clean test')
    .build(this)