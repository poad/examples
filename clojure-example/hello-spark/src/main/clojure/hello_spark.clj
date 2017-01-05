(ns hello-spark
  (:import (org.apache.spark.api.java JavaSparkContext)
           (org.apache.spark.api.java.function VoidFunction)
           (java.util ArrayList)))

(defn -main []
  (with-open [sc (JavaSparkContext. "local" "Hello World")]
    (let [list (doto (ArrayList.)
                 (.add "Hello")
                 (.add "World")
                 (.add "!"))
          rdd (.parallelize sc list)]
      (.foreach rdd (reify VoidFunction
                      (call [this v]
                        (println (v)))))
      (flush))))
