import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {

    vcsRoot(HttpsGithubComHexal16TCAngular)

    buildType(FastTest)
    buildType(Deploy)
    buildType(OtherTests)

    template(Tmp)
}

object Deploy : BuildType({
    name = "Deploy"

    vcs {
        root(HttpsGithubComHexal16TCAngular)
    }

    dependencies {
        snapshot(FastTest) {
        }
        snapshot(OtherTests) {
        }
    }
})

object FastTest : BuildType({
    templates(Tmp)
    name = "FastTest"
    description = "Muahahahahahahahahahahahahahahahahahahahahahahah"
    params {
        param("browsers", "Chrome")
    }

    steps {
        script {
            name = "test"
            id = "RUNNER_11"
            scriptContent = "ng test --watch=false --browsers %browsers%"
        }
    }
})

object NEEEW : BuildType({
    templates(Tmp)
    name = "NEEEW"

    params {
        param("browsers", "IE")
    }

    dependencies {
        snapshot(FastTest) {
        }
    }
})

object OtherTests : BuildType({
    templates(Tmp)
    name = "OtherTests"

    params {
        param("browsers", "Chrome")
    }

    dependencies {
        snapshot(FastTest) {
        }
    }
})

object Tmp : Template({
    name = "tmp"

    vcs {
        root(HttpsGithubComHexal16TCAngular)
    }

    steps {
        script {
            name = "npm"
            id = "RUNNER_10"
            scriptContent = "npm install"
        }
        script {
            name = "test"
            id = "RUNNER_11"
            scriptContent = "ng test --watch=false --browsers %browsers%"
        }
    }
})

object HttpsGithubComHexal16TCAngular : GitVcsRoot({
    name = "https://github.com/Hexal16/TCAngular"
    url = "https://github.com/Hexal16/TCAngular"
    branch = "main"
})
