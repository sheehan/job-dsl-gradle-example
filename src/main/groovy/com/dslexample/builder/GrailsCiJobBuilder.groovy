package com.dslexample.builder

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Example Class for creating a Grails build
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GrailsCiJobBuilder {

    String name
    String description
    String ownerAndProject
    String gitBranch = 'master'
    String pollScmSchedule = '*/5 * * * *'
    List<String> emails

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description description
            logRotator {
                numToKeep 5
            }
            scm {
                github ownerAndProject, gitBranch
            }
            triggers {
                scm pollScmSchedule
            }
            steps {
                shell 'chmod a+x ./grailsw'
                grails {
                    target 'test-app'
                    target 'war'
                    grailsWorkDir './grails-work'
                    projectWorkDir './project-work'
                    projectBaseDir ''
                    serverPort ''
                    useWrapper true
                }
            }
            publishers {
                archiveArtifacts 'target/**/*.war'
                archiveJunit 'target/test-reports/**/*Spec.xml'
                if (emails) {
                    mailer emails.join(' ')
                }
            }
        }
    }
}
