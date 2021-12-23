node("win10") {
    stage("Step 1 - checkout code") {
        var_of_git = checkout(scm)
        writeFile(file: 'Dockerfile', text: readFile(file: 'Dockerfile') + '\nENV GIT_COMMIT=' + var_of_git.get('GIT_COMMIT'))
    }
    stage("Step 2 - maven package") {
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