package com.dslexample

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobParent
import spock.lang.Specification

@Mixin(JobSpecMixin)
class GradleCiJobBuilderSpec extends Specification {

    JobParent jobParent = createJobParent()

    void 'test XML output'() {
        given:
        GradleCiJobBuilder builder = new GradleCiJobBuilder(
            name: 'test-job',
            description: 'testing',
            gitUrl: 'git@github.com:example/example.git',
            tasks: 'clean test',
            mailerRecipients: ['test1', 'test2']
        )

        when:
        Job job = builder.build(jobParent)

        then:
        job.name == builder.name
        with(job.node) {
            name() == 'project'
            description.text() == builder.description
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '@daily'
            builders.'hudson.plugins.gradle.Gradle'.tasks.text() == 'clean test'
            builders.'hudson.plugins.gradle.Gradle'.useWrapper.text() == 'true'
            publishers.'hudson.tasks.ArtifactArchiver'.artifacts.text() == '**/build/libs/*.jar'
            publishers.'hudson.tasks.junit.JUnitResultArchiver'.testResults.text() == '**/build/test-results/*.xml'
            publishers.'hudson.tasks.Mailer'.recipients.text() == 'test1 test2'
            scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.text() == builder.gitUrl
        }
    }

}
