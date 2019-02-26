(ns portfolio.projects
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [reagent.core :as r]
    [cljs-http.client :as http]
    [cljs.core.async :refer [<!]]
    [portfolio.resources :as s]))

(defn grid-item [overview description]
  [:label
   [:input {:type "checkbox" :name "toggle-description"}]
   [:div.overview overview]
   [:div.description description]])

;; -- Projects (github api fetch)
(defn build-project-overview [project]
  (let [class (if (:fork project) :div.wrapper.fork :div.wrapper.source)]
    [class [:h3 (:name project)] [:p (:description project)]]))

(defn build-project-description [project]
  [:div.wrapper
   [:p (:description project)]
   [:div.details (s/svg-icon-watchers) [:span (:watchers_count project)] (s/svg-icon-star) [:span (:stargazers_count project)] (s/svg-icon-fork) [:span (:forks_count project)]]])

(defn parse-projects [projects]
  (if (seq (first projects))
    (cons
      (grid-item (build-project-overview (first projects)) (build-project-description (first projects)))
      (parse-projects (rest projects)))))

; this is ugly
(defn fetch-projects []
  (go (let [response (<! (http/get s/github-api-repos-url
                                   {:with-credentials? false}))]
        (if (= (:status response) 200)
          (let [repositories (sort-by :stargazers_count #(> %1 %2) (seq (:body response)))]
            (r/render [:div.grid (for [item (parse-projects repositories)] ^{:key item} [:div.grid-item item])
                       [:div.grid-item.example (grid-item [:img {:src s/about-img-url}] [:p "asdasdasd"])]]
                      (.getElementById js/document "projects")))))))

