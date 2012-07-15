
 
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

(declare fill-handbag-cached)

(defn fill-handbag
  "Recursively call to create an array of maximum values at the max weight.  Compute the max value at this weight using items in the array. The array is ordered by items available vector, count of items available and the weight limit we are computing for"
  [items-available index size-of-handbag]
  (cond
    ; if index < 0 or handbag size = 0
    (< index 0) [0 []]
    (zero? size-of-handbag) [0 []]
    :else
    (let [{doll-weight :weight doll-value :value} (get items-available index)]
      (if (> doll-weight size-of-handbag)
        ; weights too much run again
        (fill-handbag-cached items-available (dec index) size-of-handbag)
        ; else
        (let [ new-handbag-size (- size-of-handbag doll-weight)
              [vn sn :as no] (fill-handbag-cached items-available (dec index) size-of-handbag)
              [vy sy :as yes] (fill-handbag-cached items-available (dec index) new-handbag-size)]
          (if (> (+ vy doll-value) vn)
            ; keep
            [(+ vy doll-value) (conj sy index)]
            ; skip / shortcut
            no))))))

(def fill-handbag-cached (memoize fill-handbag))

(defn fill-the-bag 
  [max-weight]
 (let [start-max-weight max-weight
        [total-street-value selected-dolls] (fill-handbag items (- (count items) 1) start-max-weight)
        ; grab the names from the dolls-available based on selected-dolls
        names (map (comp :name items) selected-dolls)]
    (println "Street Value:" total-street-value)
     (println "\nSelected Dolls:" (reverse names))
  
    ) )
  

