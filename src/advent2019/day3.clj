(ns advent2019.day3
    (:require [clojure.string :as s])
    (:require [clojure.set :as set])
    (:require [advent2019.utils :as utils]))

(defn move [point dir]
    (cond
        (s/starts-with? dir "R") (update point :x inc)
        (s/starts-with? dir "L") (update point :x dec)
        (s/starts-with? dir "U") (update point :y inc)
        (s/starts-with? dir "D") (update point :y dec)
            ))

(defn sequence-generator [inputstr]
    (take (Integer/parseInt (subs inputstr 1)) (repeat (subs inputstr 0 1))))

(defn move-reducer [input]
    (reductions move {:x 0 :y 0} input))

(defn split-input [input]
    (s/split input #","))

(defn move-sequence [input]
    (->> input
        (split-input)
        (map sequence-generator)
        (flatten)
        (move-reducer)
        (into #{})))
 
(defn cross-point [list]
    (set/intersection (first list) (second list)))

(defn dist-orig [point]
    (+ (Math/abs (:x point)) (Math/abs (:y point))))

(defn run []
    (let [input (utils/day-file 3)]
    {:part1 (apply min (map dist-orig (disj (cross-point (map move-sequence input)) {:x 0 :y 0})))}))
