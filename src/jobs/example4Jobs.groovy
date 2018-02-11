String basePath = 'example4'

folder(basePath) {
    description 'This example shows a way to reuse job definitions for jobs that differ only with a few properties.'
}

[
    [repo: 'repo1', email: 'me@example.com'],
    [repo: 'repo2', email: 'you@example.com'],
    [repo: 'repo3'],
].each { Map config ->

    job("$basePath/ci-${config.repo}") {
        description "Main job for ${config.repo}"

        logRotator {
            numToKeep 5
        }
        triggers {
            scm 'H/5 * * * *'
        }
        steps {
            gradle 'assemble'
        }
        publishers {
            if (config.email) {
                extendedEmail {
                    recipientList config.email
                }
            }
        }
    }
}