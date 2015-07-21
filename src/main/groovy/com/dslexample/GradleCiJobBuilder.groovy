package com.dslexample

import javaposse.jobdsl.dsl.DslFactory
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

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            logRotator {
                numToKeep 5
            }
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
