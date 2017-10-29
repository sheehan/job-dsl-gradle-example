package com.dslexample.builder

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Example Class for creating a Gradle build
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GradleCiJobBuilder {

    String name
    String description
    String ownerAndProject
    String gitBranch = 'master'
    String pollScmSchedule = '@daily'
    String tasks
    String switches
    Boolean useWrapper = true
    String junitResults = '**/build/test-results/*.xml'
    String artifacts = '**/build/libs/*.jar'
    List<String> emails = []

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            logRotator {
                numToKeep 5
            }
            scm {
                github this.ownerAndProject, gitBranch
            }
            triggers {
                scm pollScmSchedule
            }
            steps {
                gradle tasks, switches, useWrapper
            }
            publishers {
                archiveArtifacts artifacts
                archiveJunit junitResults
                if (emails) {
                    mailer emails.join(' ')
                }
            }
        }
    }
}
