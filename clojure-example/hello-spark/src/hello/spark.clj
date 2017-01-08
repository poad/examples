(ns hello.spark
  (:require [flambo.conf :as conf])
  (:require [flambo.api :as f]))

(defn -main [& args]
  (def c (-> (conf/spark-conf)
             (conf/master "local")
             (conf/app-name "Hello World")))

        (def sc (f/spark-context c))
  (def rdd (f/parallelize sc ["Hello" " " "World" "!"]))
    (let [str (f/reduce rdd (f/fn [x y] (.concat x y)))]
      (println str))
  (flush)
    )
