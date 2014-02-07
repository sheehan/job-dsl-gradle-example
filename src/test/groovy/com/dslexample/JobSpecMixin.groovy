package com.dslexample

import javaposse.jobdsl.dsl.JobParent


class JobSpecMixin {

    JobParent createJobParent() {
        new JobParent() {
            @Override
            Object run() {
                return null
            }
        }
    }
}
