package api

import (
	"encoding/json"
	"github.com/devinshively/ExampleRestAPI/database"
	"github.com/devinshively/ExampleRestAPI/database/postgres"
	"github.com/devinshively/ExampleRestAPI/model"
	"github.com/gorilla/mux"
	"net/http"
)

var db database.DocumentDataStore

func init() {
	routes := []model.Route{
		{
			Method:  "GET",
			Path:    "/documents",
			Handler: getAllDocumentsHandler,
		}, {
			Method:  "POST",
			Path:    "/documents",
			Handler: createOrUpdateDocumentHandler,
		}, {
			Method:  "GET",
			Path:    "/documents/{id}",
			Handler: getDocumentHandler,
		}, {
			Method:  "DELETE",
			Path:    "/documents/{id}",
			Handler: deleteDocumentHandler,
		},
	}

	for _, rt := range routes {
		model.RegisterRoute(rt)
	}

	// Set datastore
	db = new(postgres.PostgresDocumentDB)
}

func getAllDocumentsHandler(res http.ResponseWriter, req *http.Request) {
	docs, err := db.GetAll()
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	docsJson, err := json.Marshal(docs)
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	res.Write(docsJson)
}

func createOrUpdateDocumentHandler(res http.ResponseWriter, req *http.Request) {
	decoder := json.NewDecoder(req.Body)
	var doc model.Document
	if err := decoder.Decode(&doc); err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	if err := db.CreateOrUpdate(&doc); err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	docJson, err := json.Marshal(doc)
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	res.Write(docJson)
}

func getDocumentHandler(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	id := vars["id"]
	doc, err := db.Get(id)
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	docJson, err := json.Marshal(doc)
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	res.Write(docJson)
}

func deleteDocumentHandler(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	id := vars["id"]

	if err := db.Delete(id); err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	res.WriteHeader(http.StatusNoContent)
}
