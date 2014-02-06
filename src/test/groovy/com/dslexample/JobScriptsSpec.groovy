package com.dslexample

import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests that all dsl scripts in the jobs directory will compile.
 */
class JobScriptsSpec extends Specification {

    @Unroll
    void 'test script #file.name'(File file) {
        given:
        JobManagement jm = Mock(JobManagement)
        jm.getOutputStream() >> System.out
        jm.getParameters() >> [:]
        jm.createOrUpdateConfig(* _) >> true

        when:
        DslScriptLoader.runDslEngine file.text, jm

        then:
        noExceptionThrown()

        where:
        file << jobFiles
    }

    static List<File> getJobFiles() {
        List<File> files = []
        new File('jobs').eachFileRecurse(FileType.FILES) {
            files << it
        }
        files
    }

}

