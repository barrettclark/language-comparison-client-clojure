(ns client.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn extractJson
  [responseBody]
  (get (json/read-str responseBody) "name"))

(defn makeCall
  [url]
   (:body
    (client/get url)))

(defn getIt
  [url]
   (println
    (extractJson
     (makeCall url))))

(defn -main []
  (loop [i 0]
    (when (< i 100)
      (getIt "http://localhost:9292")
      (recur (inc i)))))
