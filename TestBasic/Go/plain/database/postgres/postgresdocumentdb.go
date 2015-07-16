package postgres

import (
	"database/sql"
	"github.com/devinshively/ExampleRestAPI/model"
	_ "github.com/lib/pq"
	"log"
)

var db *sql.DB

func init() {
	newDb, err := sql.Open("postgres", "dbname=test host=localhost port=5432 user=test password=password sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}
	db = newDb
}

type PostgresDocumentDB struct{}

func (ds PostgresDocumentDB) CreateOrUpdate(doc *model.Document) error {
	if doc.Id == 0 {
		var docId int
		query := `INSERT INTO documents(title, text) VALUES($1, $2) RETURNING id`
		err := db.QueryRow(query, doc.Title, doc.Text).Scan(&docId)
		doc.Id = docId
		return err
	} else {
		query := `UPDATE documents SET title=$2, text=$3 WHERE id=$1`
		_, err := db.Exec(query, doc.Id, doc.Title, doc.Text)
		return err
	}
}

func (ds PostgresDocumentDB) Get(docId string) (*model.Document, error) {
	var id int
	var title string
	var text string
	query := `SELECT * FROM documents WHERE id=$1`
	err := db.QueryRow(query, docId).Scan(&id, &title, &text)
	return &model.Document{
		Id:    id,
		Title: title,
		Text:  text,
	}, err
}

func (ds PostgresDocumentDB) Delete(docId string) error {
	query := `DELETE FROM documents WHERE id=$1`
	_, err := db.Exec(query, docId)
	return err
}

func (ds PostgresDocumentDB) GetAll() ([]model.Document, error) {
	var docs []model.Document
	query := `SELECT * FROM documents`
	rows, err := db.Query(query)
	if err != nil {
		log.Fatal(err)
	}
	for rows.Next() {
		var id int
		var title string
		var text string
		if err := rows.Scan(&id, &title, &text); err != nil {
			log.Fatal(err)
		}
		docs = append(docs, model.Document{
			Id:    id,
			Title: title,
			Text:  text,
		})
	}
	if err := rows.Err(); err != nil {
		log.Fatal(err)
	}
	return docs, err
}
