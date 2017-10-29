import com.dslexample.util.StepsUtil

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
                -Dhttp.proxyHost=proxy.example.com
                -Dhttps.proxyHost=proxy.example.com
                -Dhttp.proxyPort=80
                -Dhttps.proxyPort=80
            '''
        }
    }
}

job("$basePath/static-method-example2") {

    steps {
        StepsUtil.proxiedGradle delegate, 'clean test'
    }
}

