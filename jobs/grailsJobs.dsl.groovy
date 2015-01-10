import com.dslexample.GrailsCiJobBuilder
import javaposse.jobdsl.dsl.DslFactory

job {
    name 'example-job'
    description 'An example for the Gradle project.'
}

job {
    name 'another-example-job'
    description 'An example for the Gradle project.'
}

new GrailsCiJobBuilder(
    name: 'example-grails-job',
    description: 'An example using a job builder for a Grails project.',
    gitUrl: 'git@github.com:example/example.git'
).build(this as DslFactory)

new GrailsCiJobBuilder(
    name: 'another-example-grails-job',
    description: 'Another example using a job builder for a Grails project.',
    gitUrl: 'git@github.com:example/example2.git'
).build(this as DslFactory)