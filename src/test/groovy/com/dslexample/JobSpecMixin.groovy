package com.dslexample

import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.dsl.MemoryJobManagement


class JobSpecMixin {

    JobParent createJobParent() {
       createJobParentFor(new MemoryJobManagement())
    }

    JobParent createJobParentFor(JobManagement jm) {
        JobParent jp = new JobParent() {
            @Override
            Object run() {
                return null
            }
        }
        jp.setJm(jm)
        jp
    }
}
