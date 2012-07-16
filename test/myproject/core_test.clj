(ns myproject.core-test
  (:use clojure.test
        myproject.core))

(deftest a-test
  (testing "fill-handbag."
    (is (= 0 1))))

(deftest test-with-test-data
  []
  (let [start-max-weight 5
        [total-value selected-dolls] (fill-handbag test-items (- (count test-items) 1) start-max-weight)
        ; get names from the test-items based on selected-dolls
        names (map (comp :name test-items) selected-dolls)]
     (if (= total-value 6)
         (println "Total value is correct. " total-value))
     (if (= (into #{} ["jason" "sarah"]) (into #{} names))
         (println "Selected items are correct:" (reverse names)))
     )
  
  )

;Standard unit test with a small set of data and the expected results hardcoded.  Testing for the max weight of 15.
;Prints out a success message as well as the expected results.
(deftest unit-tests
  []
  (use '[clojure.string :only (split)])
  (use '[clojure.java.io :only [reader]])
  (let [new-names-set [
           "sarah" 1 1
           "jason" 4 5
           "nila" 3 2 
           "jonas" 5 6
           "judy" 8 15
           "denny" 9 14
           "lis" 2 2]
        new-test-items (vec (map #(apply struct item %) (partition 3 new-names-set)))
        start-max-weight 15
        names-result ["lis" "judy" "jason" "sarah"]
        good-result 23
                     
        ;call function to compute answer and answer vector
        [total-value selected-items] (fill-solution new-test-items (- (count new-test-items) 1) start-max-weight)
        ; get names based on answer-vector
        names (map (comp :name new-test-items) selected-items)]
    
    (assert (number? total-value))
    (assert (vector? selected-items))
    (assert (seq? names))
    
    (assert (= total-value good-result))
    (assert (= (into #{} names-result) (into #{} names)))
    (println "All tests succeeded. \nTotal value is: " total-value)
    (println "Selected items are:" (reverse names))
     )
  
  )
