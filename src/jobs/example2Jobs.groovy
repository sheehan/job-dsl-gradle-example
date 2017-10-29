import groovy.json.JsonSlurper

String basePath = 'example2'
String repo = 'sheehan/grails-example'

folder(basePath) {
    description 'This example shows how to create a set of jobs for each github branch, each in its own folder.'
}

URL branchUrl = "https://api.github.com/repos/$repo/branches".toURL()
List branches = new JsonSlurper().parse(branchUrl.newReader())
branches.each { branch ->

    String safeBranchName = branch.name.replaceAll('/', '-')

    folder "$basePath/$safeBranchName"

    job("$basePath/$safeBranchName/grails-example-build") {
        scm {
            github repo, branch.name
        }
        triggers {
            scm 'H/30 * * * *'
        }
        steps {
            grails 'test-app war', true
        }
    }

    job("$basePath/$safeBranchName/grails-example-deploy") {
        parameters {
            stringParam 'host'
        }
        steps {
            shell 'scp war file; restart...'
        }
    }
}