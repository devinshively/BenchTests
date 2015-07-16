import com.shively.example.Document
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer
import grails.rest.render.xml.XmlCollectionRenderer
import grails.rest.render.xml.XmlRenderer

// Place your Spring DSL code here
beans = {
    documentXmlRenderer(XmlRenderer, Document) {
        excludes = ['class']
    }
    documentJSONRenderer(JsonRenderer, Document) {
        excludes = ['class']
    }
    documentXmlCollectionRenderer(XmlCollectionRenderer, Document) {
        excludes = ['class']
    }
    documentJSONCollectionRenderer(JsonCollectionRenderer, Document) {
        excludes = ['class']
    }
}
