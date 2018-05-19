package com.oxygen.yallagoom.entity.TicketClasses;

import com.oxygen.yallagoom.entity.CategoryDetails;
import com.oxygen.yallagoom.entity.CountryDetails;
import com.oxygen.yallagoom.entity.TicketClasses.CategoryTicketsListCount;
import com.oxygen.yallagoom.entity.TicketClasses.LikesCount;
import com.oxygen.yallagoom.entity.TicketClasses.TicketInfo;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/25/2018.
 */

public class Discover {
    private ArrayList<CategoryDetails> category_list;
    private CountryDetails country_data;
    private Recommended recommended;
    private Offers offers;

    public ArrayList<CategoryDetails> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(ArrayList<CategoryDetails> category_list) {
        this.category_list = category_list;
    }

    public CountryDetails getCountry_data() {
        return country_data;
    }

    public void setCountry_data(CountryDetails country_data) {
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



    public class Recommended {
        ArrayList<TicketInfo> data;

        public ArrayList<TicketInfo> getData() {
            return data;
        }

        public void setData(ArrayList<TicketInfo> data) {
            this.data = data;
        }



    }

    public class Offers {
        ArrayList<TicketInfo> data;

        public ArrayList<TicketInfo> getData() {
            return data;
        }

        public void setData(ArrayList<TicketInfo> data) {
            this.data = data;
        }



    }
}
