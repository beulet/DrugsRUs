(ns myproject.core)

(def item-data
  [ "luke"        9   150
   "anthony"    13    35
   "candice"   153   200
   "dorothy"    50   160
   "puppy"      15    60
   "thomas"     68    45
   "randal"     27    60
   "april"      39    40
   "nancy"      23    30
   "bonnie"     52    10
   "marc"       11    70
   "kate"       32    30
   "tbone"      24    15
   "tranny"     48    10
   "uma"        73    40
   "grumpkin"   42    70
   "dusty"      43    75
   "grumpy"     22    80
   "eddie"       7    20
   "tory"       18    12
   "sally"       4    50
   "babe"       30    10])
 


(defstruct item :name :weight :value)
 
(def items (vec (map #(apply struct item %) (partition 3 item-data))))

(declare fill-solution-cached)

(defn fill-solution
  "Recursively call to create two results, one the max possible value and two an array of items that were used to create this value.  Compute the max value at this weight using items in the array. Inputs are the items available vector, index we are computing for and the weight limit we are computing for"
  [items-available index weight-limit]
  (cond
    ; if index < 0 or weight-limit = 0
    (< index 0) [0 []]
    (zero? weight-limit) [0 []]
    :else
    ;set up variables to process item at index
    (let [{item-weight :weight item-value :value} (get items-available index)]
      
      (if (> item-weight weight-limit)
        ; item weight greater than max weight so this item cannot be in solution, run for next item
        (fill-solution-cached items-available (dec index) weight-limit)
        
        
        ;else clause, find the answers to the previous items and use that to determine if the item stays in the solution
        (let [[value-of-previous-item sn :as no] (fill-solution-cached items-available (dec index) weight-limit)
              [value-of-previous-item-at-weight-wo-current-weight sy :as yes] (fill-solution-cached items-available (dec index) (- weight-limit item-weight))]
          
          (if (> (+ value-of-previous-item-at-weight-wo-current-weight item-value) value-of-previous-item)
            ;item better than previous, keep and return 
            [(+ value-of-previous-item-at-weight-wo-current-weight item-value) (conj sy index)]
            ; don't keep item
            no))))))

(def fill-solution-cached (memoize fill-solution))

;main function that finds the answer to the origional question.
;Takes a max weight you would like to solve for.
;Prints out the selected dolls (items selected) and the street value (max profit).
(defn solve-drug-smuggler
  [max-weight]
 (let [[total-value selected-items] (fill-solution items (- (count items) 1) max-weight)
        ; get the names from the dolls-available based on selected-dolls
        names (map (comp :name items) selected-items)]
     (println "Street Value:" total-value)
     (println "Selected Dolls:" (reverse names))
  
    ) )
  

  
  


;Standard unit test with a small set of data and the expected results hardcoded.  Testing for the max weight of 15.
;Prints out a success message as well as the expected results.
(defn unit-test
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

;Reads an input file of test data.  Please give the full file location ex:  "C://Drugs/myprojectdata.txt"
;Test data must be in the format 'name weight value' with only one space between and no punctuation. 
;Takes the max weight that you are solving for.
;Returns the total value and items selected. 
(defn test-other-data
  [max-weight full-file-name]
  (use '[clojure.string :only (split)])
  (use '[clojure.java.io :only (reader)])
  (let [
        ;setting up file reader
        items-from-file (vec (map (fn [[name weight cost]] 
                (hash-map :name name 
                          :weight (Integer/parseInt weight) 
                          :value (Integer/parseInt cost))) 
                          (map #(split % #" ") (line-seq (reader full-file-name)))))
    
        ;call function to compute answer and answer vector
        [total-value selected-items] (fill-solution items-from-file (- (count items-from-file) 1) max-weight)
        ; get names based on answer-vector
        names (map (comp :name items-from-file) selected-items)]
    
    (assert (number? total-value))
    (assert (vector? selected-items))
    (assert (seq? names))
    
    (println "Total value is: " total-value)
    (println "Selected items are:" (reverse names))
     )
  
  )