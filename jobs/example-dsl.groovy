import com.dslexample.GrailsCiJobBuilder

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
    description: 'An example using a job builder.',
    gitUrl: 'git@github.com:example/example.git'
).build(this)

new GrailsCiJobBuilder(
    name: 'another-example-grails-job',
    description: 'Another example using a job builder.',
    gitUrl: 'git@github.com:example/example2.git'
).build(this)