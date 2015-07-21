package com.dslexample

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 *  Example Class for creating a Grails build
 */
class GrailsCiJobBuilder {

    String name
    String description
    String gitUrl

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            logRotator {
                numToKeep 5
            }
            scm {
                git this.gitUrl, 'master'
            }
            triggers {
                scm '*/5 * * * *'
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
            }
        }
    }
}
