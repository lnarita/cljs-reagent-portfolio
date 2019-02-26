(ns portfolio.footer
  (:require
    [portfolio.resources :as s]
    [portfolio.writer :as w]
    [reagent.core :as r]))

(def about-text (r/atom ""))

(defn change-lang []
  [:div (for [[key strings] (:dict s/opts)]
          ^{:key key} [:a.lang-selection {:on-click (fn [] (reset! s/lang [key])
                                                              (w/writer (w/build-typing (s/tr [:who-am-i/text])) (fn [value] (reset! about-text value)) 110))}
                       key])])

(defn footer []
  (change-lang))