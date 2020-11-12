(ns links-ui.views
  (:require
   [re-frame.core :as re-frame]
   [links-ui.subs :as subs]
   ))

(defn add-button []
  [:div.columns.is-centered {:style {:margin-top "10px" }}
    [:div.column.is-half
      [:div.field.has-addons.has-text-centered
        [:div.control.has-icons-left.add-link 
          [:input.input]
          [:span.icon.is-small.is-left
            [:i.fa.fa-link]]]
        [:div.control
          [:button.button.is-dark "Submit"]]]]])

(defn link [{:keys [url title icon]}]
  [:div.column.is-full
      [:div.card
        [:div.card-content
          [:article.media
            [:figure.media-left
              [:p.image.is-64x64
                [:img {:src icon}]]]
            [:div.media-content
              [:div.content.link
                [:a {:href url :target "_blank"}
                  [:h4 title]]]]]]]])

(defn link-list []
  (let [links (re-frame/subscribe [::subs/links])]
    [:div.columns.is-multiline
      (for [l @links]
        ^{:key (:id l)}
        [link l])]))

;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div [:nav.navbar.is-warning {:role "navigation"}
        [:div.navbar-brand
          [:a.navbar-item {:href "/"}
            [:h1.navbar-title "Links"]]]]
      [:div.container
        [add-button]
        [link-list]]]))



;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
