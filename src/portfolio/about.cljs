(ns portfolio.about
  (:require
    [portfolio.resources :as s]))

(defn about-text-summary []
  (for [x (clojure.string/split-lines (s/tr [:about/summary]))] ^{:key x} [:p x]))

(defn about-page-text []
  [:div.about-text.pure-u-1.pure-u-md-5-8 [:h1.name (s/tr [:about/name])]
   [:h2.surname (s/tr [:about/surname])]
   (about-text-summary)])

(defn about-page-img []
  [:div.about-pic.pure-u-1.pure-u-md-3-8 [:div.about-pic__wrapper [:div.about-pic__image.image-texture {:style {:background-image  (str "url('" s/about-img-url "')")
                                                                              :background-repeat "no-repeat"}}]]])
(defn about-page []
  [:div.about.pure-g
   (about-page-text)
   (about-page-img)])

