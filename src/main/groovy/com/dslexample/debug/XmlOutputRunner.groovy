package com.dslexample.debug

import groovy.io.FileType
import javaposse.jobdsl.dsl.*

/**
 * Runs the DSL engine and writes the XML output to files.
 */

String pattern = System.getProperty('pattern')

if (!pattern) {
    println 'usage: -Dpattern=<pattern>'
    System.exit 1
}

File outputDir = new File('./build/debug-xml')
outputDir.deleteDir()

MemoryJobManagement jm = new MemoryJobManagement() {
    @Override
    Node callExtension(String name, Item item, Class<? extends ExtensibleContext> contextType, Object... args) {
        if (name == 'githubPullRequest') {
            println "Warning githubPullRequest call ignored locally"
            return NO_VALUE
        }
        super.callExtension(name, item, contextType, args)
    }
}

new File('resources').eachFileRecurse(FileType.FILES) {
    jm.availableFiles[it.path.replaceAll('\\\\', '/')] = it.text
}

println '\nProcessing DSL files:\n'

new FileNameFinder().getFileNames('.', pattern).each { String fileName ->
    println fileName
    DslScriptLoader.runDslEngine new File(fileName).text, jm
}

println '\nWriting XML files:\n'

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

if (jm.savedConfigs) {
    File dir = new File(outputDir, 'jobs')

    jm.savedConfigs.each { String name, String xml ->
        writeFile dir, name, xml
    }
}

if (jm.savedViews) {
    File dir = new File(outputDir, 'views')
    dir.mkdirs()

    jm.savedViews.each { String name, String xml ->
        writeFile dir, name, xml
    }
}
