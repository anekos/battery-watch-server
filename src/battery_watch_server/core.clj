(ns battery-watch-server.core
  (:require [clojure.walk :refer [keywordize-keys]]
            [ring.util.response :refer [charset header response]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as string]
            [environ.core :refer [env]]
            [battery-watch-server.api :as api]))


(def client
  (let [{{host :host port :port} :redis} env]
    (api/new-client)))


(defroutes router
  (GET "/level" []
       (str (api/levels client)))
  (route/not-found "<h1>Page not found</h1>"))


(def app
  (-> router
    wrap-params))


(defn -main
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
