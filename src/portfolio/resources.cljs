(ns portfolio.resources
  (:require [taoensso.tempura :as tempura]
            [reagent.core :as r]))

(defn long-str [& strs] (clojure.string/join "\n" strs))

(def job "job")
(def education "education")
(def speaking "talks")
(def special "hooray")

(def strings
  {:en    {:missing    "n.o.p.e."
           :who-am-i   {:text (fn [] ["Placeholder #1" "Placeholder #2"] )}
           :about      {:name    "John"
                        :surname "Doe"
                        :summary (long-str "The sorrows of young Werther"
                                           "A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart."
                                           "I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so happy, my dear friend, so absorbed in the exquisite sense of mere tranquil existence, that I neglect my talents.")}
           :experience {:timeline (fn [] {:2017 [{:type        job
                                                  :title       "Job Position #1"
                                                  :where       "@ A Nice Company"
                                                  :when        "Jan"
                                                  :description (long-str "A brief description of what you did"
                                                                         "Maybe not so brief"
                                                                         "Quite a long description?")}]
                                          :2016 [{:type        special
                                                  :title       "A special event?"
                                                  :where       "@ Somewhere"
                                                  :when        "Jan"
                                                  :description (long-str "A brief description of what happened"
                                                                         "And a long description of why it was special")}
                                                 {:type        education
                                                  :title       "College?"
                                                  :where       "in A Nice University"
                                                  :when        "Jan"
                                                  :description (long-str "Your Major"
                                                                         "And why it was a long path of sadness and sorrow")}]
                                          :2015 [{:type        speaking
                                                  :title       "Lightning Talk"
                                                  :where       "@ Big Meetup"
                                                  :when        "Dez"
                                                  :description (long-str (long-str "Talk description"))
                                                  }]})}
           }
   :pt-br {:missing    "nay"
           :who-am-i   {:text (fn [] ["Qwerty" "Azerty" "Dvorak"])}
           :about      {:name    "Jose"
                        :surname "Silva"
                        :summary (long-str "Gerador de Lero Lero"
                                           "É claro que a estrutura atual da organização agrega valor ao estabelecimento dos procedimentos normalmente adotados."
                                           "Neste sentido, a complexidade dos estudos efetuados faz parte de um processo de gerenciamento das regras de conduta normativas."
                                           "No entanto, não podemos esquecer que a contínua expansão de nossa atividade é uma das consequências do sistema de formação de quadros que corresponde às necessidades.")}
           :experience {:timeline (fn [] {:2017 [{:type        job
                                                  :title       "Job Position #2"
                                                  :where       "@ Empresa #1"
                                                  :when        "Jan"
                                                  :description (long-str "Pequena descrição do trabalho"
                                                                         "Talvez nem tão pequena"
                                                                         "Uma descrição longa?")}]
                                          :2016 [{:type        special
                                                  :title       "Um evento especial?"
                                                  :where       "@ Algum lugar"
                                                  :when        "Jan"
                                                  :description (long-str "Breve descrição do acontecimento"
                                                                         "E porque foi especial")}
                                                 {:type        education
                                                  :title       "Faculdade"
                                                  :where       "em uma Universidade Pública"
                                                  :when        "Jan"
                                                  :description (long-str "Sobre o curso"
                                                                         "E sobre sua jornada de dor e sofrimento")}]
                                          :2015 [{:type        speaking
                                                  :title       "Lightning Talk"
                                                  :where       "@ Meetup X"
                                                  :when        "Dez"
                                                  :description (long-str (long-str "Descrição de uma palestra"))
                                                  }]})}
           }
   :jp    {:missing    "あれ？"
           :who-am-i   {:text (fn [] [{:read "yabai," :out "ヤバイ、"} {:read "nani" :out "何"} {:read "kaitokouka" :out "書いとこうか"}])}
           :about      {:name    "山田"
                        :surname "一郎"
                        :summary (long-str "心"
                                           "私はその人を常に先生と呼んでいた。"
                                           "だからここでもただ先生と書くだけで本名は打ち明けない。"
                                           "これは世間を憚かる遠慮というよりも、その方が私にとって自然だからである。")}
           :experience {:timeline (fn [] {:2017 [{:type        job
                                                  :title       "Job Position #2"
                                                  :where       "@ A Nice Company"
                                                  :when        "Jan"
                                                  :description (long-str "なんか知らん")}]
                                          :2016 [{:type        special
                                                  :title       "A special event?"
                                                  :where       "@ Somewhere"
                                                  :when        "Jan"
                                                  :description (long-str "A brief description of what happened"
                                                                         "And a long description of why it was special")}
                                                 {:type        education
                                                  :title       "College?"
                                                  :where       "in A Nice University"
                                                  :when        "Jan"
                                                  :description (long-str "Your Major"
                                                                         "And why it was a long path of sadness and sorrow")}]
                                          :2015 [{:type        speaking
                                                  :title       "Lightning Talk"
                                                  :where       "@ Big Meetup"
                                                  :when        "Dez"
                                                  :description (long-str (long-str "Talk description"))
                                                  }]})}
           }
   })

;(def about-img-url "/img/10.jpg")
(def about-img-url "/img/photo.png")
(def protocol "http")
(def github-api-base-url (str protocol ":" "//api.github.com/"))
(def github-user "")
(def github-api-user-url (str github-api-base-url "users/" github-user))
(def github-api-repos-url (str github-api-user-url "/repos"))

(def opts {:dict strings})
(def lang (r/atom [:en]))
(defn tr [data] (tempura/tr opts @lang data))

(defn typed-text [] (clj->js (tr [:who-am-i/text])))

(defn typed-options [] #js {:strings        (typed-text)
                            :typeSpeed      75
                            :backSpeed      75
                            :loop           true
                            :onStringTyped  (js* "function(arrayPos, self) {document.querySelector('.typed').classList.add('semi');setTimeout(function() {document.querySelector('.typed').classList.remove('semi')}, 700)}")
                            :preStringTyped (js* "function(arrayPos, self) {var colors = ['#606060', '#0f829d', '#f03']; document.querySelector('.typed').style.color = colors[arrayPos]}")})

;; -- Timeline
;; ---- dimensions
(def min-event-count-per-year 12)
(def event-height 60)
(def no-event-height 10)
(def svg-max-width 650)
(def svg-max-height 800)
(def arrow-x 30)
(def arrow-y 20)
;; ---- colors
(def shade-light "#fff")
(def shade-medium "#ccc")
(def shade-dark "#888")
(def color-blue "#0fc")
(def color-green "#0f9")
(def color-red "#d91428")
(def svg-color color-red)
;; ---- paths
(def star-path "M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74z")
(def tooltip-path "M80 70.00000038146973C33 70.30000038146973 -10 83.80000038146973 -40.2 105.50000038146973C-71 127.20000038146972 -90 157.10000038146973 -90 190.10000038146973C-90 220.10000038146973 -73.1 249.10000038146973 -45.400000000000006 270.1000003814697C-18 291.1000003814697 21 306.1000003814697 64 308.1000003814697C70 318.1000003814697 81 330.1000003814697 81 330.1000003814697C81 330.1000003814697 92 318.1000003814697 98 308.1000003814697C142 306.1000003814697 180 291.1000003814697 208 270.1000003814697C235 249.10000038146973 252 220.10000038146973 252 190.10000038146973C252 157.10000038146973 233 126.80000038146973 202 105.10000038146973C171 83.40000038146972 128 70.00000038146973 80 70.00000038146973Z ")

;; -- SVG Icons

(def svg-icon-star
  [:svg.icon.star-icon {:height 16 :width 14 :role "img" :viewBox "0 0 14 16"}
   [:path {:fill-rule "evenodd" :d "M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74z"}]])

(def svg-icon-fork
  [:svg.icon.fork-icon {:height 16 :width 10 :role "img" :viewBox "0 0 10 16"}
   [:path {:fill-rule "evenodd" :d "M8 1a1.993 1.993 0 0 0-1 3.72V6L5 8 3 6V4.72A1.993 1.993 0 0 0 2 1a1.993 1.993 0 0 0-1 3.72V6.5l3 3v1.78A1.993 1.993 0 0 0 5 15a1.993 1.993 0 0 0 1-3.72V9.5l3-3V4.72A1.993 1.993 0 0 0 8 1zM2 4.2C1.34 4.2.8 3.65.8 3c0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3 10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3-10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2z"}]])

(def svg-icon-watchers
  [:svg.icon.watchers-icon {:height 16 :width 16 :role "img" :viewBox "0 0 16 16"}
   [:path {:fill-rule "evenodd" :d "M8.06 2C3 2 0 8 0 8s3 6 8.06 6C13 14 16 8 16 8s-3-6-7.94-6zM8 12c-2.2 0-4-1.78-4-4 0-2.2 1.8-4 4-4 2.22 0 4 1.8 4 4 0 2.22-1.78 4-4 4zm2-4c0 1.11-.89 2-2 2-1.11 0-2-.89-2-2 0-1.11.89-2 2-2 1.11 0 2 .89 2 2z"}]])

