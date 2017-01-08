(defproject hello-spark "0.0.1-SNAPSHOT"
  :profiles {:uberjar {:aot :all}
             :dev {:aot :all}
             :provided {:dependencies
                        [[org.apache.spark/spark-core_2.11 "2.1.0"]]}}
  :dependencies [[org.clojure/clojure "1.8.0"] [yieldbot/flambo "0.8.0"]]
  :main hello.spark
  )