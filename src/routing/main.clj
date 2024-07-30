(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

(defn not-found
  []
  {:status 404})

(def routes
  {{:get "/lists"} "Lists page"
   {:get "/info"} "Info page"})

(defn handler
  [request]
  (let [route {(:request-method request) (:uri request)}
        body (get routes route)]
    (if body
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body body}
      {:status 404
       :headers {"Content-Type" "text/html"}
       :body "Not found"})))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (identity routes))
