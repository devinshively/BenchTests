package main

import (
  "net/http"
  "runtime"
)

func main() {
  runtime.GOMAXPROCS(runtime.NumCPU()-1)
  http.HandleFunc("/", hello)
  http.ListenAndServe(":8080",nil)
}

func hello(w http.ResponseWriter, r *http.Request) {
  w.Write([]byte("Hello World!"))
}
