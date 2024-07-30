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

(def not-found
  {:status 404})

(defn handler
  [request]
  ;; (pp/pprint request)
  (let [route {(:request-method request) (:uri request)}]
    (case route
      {:get "/lists"} (lists)
      {:get "/info"} (info)
      not-found)))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (lists))
