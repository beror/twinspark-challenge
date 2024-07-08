(ns ecomspark.views.pages
  (:require [hiccup.core :as hi]

            [ecomspark.views.base :as base]
            [ecomspark.views.brand :as brand]
            [ecomspark.views.product :as product]))


(defn Index [opts]
  (base/wrap "EcomSpark"
    (hi/html
      [:h1 "Products"]
      (product/ProductList {:products (:products opts)
                            :offset   (:offset opts)}))))


(defn Cart [opts]
  (base/wrap "Cart - EcomSpark"
    (hi/html
      [:h1 "Cart"]
      (product/ProductList {:cart?    true
                            :products (:products opts)}))))

(defn Brands [opts]
  (base/wrap "Brands - EcomSpark"
    (hi/html
      [:h1.brand-item "Бренди"]
      (brand/BrandList (select-keys opts [:brands :offset])))))
