(ns janis.core)

(def base-options
  {:location "resources/janis"})

(defn check-files [location] "beep")

(defn process-options [options]
  (reduce-kv  #(assoc base-options %2 %3) base-options options))

(defn setup
  "I don't do a whole lot."
  ([db]
   (setup {} db))
  ([options db]
   (let [merged-options (process-options options)
         files (check-files (:location merged-options))])))
