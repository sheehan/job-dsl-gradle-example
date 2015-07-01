package com.dslexample.rest

import javaposse.jobdsl.dsl.DslScriptLoader

String pattern = System.getProperty('pattern')
String baseUrl = System.getProperty('baseUrl')
String username = System.getProperty('username')
String password = System.getProperty('password') // password or token

if (!pattern || !baseUrl) {
    println 'usage: -Dpattern=<pattern> -DbaseUrl=<baseUrl> [-Dusername=<username>] [-Dpassword=<password>]'
    System.exit 1
}

RestApiJobManagement jm = new RestApiJobManagement(baseUrl)
if (username && password) {
    jm.setCredentials username, password
}

new FileNameFinder().getFileNames('.', pattern).each { String fileName ->
    println "\nprocessing file: $fileName"
    File file = new File(fileName)
    DslScriptLoader.runDslEngine(file.text, jm)
}
