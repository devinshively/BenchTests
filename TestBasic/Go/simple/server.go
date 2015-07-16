package main

import (
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"github.com/jmoiron/sqlx"
	_ "github.com/lib/pq"
	"log"
	"net/http"
	"runtime"
)

var db *sqlx.DB

type Document struct {
	Id    int64
	Title string
	Text  string
}

func helloHandler(res http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(res, "Hello World!")
}

func getAllDocumentsHandler(res http.ResponseWriter, req *http.Request) {
	docs := []Document{}
	err := db.Select(&docs, `SELECT * FROM documents`)
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
	var doc Document
	if err := decoder.Decode(&doc); err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	if doc.Id == 0 {
		rows, err := db.NamedQuery(`INSERT INTO documents(title, text) VALUES(:title, :text) RETURNING id`, &doc)
		if err != nil {
			http.Error(res, err.Error(), http.StatusInternalServerError)
			return
		}
		if rows.Next() {
			rows.Scan(&doc.Id)
		}
	} else {
		_, err := db.NamedExec(`UPDATE documents SET title=:title, text=:text WHERE id=:id`, &doc)
		if err != nil {
			http.Error(res, err.Error(), http.StatusInternalServerError)
			return
		}
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
	doc := Document{}
	err := db.Get(&doc, `SELECT * FROM documents WHERE id=$1`, id)

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

	_, err := db.NamedExec(`DELETE FROM documents WHERE id=$1`, id)
	if err != nil {
		http.Error(res, err.Error(), http.StatusInternalServerError)
		return
	}

	res.WriteHeader(http.StatusNoContent)
}

func main() {
	runtime.GOMAXPROCS(runtime.NumCPU())
	newDb, err := sqlx.Connect("postgres", "dbname=test host=localhost port=5432 user=test password=password sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	db = newDb
	router := mux.NewRouter()
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("./static/"))))
	router.HandleFunc("/documents", getAllDocumentsHandler).Methods("GET")
	router.HandleFunc("/documents", createOrUpdateDocumentHandler).Methods("POST")
	router.HandleFunc("/documents/{id}", getDocumentHandler).Methods("GET")
	router.HandleFunc("/documents/{id}", deleteDocumentHandler).Methods("DELETE")
	router.HandleFunc("/hello", helloHandler).Methods("GET")
	http.Handle("/", router)
	http.ListenAndServe(":8080", nil)
}
