package com.dslexample

import com.dslexample.support.TestUtil
import groovy.io.FileType
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import javaposse.jobdsl.dsl.helpers.ScmContext
import javaposse.jobdsl.dsl.helpers.triggers.TriggerContext
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * This is an alternative to JobScriptsSpec for testing scripts. Any usage of auto-generated DSL must by stubbed.
 * The upside is it's faster to run and easier to set up. The downside is that you won't catch any syntax errors within
 * calls to auto-generated DSL methods.
 */
class JobScriptsSpecAlternative extends Specification {

    MemoryJobManagement jobManagement = Spy(MemoryJobManagement)

    @Shared
    private File outputDir = new File('./build/debug-xml')

    def setupSpec() {
        outputDir.deleteDir()
    }

    def setup() {
        stubGeneratedDslCall TriggerContext, 'githubPullRequest'
        stubGeneratedDslCall ScmContext, 'cvsscm'

        new File('src/scripts').eachFileRecurse(FileType.FILES) {
            String path = it.path.replaceAll('\\\\', '/')
            jobManagement.availableFiles[path] = it.text
        }
    }

    @Unroll
    void 'test script #file.name'(File file) {
        when:
        new DslScriptLoader(jobManagement).runScript(file.text)
        writeItems(outputDir)

        then:
        noExceptionThrown()

        where:
        file << TestUtil.getJobFiles()
    }

    private void stubGeneratedDslCall(Class contextType, String methodName) {
        _ * jobManagement.callExtension(methodName, _, contextType, *_) >> JobManagement.NO_VALUE
    }

    /**
     * Write the config.xml for each generated job and view to the build dir.
     */
    private void writeItems(File outputDir) {
        jobManagement.savedConfigs.each { String name, String xml ->
            TestUtil.writeFile(new File(outputDir, 'jobs'), name, xml)
        }
        jobManagement.savedViews.each { String name, String xml ->
            TestUtil.writeFile(new File(outputDir, 'views'), name, xml)
        }
    }
}