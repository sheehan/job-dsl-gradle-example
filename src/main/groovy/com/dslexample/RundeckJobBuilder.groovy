package com.dslexample

import com.dslexample.generateddsl.RundeckNotificationConfiguration
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Example Class for creating a rundeck call
 */
class RundeckJobBuilder {
    String name
    String description
    String rundeckTask
    Map<String, String> rundeckOptions

    Job build(DslFactory dslFactory) {
        dslFactory.job(name) {
            it.description this.description
            publishers RundeckNotificationConfiguration.of(rundeckTask, rundeckOptions).getRundeckNotificationDsl()
        }
    }
}
