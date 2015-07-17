(defproject client "0.1.0-SNAPSHOT"
  :description "Language Comparison Client - Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-http "1.1.2"]
                 [org.clojure/data.json "0.2.6"]]
  :main client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
