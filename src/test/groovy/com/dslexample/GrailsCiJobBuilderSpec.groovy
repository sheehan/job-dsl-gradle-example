package com.dslexample

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobParent
import spock.lang.Specification

@Mixin(JobSpecMixin)
class GrailsCiJobBuilderSpec extends Specification {

    JobParent jobParent = createJobParent()
    GrailsCiJobBuilder builder

    def setup() {
        builder = new GrailsCiJobBuilder(
            name: 'test-job',
            description: 'testing',
            ownerAndProject: 'sheehan/example'
        )
    }

    void 'test XML output'() {
        when:
        Job job = builder.build(jobParent)

        then:
        job.name == builder.name
        with(job.node) {
            name() == 'project'
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '*/5 * * * *'
            !publishers.'hudson.tasks.Mailer'.recipients
        }
    }

    void 'test XML output - with overrides'() {
        given:
        builder.emails = ['test1', 'test2']
        builder.pollScmSchedule = '@weekly'

        when:
        Job job = builder.build(jobParent)

        then:
        job.name == builder.name
        with(job.node) {
            name() == 'project'
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '@weekly'
            publishers.'hudson.tasks.Mailer'.recipients.text() == 'test1 test2'
        }
    }
}
