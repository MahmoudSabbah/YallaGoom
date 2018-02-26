package com.yallagoom.entity;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/25/2018.
 */

public class Discover {
    private ArrayList<CategoryList> category_list;
    private CountryData country_data;
    private Recommended recommended;
    private Offers offers;

    public ArrayList<CategoryList> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(ArrayList<CategoryList> category_list) {
        this.category_list = category_list;
    }

    public CountryData getCountry_data() {
        return country_data;
    }

    public void setCountry_data(CountryData country_data) {
        this.country_data = country_data;
    }

    public Recommended getRecommended() {
        return recommended;
    }

    public void setRecommended(Recommended recommended) {
        this.recommended = recommended;
    }

    public Offers getOffers() {
        return offers;
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
    }

    public class CategoryList {
        private int id;
        private String CategoryName;
        private String updated_at;
        private String created_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String categoryName) {
            CategoryName = categoryName;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public class CountryData {
        private int id;
        private String name_en;
        private String name_ar;
        private String code;
        private String code_3;
        private String phone_code;
        private String flag;
        private String img_url;
        private String created_at;
        private String updated_at;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName_ar() {
            return name_ar;
        }

        public void setName_ar(String name_ar) {
            this.name_ar = name_ar;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_3() {
            return code_3;
        }

        public void setCode_3(String code_3) {
            this.code_3 = code_3;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public void setPhone_code(String phone_code) {
            this.phone_code = phone_code;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    public class Recommended {
        ArrayList<Data> data;

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {
            private int id;
            private int quantity;
            private int max_per_order;
            private int min_per_order;
            private int tickets_category_id;
            private int refundable;
            private int country_id;
            private String title;
            private String time;
            private String date;
            private String description;
            private String sale_start_date;
            private String sale_end_date;
            private String img_url;
            private String is_offer;
            private double price;
            private String price_after_discount;
            private String address_id;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getMax_per_order() {
                return max_per_order;
            }

            public void setMax_per_order(int max_per_order) {
                this.max_per_order = max_per_order;
            }

            public int getMin_per_order() {
                return min_per_order;
            }

            public void setMin_per_order(int min_per_order) {
                this.min_per_order = min_per_order;
            }

            public int getTickets_category_id() {
                return tickets_category_id;
            }

            public void setTickets_category_id(int tickets_category_id) {
                this.tickets_category_id = tickets_category_id;
            }

            public int getRefundable() {
                return refundable;
            }

            public void setRefundable(int refundable) {
                this.refundable = refundable;
            }

            public int getCountry_id() {
                return country_id;
            }

            public void setCountry_id(int country_id) {
                this.country_id = country_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSale_start_date() {
                return sale_start_date;
            }

            public void setSale_start_date(String sale_start_date) {
                this.sale_start_date = sale_start_date;
            }

            public String getSale_end_date() {
                return sale_end_date;
            }

            public void setSale_end_date(String sale_end_date) {
                this.sale_end_date = sale_end_date;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getIs_offer() {
                return is_offer;
            }

            public void setIs_offer(String is_offer) {
                this.is_offer = is_offer;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getPrice_after_discount() {
                return price_after_discount;
            }

            public void setPrice_after_discount(String price_after_discount) {
                this.price_after_discount = price_after_discount;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }


    }

    public class Offers {
        ArrayList<Data> data;

        public ArrayList<Data> getData() {
            return data;
        }

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        public class Data {
            private int id;
            private int quantity;
            private int max_per_order;
            private int min_per_order;
            private int tickets_category_id;
            private int refundable;
            private int country_id;
            private String title;
            private String time;
            private String date;
            private String description;
            private String sale_start_date;
            private String sale_end_date;
            private String img_url;
            private String is_offer;
            private double price;
            private String price_after_discount;
            private String address_id;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getMax_per_order() {
                return max_per_order;
            }

            public void setMax_per_order(int max_per_order) {
                this.max_per_order = max_per_order;
            }

            public int getMin_per_order() {
                return min_per_order;
            }

            public void setMin_per_order(int min_per_order) {
                this.min_per_order = min_per_order;
            }

            public int getTickets_category_id() {
                return tickets_category_id;
            }

            public void setTickets_category_id(int tickets_category_id) {
                this.tickets_category_id = tickets_category_id;
            }

            public int getRefundable() {
                return refundable;
            }

            public void setRefundable(int refundable) {
                this.refundable = refundable;
            }

            public int getCountry_id() {
                return country_id;
            }

            public void setCountry_id(int country_id) {
                this.country_id = country_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSale_start_date() {
                return sale_start_date;
            }

            public void setSale_start_date(String sale_start_date) {
                this.sale_start_date = sale_start_date;
            }

            public String getSale_end_date() {
                return sale_end_date;
            }

            public void setSale_end_date(String sale_end_date) {
                this.sale_end_date = sale_end_date;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getIs_offer() {
                return is_offer;
            }

            public void setIs_offer(String is_offer) {
                this.is_offer = is_offer;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getPrice_after_discount() {
                return price_after_discount;
            }

            public void setPrice_after_discount(String price_after_discount) {
                this.price_after_discount = price_after_discount;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

    }
}
