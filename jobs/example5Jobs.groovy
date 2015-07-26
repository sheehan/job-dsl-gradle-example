import com.dslexample.StepsUtil

String basePath = 'example5'

folder(basePath) {
    description 'This example shows how to pull out common components into static methods.'
}

job("$basePath/static-method-example1") {

    steps {
        gradle {
            useWrapper true
            tasks 'clean test'
            switches '''
                -Dhttp.proxyHost=xxx
                -Dhttps.proxyHost=xxx
                -Dhttp.proxyPort=xxx
                -Dhttps.proxyPort=xxx
            '''
        }

    }
}

job("$basePath/static-method-example2") {

    steps {
        StepsUtil.proxiedGradle delegate, 'clean test'
    }
}

