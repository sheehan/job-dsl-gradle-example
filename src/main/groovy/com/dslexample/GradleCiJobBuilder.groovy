package com.dslexample

import javaposse.jobdsl.dsl.Job

/**
 * Example Class for creating a Gradle build
 */
class GradleCiJobBuilder {

    String name
    String description
    String gitUrl
    String gitBranch = 'master'
    String pollScmSchedule = '@daily'
    String tasks
    String switches
    Boolean useWrapper = true
    String junitResults = '**/build/test-results/*.xml'
    String artifacts = '**/build/libs/*.jar'
    List mailerRecipients = []

    Job build(jobParent) {
        jobParent.job {
            it.name this.name
            it.description this.description
            logRotator(-1, 5, -1, -1)
            scm {
                git this.gitUrl, gitBranch
            }
            triggers {
                scm pollScmSchedule
            }
            steps {
                gradle(tasks, switches, useWrapper)
            }
            publishers {
                archiveArtifacts artifacts
                archiveJunit junitResults
                mailer(mailerRecipients.join(' '), false, true)
            }
        }
    }
}
