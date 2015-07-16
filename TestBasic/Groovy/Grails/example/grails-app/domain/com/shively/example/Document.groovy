package com.shively.example

import grails.rest.Resource

/**
 * Document
 * A domain class describes the data object and it's mapping to the database
 */

@Resource(uri='/documents', formats=['json', 'xml'])
class Document {

    Long   id
    String title
    String text

    static mapping = {
        table "documents"
        version false
    }

    static constraints = {
        title blank:false, nullable:false
        text blank:false, nullable:false
    }
}

