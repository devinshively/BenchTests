package main

import (
  "net/http"
  "runtime"
  "fmt"
  // "encoding/json"
  "os"
  "io/ioutil"
  "github.com/satori/go.uuid"
  "time"
)

func main() {
  runtime.GOMAXPROCS(runtime.NumCPU()-1)
  http.HandleFunc("/sqm-list", writeToFile)
  http.HandleFunc("/sqm-list/", writeToFile)
  http.ListenAndServe(":8080",nil)
}

func writeToFile(w http.ResponseWriter, r *http.Request) {
  postBody, err := ioutil.ReadAll(r.Body)
  if err != nil {
    fmt.Println(err)
    w.WriteHeader(400)
  } else {

    u1 := uuid.NewV4()
    f, err := os.Create("/Users/dshively/TestFile/"+time.Now().Format("20060102")+"-"+u1.String())
    if err != nil {
      fmt.Println(err)
    }
    _, err = f.Write(postBody)
    if err != nil {
      fmt.Println(err)
    }
    f.Close()

    w.WriteHeader(200)
  }
}
