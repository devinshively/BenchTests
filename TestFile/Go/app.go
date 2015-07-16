package main

import "net/http"
import "gopkg.in/mgo.v2"
import "gopkg.in/mgo.v2/bson"
import "runtime"
import "fmt"
import "encoding/json"

type Network struct {
  Ssid string
}

func main() {
  runtime.GOMAXPROCS(runtime.NumCPU()-1)
  session, err := mgo.Dial("localhost")
  if err != nil {
    panic(err)
  }
  defer session.Close()
  session.SetMode(mgo.Monotonic, true)
  collection := session.DB("demo").C("networks")

  http.HandleFunc("/network/", func(w http.ResponseWriter, r *http.Request) {
    network := r.URL.Path[9:]
    result := Network{}
    err = collection.Find(bson.M{"ssid":network}).One(&result)
    if err != nil {
      fmt.Fprint(w, err)
      return
    }
    jsonResult, _ := json.Marshal(result)
    fmt.Fprint(w, string(jsonResult))
  })


  http.ListenAndServe(":8080",nil)
}
