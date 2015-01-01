package com.dslexample

import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.JobParent


class JobSpecMixin {

    JobParent createJobParent() {
        JobParent jp = new JobParent() {
            @Override
            Object run() {
                return null
            }
        }
        JobManagement jm = [
                getPluginVersion: { String pluginShortName -> null },
        ] as JobManagement
        jp.setJm(jm)
        jp
    }
}
