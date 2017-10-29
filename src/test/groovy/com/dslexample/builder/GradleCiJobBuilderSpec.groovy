package com.dslexample.builder

import com.dslexample.support.MockJobParent
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobParent
import spock.lang.Specification

class GradleCiJobBuilderSpec extends Specification {

    JobParent jobParent = new MockJobParent()
    GradleCiJobBuilder builder

    def setup() {
        builder = new GradleCiJobBuilder()
            .name('test-job')
            .description('testing')
            .ownerAndProject('sheehan/example')
            .tasks('clean test')
    }

    void 'test XML output'() {
        when:
        Job job = builder.build(jobParent)

        then:
        with(job.node) {
            name() == 'project'
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '@daily'
            !publishers.'hudson.tasks.Mailer'.recipients
        }
    }

    void 'test XML output - with overrides'() {
        given:
        builder.emails(['test1', 'test2'])
        builder.pollScmSchedule('@weekly')

        when:
        Job job = builder.build(jobParent)

        then:
        with(job.node) {
            name() == 'project'
            triggers.'hudson.triggers.SCMTrigger'.spec.text() == '@weekly'
            publishers.'hudson.tasks.Mailer'.recipients.text() == 'test1 test2'
        }
    }
}
