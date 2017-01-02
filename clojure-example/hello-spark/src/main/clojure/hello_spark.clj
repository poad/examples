(ns hello-spark
  (:import (org.apache.spark.api.java JavaSparkContext)
           (org.apache.spark.api.java.function VoidFunction)
           (java.util ArrayList)))

(defn -main []
  (
    (with-open [sc (JavaSparkContext. "local" "Hello World")]
      (let [list (ArrayList.)]
        (
          (. list add "Hello")
          (. list add "World")
          (. list add "!")

          (let [rdd (. sc parallelize list)]
            (. rdd foreach (reify VoidFunction
                             (call [this v]
                               (println (v)))))

            )
          (flush)
          )
        ))
    )
  )
