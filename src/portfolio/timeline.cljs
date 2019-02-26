(ns portfolio.timeline
  (:require
    [portfolio.resources :as s]))

(def svg
  (.size (.SVG js/window "timeline") s/svg-max-width s/svg-max-height))

(def svg-rect
  (.fill (.rect svg s/svg-max-width s/svg-max-height) s/svg-color))

(def svg-mask (.mask svg))

(defn arrow-body-segment [color width height dash-array x y]
  (let [default-stroke-properties {:color color :width width}]
    (let [full-stroke-properties (if (clojure.string/blank? dash-array) default-stroke-properties (assoc default-stroke-properties :dasharray dash-array))]
      (.add svg-mask (.move (.stroke (.line svg (str "0,0 0," height)) (clj->js full-stroke-properties)) x y)))))

(defn arrow-tail [color height side x y]
  (arrow-body-segment s/shade-medium 2 height "" x y)
  (doseq [n (range (inc (/ side 5)))]
    (.add svg-mask (.move (.stroke (.line svg (str "0," side " " side ",0")) (clj->js {:color color :width 1})) x (- y (* n 5))))
    (.add svg-mask (.move (.stroke (.line svg (str "0,0 " side "," side)) (clj->js {:color color :width 1})) (- x side) (- y (* n 5)))))
  (.add svg-mask (.move (.fill (.polygon svg (str "0,0 0," (inc side) " " (inc (/ side 2)) "," (/ side 2))) color) x (dec y))))

(defn arrow-head [color height side x y]
  (arrow-body-segment s/shade-medium 2 height "" x y)
  (.add svg-mask (.move (.stroke (.fill (.polygon svg (str (* (/ side 3) 2) ",0" " " (/ side 3) "," (/ side 3) " " (/ side 3) "," side)) "none") (clj->js {:width 2 :color color})) x (+ (- height (* side 0.5)) y)))
  (.add svg-mask (.move (.fill (.polygon svg (str "0,0 " (/ side 3) "," (/ side 3) " " (/ side 3) "," side)) color) (dec (- x (/ side 3))) (+ (- height (* side 0.5)) y))))

(defn arrow-detail-battery [color width x y]
  (.add svg-mask (.move (.stroke (.line svg (str "0,0 " width ",0")) (clj->js {:color color :width 1})) (- x (/ width 2)) y))
  (.add svg-mask (.move (.stroke (.line svg (str "0,0 " (/ width 2) ",0")) (clj->js {:color color :width 1})) (- x (/ width 4)) (+ y 5))))

(defn arrow-detail-triangles [color-1 color-2 width x y]
  (.add svg-mask (.move (.fill (.polygon svg (str width "," (* 2 width) " 0,0 " (* 2 width) ",0")) color-2) (- x width) (+ y width)))
  (.add svg-mask (.move (.fill (.polygon svg (str width "," (* 2 width) " 0,0 " (* 2 width) ",0")) color-1) (- x width) y)))

(defn arrow-detail-circles [color-1 color-2 color-3 width x y]
  (.add svg-mask (.move (.fill (.circle svg (* width 0.8)) color-1) (- x (* width 0.4)) y))
  (.add svg-mask (.move (.fill (.circle svg (* width 0.8)) color-2) (- x (* width 0.4)) (+ (* width 1.2) y)))
  (.add svg-mask (.move (.fill (.circle svg width) color-3) (- x (/ width 2)) (+ (/ width 2) y))))

(defn arrow-detail-squares [color-1 color-2 color-3 color-4 width x y]
  (.add svg-mask (.transform (.move (.fill (.rect svg width width) color-1) (- x width) y) (clj->js {:rotation 40})))
  (.add svg-mask (.transform (.move (.fill (.rect svg (* 1.5 width) (* 1.5 width)) color-2) (- x (/ width 2)) (+ y (/ width 2))) (clj->js {:rotation 60})))
  (.add svg-mask (.transform (.move (.fill (.rect svg (* 1.3 width) (* 1.3 width)) color-3) (- x width) (+ y (* width 1.5))) (clj->js {:rotation 25})))
  (.add svg-mask (.transform (.move (.fill (.rect svg width width) color-4) (- x (/ width 2)) (+ y (* width 2.5))) (clj->js {:rotation 45}))))

(defn arrow-detail-stars [color-1 color-2 color-3 color-4 scale x y]
  (.add svg-mask (.transform (.transform (.move (.fill (.path svg s/star-path) color-1) (- x (* scale 10)) y) (clj->js {:scaleX scale :scaleY scale})) (clj->js {:rotation 20})))
  (.add svg-mask (.transform (.move (.fill (.path svg s/star-path) color-2) (- x (* scale 15)) (+ y (* scale 15))) (clj->js {:scaleX (* scale 1.3) :scaleY (* scale 1.3)})))
  (.add svg-mask (.transform (.move (.fill (.path svg s/star-path) color-3) (+ x scale) (+ y (* scale 25))) (clj->js {:scaleX (* scale 1.3) :scaleY (* scale 1.3)})))
  (.add svg-mask (.transform (.transform (.move (.fill (.path svg s/star-path) color-4) (- x (* scale 5)) (+ y (* scale 40))) (clj->js {:scaleX scale :scaleY scale})) (clj->js {:rotation 40}))))

(defn draw-event-detail-tooltip [color-1 color-2 scale x y event]
  (.add svg-mask (.transform (.transform (.move (.fill (.path svg s/tooltip-path) color-1) (- x 120) (- y 105)) (clj->js {:scaleX scale :scaleY scale})) (clj->js {:rotation 20})))
  (.add svg-mask (.move (.fill (.text svg (clj->js (:description event))) color-2) (+ x 90) y))
  ;(.add svg-mask (.move (.fill (.rect svg 550 50) color-1) (+ x 90) y))
  )

(defn draw-single-experience [event y]
  (if event
    (let [] (arrow-body-segment s/shade-medium 2 s/event-height "" s/arrow-x (+ y s/arrow-y))
            (if (= s/job (:type event))
              (arrow-detail-circles s/shade-light s/shade-dark s/shade-medium 10 s/arrow-x (+ 15 y s/arrow-y)))
            (if (= s/education (:type event))
              (arrow-detail-triangles s/shade-medium s/shade-dark 5 s/arrow-x (+ 20 y s/arrow-y)))
            (if (= s/speaking (:type event))
              (arrow-detail-squares s/shade-dark s/shade-medium s/shade-light s/shade-medium 10 s/arrow-x (+ 5 y s/arrow-y)))
            (if (= s/special (:type event))
              (arrow-detail-stars s/shade-light s/shade-medium s/shade-dark s/shade-light 1 s/arrow-x (+ y s/arrow-y)))
            (draw-event-detail-tooltip s/shade-medium "#fff" 0.2 s/arrow-x (+ y s/arrow-y) event)
            s/event-height)
    (let [] (arrow-body-segment s/shade-medium 2 s/no-event-height "1,2" s/arrow-x (+ y s/arrow-y))
            s/no-event-height)))

(def current-height (atom {}))

(defn draw-timeline []
  (let [start-year (first (apply min (map #(vector (first %)) (s/tr [:experience/timeline]))))]
    (arrow-tail s/shade-medium s/event-height 30 s/arrow-x s/arrow-y)
    (swap! current-height assoc :value s/event-height)
    (doseq [[year exp] (s/tr [:experience/timeline])]
      (doseq [n (range s/min-event-count-per-year)]
        (swap! current-height update-in [:value] + (draw-single-experience (get exp n) (:value @current-height)))))
    (arrow-head s/shade-medium s/event-height 30 s/arrow-x (+ (:value @current-height) s/arrow-y)))
  (.maskWith svg-rect svg-mask))

(defn timeline []
  (let [] (.clear svg-mask)
          (draw-timeline)
          nil))

