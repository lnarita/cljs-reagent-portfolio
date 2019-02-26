(ns ^:figwheel-no-load portfolio.dev
  (:require
    [portfolio.core :as core]
    [figwheel.client :as figwheel :include-macros true]
    [devtools.core :as devtools]
    [reagent.core :as r]))


(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback (fn [] (core/mount-root)))

(enable-console-print!)

(devtools/install!)

(core/init!)
