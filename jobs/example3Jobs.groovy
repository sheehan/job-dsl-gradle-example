String basePath = 'example3'

folder(basePath) {
    description 'This example shows how to use the configure block.'
}

job("$basePath/configure-block-example") {

    logRotator {
        numToKeep 5
    }

    triggers {
        scm 'H/5 * * * *'
    }

    steps {
        grails 'test war'
    }

    configure { Node project ->
        project / publishers / 'com.cloudbees.jenkins.GitHubCommitNotifier' {
            resultOnFailure 'FAILURE'
        }
    }
}