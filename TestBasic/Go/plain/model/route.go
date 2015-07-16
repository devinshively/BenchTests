package model

import (
	"net/http"
)

var Routes = make([]Route, 0)

func RegisterRoute(r Route) {
	Routes = append(Routes, r)
}

type Route struct {
	Method  string
	Path    string
	Handler func(res http.ResponseWriter, req *http.Request)
}
