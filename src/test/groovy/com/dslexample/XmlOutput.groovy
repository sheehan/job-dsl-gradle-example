package com.dslexample

import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.dsl.GeneratedItems
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.plugin.JenkinsJobManagement
import org.junit.ClassRule
import org.jvnet.hudson.test.JenkinsRule
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Generate config.xml of dsl scripts
 */
class XmlOutput extends Specification {
    @Shared
    @ClassRule
    private JenkinsRule jenkinsRule = new JenkinsRule()

    @Shared
    private File outputDir = new File('./build/debug-xml')
    @Shared
    private File jobXMLDir = new File(outputDir, 'jobs')
    @Shared
    private File viewXMLDir = new File(outputDir, 'views')
    @Shared
    private String jenkinsAbsoluteUrl

    def writeFile(File dir, String name, String xml) {
        List tokens = name.split('/')
        File folderDir = dir
        tokens[0..<tokens.size() - 1].each { String token ->
            folderDir = new File(folderDir, token)
        }
        folderDir.mkdirs()

        File xmlFile = new File(folderDir, "${tokens[-1]}.xml")
        xmlFile.text = xml
        println new File('.').toURI().relativize(xmlFile.toURI()).toString()
    }

    def setupSpec() {
        outputDir.deleteDir()
        jenkinsAbsoluteUrl = jenkinsRule.jenkins.getRootUrl()
    }

    @Unroll
    void 'test script #file.name'(File file) {
        given:
        JobManagement jm = new JenkinsJobManagement(System.out, [:], new File('.'))

        when:
        GeneratedItems items = new DslScriptLoader(jm).runScript(file.text)

        items.jobs.each {
            def jobName = it.jobName
            jenkinsRule.jenkins.getAllItems().findAll {item -> item.getFullName().equals(jobName)}.each {
                writeFile jobXMLDir, jobName, new URL(jenkinsAbsoluteUrl + it.getUrl() + 'config.xml').getText()
            }
        }

        items.views.each {
            def viewName = it.name
            jenkinsRule.jenkins.views.findAll {view -> view.viewName.equals(viewName)}.each {
                writeFile viewXMLDir, viewName, new URL(it.absoluteUrl + 'config.xml').getText()
            }
        }

        then:
        noExceptionThrown()

        where:
        file << jobFiles
    }

    static List<File> getJobFiles() {
        List<File> files = []
        String pattern = System.getProperty('pattern')
        List<String> filePaths = new FileNameFinder().getFileNames('./', pattern)
        filePaths.each {
            files.add(new File(it))
        }
        files
    }
}
