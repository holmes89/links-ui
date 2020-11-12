(ns links-ui.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]  
   [re-frame.core :as re-frame]
   [links-ui.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db                   
    ::process-response             
  (fn
    [db [_ response]]
    (-> db
        (assoc :loading? false)
        (assoc :links (js->clj response)))))


(re-frame/reg-event-db                   
    ::bad-response             
  (fn
    [message]
    (js/console.log  message)))


(re-frame/reg-event-fx
    ::get-links
  (fn
    [{db :db} _]
    {:http-xhrio {:method          :get
                  :uri             "https://links-api-4josd7vm2q-ue.a.run.app/api/links/"
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true}) 
                  :on-success      [::process-response]
                  :on-failure      [::bad-response]
                  }
     :db  (assoc db :loading? true)}))