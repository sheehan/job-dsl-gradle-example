package com.dslexample.rest

import spock.lang.Specification

import static com.dslexample.rest.RestApiJobManagement.getPath

class RestApiJobManagementSpec extends Specification {

    void 'getPath'(String name, boolean isView, String expected) {
        expect:
        getPath(name, isView) == expected

        where:
        name                          | isView || expected
        'myJob'                       | false  || 'job/myJob'
        'myFolder/mySubFolder/myJob'  | false  || 'job/myFolder/job/mySubFolder/job/myJob'
        '/myFolder/mySubFolder/myJob' | false  || '/job/myFolder/job/mySubFolder/job/myJob'
        'myView'                      | true   || 'view/myView'
        '/myView'                     | true   || '/view/myView'
    }
}
