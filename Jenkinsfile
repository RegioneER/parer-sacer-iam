node {
    
    def mvnTool = tool 'Apache Maven 3.0.5'
    def antTool = tool 'Apache Ant(TM) version 1.9.2'
    
    stage('Init'){
        echo "Pulizia workspace"
        deleteDir()
    }
    
    stage('Checkout'){
        echo "Check out di settings.xml"
        dir('maven') {
            git branch: 'master',  poll: false, credentialsId: "${credentials}", url: 'https://redmine.ente.regione.emr.it/git/parer-tools-mvn-settings'
        }
        echo "Check out di saceriam"
        dir('saceriam') {
            git branch: "${branch}", credentialsId: "${credentials}", url: 'https://redmine.ente.regione.emr.it/git/parer-sacer-iam'
        }
    }
    
    stage ('Build'){
        echo "Building saceriam"
        dir('saceriam') {
            echo "Build maven"
            sh "${mvnTool}/bin/mvn -s ../maven/settings.xml clean install" 
            echo "Esecuzione target ant snap-dist"
            sh "${antTool}/bin/ant snap-dist"
        }
    }
    
    stage ('Distribution'){
        echo "Distributing saceriam"
        dir('dist') {
            sh 'cp ../saceriam/ears/saceriam*-snap.ear saceriam.ear'
            sh '/opt/jboss-http-api/as7_deploy.py -s -p 9443 parer-vas-b01.ente.regione.emr.it saceriam.ear'
        }
        
    }
}

