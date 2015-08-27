package com.dslexample

import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests that all dsl scripts in the jobs directory will compile.
 */
class JobScriptsSpec extends Specification {

    @Unroll
    void 'test script #file.name'(File file) {
        given:
        JobManagement jm = new MemoryJobManagement()
        new File('resources').eachFileRecurse(FileType.FILES) {
            jm.availableFiles[it.path.replaceAll('\\\\', '/')] = it.text
        }

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
            if (it.name.endsWith('.groovy')) {
                files << it
            }
        }
        files
    }
}

