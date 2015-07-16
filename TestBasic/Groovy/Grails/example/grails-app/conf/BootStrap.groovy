import com.shively.example.Document
import grails.util.Environment

class BootStrap {
    def init = { servletContext ->

        def result = '################## running in UNCLEAR mode.'
        println "Application starting ... "
        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                result = 'now running in DEV mode.'
//                seedTestData()
                break;
            case Environment.TEST:
                result = 'now running in TEST mode.'
                break;
            case Environment.PRODUCTION:
                result = 'now running in PROD mode.'
                seedProdData()
                break;
        }
        println "current environment: $Environment.current"
        println "$result"
    }

    def destroy = {
        println "Application shutting down... "
    }

    private void seedTestData() {
        def document = null
        println "Start loading documents into database"
        document = new Document(title: 'Munich', text: "81927")
        assert document.save(failOnError:true, flush:true, insert: true)
        document.errors = null

        document = new Document(title: 'Berlin', text: "10115")
        assert document.save(failOnError:true, flush:true, insert: true)
        document.errors = null

        assert Document.count == 2;
        println "Finished loading $Document.count documents into database"
    }
}
