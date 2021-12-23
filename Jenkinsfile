properties([
        [$class              : 'ParametersDefinitionProperty',
         parameterDefinitions: [[$class      : 'StringParameterDefinition',
                                 defaultValue: '',
                                 description : '',
                                 name        : 'DOMAIN',
                                ]]],
        disableConcurrentBuilds(),
])

node("win10") {
    stage("Step 1 - checkout code") {
        var_of_git = checkout(scm)
        writeFile(file: 'Dockerfile', text: readFile(file: 'Dockerfile') + '\nENV GIT_COMMIT=' + var_of_git.get('GIT_COMMIT'))
    }
    stage("Step 2 - maven package") {
        if (params.DOMAIN?.trim()) {
            stage('replace ngrok domain') {
                env.PROJECT_WEB_INDEX = "https://" + params.DOMAIN?.trim() + "/blog-web"
                env.SECURITY_OAUTH2_SERVER_DOMAIN = "https://" + params.DOMAIN?.trim()
                env.SECURITY_OAUTH2_CLIENT_PRE_ESTABLISHED_REDIRECT_URI = "https://" + params.DOMAIN?.trim() + "/blog/login"
                powershell('''
                    (Get-Content src/main/resources/application.properties).replace("@pom.project.web.index@", $env:PROJECT_WEB_INDEX) | Set-Content src/main/resources/application.properties
                    (Get-Content src/main/resources/application.properties).replace("@pom.security.oauth2.server.domain@", $env:SECURITY_OAUTH2_SERVER_DOMAIN) | Set-Content src/main/resources/application.properties
                    (Get-Content src/main/resources/application.properties).replace("@pom.security.oauth2.client.pre-established-redirect-uri@", $env:SECURITY_OAUTH2_CLIENT_PRE_ESTABLISHED_REDIRECT_URI) | Set-Content src/main/resources/application.properties
                ''')
            }
        }

        powershell('''
            mvn package -DskipTests=true -P=test
        ''')
    }
    stage('Step 3 - docker build') {
        powershell('''
            docker build -t local/blog .
        ''')
    }
    stage("Step 4 - deploy") {
        try {
            powershell('''
                docker rm -f blog
            ''')
        } catch (e) {

        }
        powershell('''
            docker run -itd --restart always -p 11000:20000 --name blog local/blog
        ''')
    }
}