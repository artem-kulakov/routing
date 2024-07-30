(ns routing.main
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.pprint :as pp]))

(def not-found
  "Not found")

(def routes
  {{:get "/lists"} "Lists page"
   {:get "/info"} "Info page"})

(defn handler
  [request]
  (let [route {(:request-method request) (:uri request)}
        body (get routes route)
        status (if body 200 404)]
      {:status status
       :headers {"Content-Type" "text/html"}
       :body (or body not-found)}))

(def app
  (wrap-reload handler))

(defn -main
  [& args]
  (jetty/run-jetty app {:port 3000}))

(comment
  (identity routes))
