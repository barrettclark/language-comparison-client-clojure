(ns client.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(defn get-it
  [url]
  (-> url
    client/get
    :body
    json/read-str
    (get "name")))

(defn now [] (System/currentTimeMillis))
(defn diff-ms [start] (format "%7dms" (- (now) start)))

(defmacro timed-run [title & body]
  `(let [start# (now)]
     (println ~title "starting...")
     ~@body
     (println ~title "completed in" (diff-ms start#))))

(defn -main []
  (timed-run "TOTAL"
    (let [n 100
          url "http://localhost:9292"]

      (timed-run "Serial"
        (println (string/join "\n"
                   (take n (repeatedly #(get-it url))))))

      (timed-run "Parallel"
        (println (string/join "\n"
                   (pmap identity (take n (repeatedly #(get-it url)))))))

      (timed-run "Serial w/o print"
        ;; clojure is lazy, so if we don't print these,
        ;; the work may not be done; doall ensures it is
        (doall (take n (repeatedly #(get-it url)))))

      (timed-run "Parallel w/o print"
        (doall (pmap identity (take n (repeatedly #(get-it url))))))

      ;;we used pmap, so background agent threads will wait
      ;; until timeout (30s?) on exit; this terminates them now
      (shutdown-agents))))
