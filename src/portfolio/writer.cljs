(ns portfolio.writer
  (:require [portfolio.romaji :as romaji]))

(defn hiraganize [input]
  (map romaji/toHiragana input))

;; Parses a token: either a string or a map like the following
;; `{:read "romaji reading" :out "final output"}`
(defn parse [prefix input]
  (cond (string? input) (for [idx (range 0 (count input))] (str prefix (subs input 0 (inc idx))))
        :else (concat (hiraganize (parse prefix (:read input))) [(str prefix (:out input))])))

(defn build-typing [input]
  (cond (string? input) (parse "" input)
        :else (apply concat (map-indexed (fn [idx elem]
                                           (let [prefix (reduce (fn [result index] (str result (:out (nth input index)))) "" (range 0 idx))]
                                             (parse prefix elem))) input))))

(defn aux-writer [input callback msec count]
  (if (< count (dec (alength (to-array input)))) (js/setTimeout #(aux-writer input callback msec (inc count)) msec))
  (if (>= count 0) (callback (nth input count))))

(defn writer [input callback msec]
  (aux-writer input callback msec 0))

(js/console.log (clj->js (build-typing "asdadasd")))
(js/console.log (clj->js (build-typing [{:read "asayakega" :out "朝焼けが"} {:read "mabushisugite" :out "眩しすぎて"}])))
