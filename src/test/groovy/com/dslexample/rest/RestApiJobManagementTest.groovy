package com.dslexample.rest

import spock.lang.Specification

import static com.dslexample.rest.RestApiJobManagement.getPath

class RestApiJobManagementTest extends Specification {
    def "path for top-level job"() {
        when:
        def path = getPath('myJob', false)

        then:
        path == 'job/myJob'
    }

    def "path for job with relative path"() {
        when:
        def path = getPath('myFolder/mySubFolder/myJob', false)

        then:
        path == 'job/myFolder/job/mySubFolder/job/myJob'
    }

    def "path for job with absolute path"() {
        when:
        def path = getPath('/myFolder/mySubFolder/myJob', false)

        then:
        path == '/job/myFolder/job/mySubFolder/job/myJob'
    }

    def "path for top-level view"() {
        when:
        def path = getPath('myView', true)

        then:
        path == 'view/myView'
    }
}
