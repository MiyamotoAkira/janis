(ns janis.core-test
  (:require [clojure.test :refer :all]
            [janis.core :as janis]))

(deftest setup
  (testing "When no options map is passed then it should check for files on resources/janis"
    (let [checked (atom false)
          db {}]
      (with-redefs [janis/check-files
                    (fn
                      [location]
                      (cond
                        (= location "resources/janis") (reset! checked true)
                        :else (reset! checked false)))]
        (janis/setup db)
        (is @checked))))
  (testing "When options map is passed without location then it should check for files on resources/janis"
    (let [checked (atom false)
          db {}]
      (with-redefs [janis/check-files
                    (fn
                      [location]
                      (cond
                        (= location "resources/janis") (reset! checked true)
                        :else (reset! checked false)))]
        (janis/setup {} db)
        (is @checked))))

  (testing "When options map is passed with location then it should check for files on location"
    (let [file_location "location"
          checked (atom false)
          db {}]
      (with-redefs [janis/check-files
                    (fn
                      [location]
                      (cond
                        (= location file_location) (reset! checked true)
                        :else (reset! checked false)))]
        (janis/setup {:location file_location} db)
        (is @checked)))))

(deftest process-options
  (testing "when empty options pased should return base options"
    (let [changed-options (janis/process-options {})]
      (is (= changed-options janis/base-options))))
  (testing "When options pased should change return options with changes"
    (let [changed-options (janis/process-options {:location "test"})]
      (is (= "test" (:location changed-options))))))

(deftest retrieve-table-setups
  (let [files ["file1"]]
    (testing "single table just name"
      (with-redefs [janis/read-file (fn [file] {:name "table-name"})]
        (let [tables (janis/retrieve-tables files)]
          (is (= 1 (count tables))))))
    (testing "single table with all options")
    (testing "multiple tables")
    (testing "table with improvements")))


