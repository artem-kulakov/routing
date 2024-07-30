(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

(defn lists
  []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Lists page"})

(defn info
  []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Info page"})

(defn not-found
  []
  {:status 404})

(def routes
  {{:get "/lists"} (lists)
   {:get "/info"} (info)})

(defn handler
  [request]
  (let [route {(:request-method request) (:uri request)}]
    (get routes route (not-found))))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (identity routes))
