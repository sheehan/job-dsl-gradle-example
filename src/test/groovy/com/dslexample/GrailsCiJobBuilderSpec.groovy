package com.dslexample

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobParent
import spock.lang.Specification

@Mixin(JobSpecMixin)
class GrailsCiJobBuilderSpec extends Specification {

    JobParent jobParent = createJobParent()

    void 'test XML output'() {
        given:
        GrailsCiJobBuilder builder = new GrailsCiJobBuilder(
            name: 'test-job',
            description: 'testing',
            gitUrl: 'git@github.com:example/example.git'
        )

        when:
        Job job = builder.build(jobParent)

        then:
        job.name == builder.name
        with(job.node) {
            name() == 'project'
            description.text() == builder.description
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '*/5 * * * *'
            builders.'hudson.tasks.Shell'.command.text() == 'chmod a+x ./grailsw'
            builders.'com.g2one.hudson.grails.GrailsBuilder'.targets.text() == 'test-app war'
            publishers.'hudson.tasks.ArtifactArchiver'.artifacts.text() == 'target/**/*.war'
            publishers.'hudson.tasks.junit.JUnitResultArchiver'.testResults.text() == 'target/test-reports/**/*Spec.xml'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == builder.gitUrl
        }
    }

}
