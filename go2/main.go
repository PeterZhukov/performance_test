package main

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"io/ioutil"
	"net/http"
	"strconv"
)

func RequestHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id, err := strconv.Atoi(vars["id"])
	if err != nil {

	}
	if id >= 10 {
		response := make(map[string]int)
		response["id"] = id
		jData, err := json.Marshal(response)
		if err != nil {
		}
		_, err = w.Write(jData)
		if err != nil {
		}
		return
	}
	id = id + 1
	response, err := http.Get(`http://localhost:3001/go-request/` + strconv.Itoa(id))
	if err != nil {
	}
	b, err := ioutil.ReadAll(response.Body)
	if err != nil {
	}
	_, err = w.Write(b)
	if err != nil {
	}
}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/go-request/{id}", RequestHandler)
	err := http.ListenAndServe(":3001", r)
	if err != nil {
	}
}
