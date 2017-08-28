(ns janis.core
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def base-options
  {:location "resources/janis"})

(defn check-files [location] "beep")

(defn process-options [options]
  (reduce-kv  #(assoc base-options %2 %3) base-options options))

(defn read-file [file]
  (edn/read (java.io.PushbackReader. (io/reader file))))

(defn retrieve-tables [file-paths]
  (reduce #(conj %1 (read-file %2)) [] file-paths))

(defn setup
  "I don't do a whole lot."
  ([db]
   (setup {} db))
  ([options db]
   (let [merged-options (process-options options)
         file-paths (check-files (:location merged-options))])))
