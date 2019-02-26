(ns portfolio.core
  (:require
    [reagent.core :as r]
    [portfolio.resources :as s]
    [portfolio.cover :refer [typed-animation]]
    [portfolio.about :refer [about-page]]
    [portfolio.timeline :refer [timeline]]
    [portfolio.footer :refer [footer]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [#'typed-animation] (.getElementById js/document "typed"))
  (r/render [#'about-page] (.getElementById js/document "about"))
  (r/render [#'footer] (.getElementById js/document "footer"))
  (r/render [#'timeline] (.getElementById js/document "draw-timeline"))
  )

;(fetch-projects)

(defn init! []
  (mount-root))

