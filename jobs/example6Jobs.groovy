String basePath = 'example6'

folder(basePath) {
    description 'This example shows how to include script resources from the workspace.'
}

job("$basePath/resources-example") {

    steps {
        shell readFileFromWorkspace('resources/gruntTest.sh')
    }

    publishers {
        groovyPostBuild readFileFromWorkspace('resources/example.groovy')
    }
}

