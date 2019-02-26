(ns portfolio.cover
  (:require
    [reagent.core :as r]
    [portfolio.resources :as s]
    [portfolio.writer :as w]
    [portfolio.footer :as f]))


(defn typed-animation []
  [:div.typed @f/about-text])

(w/writer (w/build-typing (s/tr [:who-am-i/text])) (fn [value] (reset! f/about-text value)) 110)