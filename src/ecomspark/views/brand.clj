(ns ecomspark.views.brand
  (:require [hiccup.core :as hi]
            [ecomspark.utilelements :as utilelements]))

(defn replace-dot-with-comma [num]
  (clojure.string/replace (str num) "." ","))

(defn Star [empty]
  (hi/html
    [:svg
      {:width "16",
        :height "14",
        :viewBox "0 0 16 14",
        :fill "none",
        :xmlns "http://www.w3.org/2000/svg"}
      [:path
        {:d
        "M3.54036 13.6979C3.80729 13.9062 4.14583 13.8346 4.54947 13.5417L7.99348 11.0091L11.444 13.5417C11.8477 13.8346 12.1797 13.9062 12.4531 13.6979C12.72 13.4961 12.7786 13.1641 12.6159 12.6953L11.2552 8.63932L14.7318 6.14583C15.1354 5.85938 15.2982 5.55339 15.194 5.23438C15.0898 4.91536 14.7838 4.75911 14.2825 4.76562L10.0182 4.79818L8.72265 0.722656C8.5664 0.247396 8.33203 0 7.99348 0C7.66145 0 7.42708 0.247396 7.27083 0.722656L5.97526 4.79818L1.71093 4.76562C1.20963 4.75911 0.903641 4.91536 0.799474 5.23438C0.688797 5.55339 0.858068 5.85938 1.26171 6.14583L4.73828 8.63932L3.3776 12.6953C3.21484 13.1641 3.27343 13.4961 3.54036 13.6979Z",
        :fill (if empty "#E5E5EA" "#FF7752")}]]))

(defn SubscribeResult [info]
  (let [id (get info :id)]
  (hi/html
    [:form {:ts-req "/brand/add"
            :ts-req-method "delete"
            ;; :ts-req-before "attr inert true" ;; would be good to prevent spamming if the request takes too long but the project's TwinSpark doesn't support these newer actions
            ;; :ts-req-after "attr inert false" ;; would be good to prevent spamming if the request takes too long but the project's TwinSpark doesn't support these newer actions
           }
     [:input {:type "hidden" :name "id" :value id}]
     [:button.followed (utilelements/checkmark) "Відстежується"]])))

(defn UnsubscribeResult [info]
  (let [id (get info :id)]
  (hi/html
    [:form {:ts-req "/brand/add"
            :ts-req-method "post"
            ;; :ts-req-before "attr inert true" ;; would be good to prevent spamming if the request takes too long but the project's TwinSpark doesn't support these newer actions
            ;; :ts-req-after "attr inert false" ;; would be good to prevent spamming if the request takes too long but the project's TwinSpark doesn't support these newer actions
           }
     [:input {:type "hidden" :name "id" :value id}]
     [:button.follow "Стежити"]])))

(defn BrandItem [id pic name rating stars follow-status]
  [:div.brand-item
   [:div.brand-item-left
    [:img {:src pic :alt name :style "width: 64px; height: 64px; border-radius: 5px;"}]]
   [:div.stretched-wrappable
    [:div.brand-item-middle
      [:div.brand-item-title name]
      [:div.brand-item-rating
      [:span {:style "font-size: 1.2em; font-weight: bold;"} (replace-dot-with-comma rating)]
      [:div
      (for [i (range 1 6)]
        [:span.star (star (> i rating))])]
      [:span.review-count (str stars " оцінок")]]]
    [:div.brand-item-right
      (if follow-status (SubscribeResult {:id id}) (UnsubscribeResult {:id id}))]]])

(defn BrandList [{:keys [brands offset]}]
    (hiccup.core/html
    [:div#brands
    (for [b brands]
      (let [{:keys [id pic name rating stars]} b
            follow-status (even? (hash name))]
        (BrandItem id pic name rating stars follow-status))
        )
    [:div {:id "infinite-scroll-trigger"
            :ts-req "/brands"
            :ts-data (str "offset=" offset)
            :ts-trigger "visible"
            :ts-swap "replace"
            :ts-target "#infinite-scroll-trigger"}]]))
