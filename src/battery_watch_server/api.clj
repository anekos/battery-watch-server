(ns battery-watch-server.api
  (:require [clj-redis.client :as redis]
            [clojure.walk :refer [keywordize-keys]]
            [environ.core :refer [env]]
            [clj-time.local :refer [local-now]]
            [clj-time.format :refer [parse]]
            [meowdule.string :refer [remove-prefix]])
  (:refer-clojure :exclude [update get]))


(declare new-client get-all-levels update-level get-level)


(defn new-client [& {:as opts}]
  (redis/init opts))


(def key-prefix "battery-level-")


(defn- make-key [name]
  (str key-prefix name))


(defn get-all-levels [c]
  (->>
    (redis/keys c (make-key "*"))
    (map (fn [k]
           (let [k (remove-prefix key-prefix k)]
           [k (get-level c k)])))
    (into {})
    keywordize-keys))


(defn update-level [c device-name level]
  (redis/hmset
    c
    (make-key device-name)
    {"at" (str (local-now))
     "level" (str level)}))


(defn get-level [c device-name]
  (let [{at "at"
         level "level"} (redis/hgetall c (make-key device-name))]
    {:at (parse at)
     :level (Integer. level)}))
