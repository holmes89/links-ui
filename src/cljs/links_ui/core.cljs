(ns links-ui.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [links-ui.events :as events]
   [links-ui.routes :as routes]
   [links-ui.views :as views]
   [links-ui.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
