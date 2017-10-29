package com.dslexample.support

import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.dsl.MemoryJobManagement

class MockJobParent extends JobParent {

    MockJobParent() {
        setJm new MemoryJobManagement()
    }

    @Override
    Object run() {
        return null
    }
}
