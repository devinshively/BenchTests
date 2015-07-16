package main

import (
	"fmt"
	"github.com/devinshively/ExampleRestAPI/api"
	"github.com/devinshively/ExampleRestAPI/model"
	"github.com/gorilla/mux"
	"net/http"
	"runtime"
)

func helloHandler(res http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(res, "Hello World!")
}

func main() {
	runtime.GOMAXPROCS(runtime.NumCPU())
	api.Register()
	router := mux.NewRouter()
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("./static/"))))
	for _, rt := range model.Routes {
		router.HandleFunc(rt.Path, rt.Handler).Methods(rt.Method)
	}
	router.HandleFunc("/hello", helloHandler).Methods("GET")
	http.Handle("/", router)
	http.ListenAndServe(":8080", nil)
}
