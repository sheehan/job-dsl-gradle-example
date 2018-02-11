String basePath = 'example9'
String repo = ':pserver:anoncvs@example.org:/cvsroot'

folder(basePath) {
    description 'This example shows how to use the automatically generated DSL provided by other plugins.'
}

job("$basePath/gradle-legacy-build") {
    scm {
        cvsscm { // <-- NOTE: provided by testPlugins dependency in build.gradle
            repositories {
                cvsRepository {
                    cvsRoot repo
                    passwordRequired false
                    password null
                    compressionLevel(-1)
                    repositoryBrowser {}
                    repositoryItems {
                        cvsRepositoryItem {
                            modules {
                                cvsModule {
                                    remoteName 'sheehan/gradle-example'
                                    localName '.'
                                    projectsetFileName null
                                }
                            }
                            location {
                                headRepositoryLocation()
                            }
                        }
                    }
                }
            }
            canUseUpdate true
            pruneEmptyDirectories true
            legacy false
            skipChangeLog false
            disableCvsQuiet false
            cleanOnFailedUpdate false
            forceCleanCopy false
            checkoutCurrentTimestamp false
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    steps {
        gradle 'assemble'
    }
}

folder('images-folder') {
    displayName('Docker Images')

    properties {
        folderLibraries { // <-- NOTE: provided by testPlugins dependency in build.gradle
            libraries {
                libraryConfiguration {
                    name 'test'
                    retriever {
                    }
                }
            }
        }
    }
}