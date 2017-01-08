(defproject clojure-example "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :eval-in-leiningen true
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 ]
  :plugins [[lein-localrepo "0.5.3"]
            [lein-sub "0.3.0"]]
  :sub ["hello-clojure" "hello-spark"]
  )
