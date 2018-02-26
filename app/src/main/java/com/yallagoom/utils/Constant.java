package com.yallagoom.utils;

import com.yallagoom.entity.Country;
import com.yallagoom.entity.Event;
import com.yallagoom.entity.User;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public class Constant {
    public static String uploads="uploads";
    public static String events_images="events_images";
    public static String api="api";
    public static String urlData="http://devyallagoom.com/"+api+"/";
    public static String imageUrl="http://devyallagoom.com/"+uploads+"/";
    public static String urlImage="http://devyallagoom.com/";
    public static String API_KEY="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjM0MmY3OTVlYWJmZDc3NDQ1Njg0OGM3NzI4MTVmZDc0MzA4NzlmMjUzYWFmMjRiNzQzMGQ3MDIwNmVkZTczZTYwY2Y4MzUzMjJhNThiMTYzIn0.eyJhdWQiOiI5IiwianRpIjoiMzQyZjc5NWVhYmZkNzc0NDU2ODQ4Yzc3MjgxNWZkNzQzMDg3OWYyNTNhYWYyNGI3NDMwZDcwMjA2ZWRlNzNlNjBjZjgzNTMyMmE1OGIxNjMiLCJpYXQiOjE1MTY0NTI3NjAsIm5iZiI6MTUxNjQ1Mjc2MCwiZXhwIjoxNTQ3OTg4NzYwLCJzdWIiOiIiLCJzY29wZXMiOltdfQ.GmXg6SCHW-ZTaJxyTIm6zEefGzAivz6NpfXxlzNMSs5srF9NIRmtM_F-f7DD0o_csVipRP6lpNmjNbMP_6bCpDsHQuCbr7FJ2NKT9zEnbuund38D35dHdcItDoi7T_IyhtvUkXqns-ElzXi5QXVAru676EG2R7sKNip8oPIn1XfWmYd7FczAKuC8P7cD_JsRZFrugwDIZt60ts6D6TQgIjEUoy5CiWlZYkNp37DwrhCrQ0VZNBVEI2mW0aL0zI7v5is-eVR0JSyWUCWAJEVTXQgFlgZK_jCA7qXMvaqVBXUs4pqYq3cAVyHHn9mvDbX5mEQHe_mEY3oG-2z01aCYje-wgc0xWdXbZiT37zbjN0pRh1pEscCHDxoHC04HLPC2fSDS2hTA3rzqYYpg05S_xqMX30OTMQFb_ro64iiAklEYM58UhWSovQ9NU6pjzQ-LK-VYPzQC-Wb3Vc2E5ZCxPIJzcSq7ap8ajeSCAFLW-UOsygck3sbZN42sS7ztnjmCUErT5_rzWqPDPUdCg_Av0RuS1SVAIiAzGRStaea3HfnPpRRJJf2KxrD9rIuMp0pHLR6b3xP5u-aYOD1jB11huRuLPBbeX9Olnbn0LmmkFm4qa9x3_03hpgxJlX77NCVpYCCpHdv0Hy8bI18Fdp-7j3B8xO5fP2Z__nqTkR1bev8";
    public static String status_callback="status";
    public static String error_callback="errors";
    public static String countries="countries";
    public static String loginCheck="loginCheck";
    public static String verification_check="verification_check";
    public static Country countriesData;
    public static String login="login";
    public static String nearby="event/nearby";
    public static String usersevents="event/usersevents";
    public static String MyEventsHome="event/MyEventsHome";
    public static String players="players/search";
    public static String friend_search="friend/search";
    public static String show_event="event/show";
    public static String data="data";
    public static User user;
    public static String sports="sports";
    public static String groups="groups/show_my_and_others_groups";
    public static String delete_event="event/delete";
    public static String FriendsEvents="event/FriendsEvents";
    public static String delete_groups="groups/delete";
    public static String delete_friend="friend/delete";
    public static String lat="lat";
    public static String lng="lng";
    public static String distance="distance";
    public static boolean click=false;
    public static ArrayList<Event.DataEvent> eventsData;
    public static String register="register";
    public static String userData="userData";
    public static String userToken="userToken";
    public static String user_sport="user-sports";
    public static String defultDistance="5";
    public static String search_event="event/search";
    public static String EventInvitation="EventInvitation/send";
    public static String ResponseToInvitation="EventInvitation/ResponseToInvitation";
    public static String add_event="event/store";
    public static String update_event="event/update";
    public static String block_friend="friend/block";
    public static String add_friend="friend/add";
    public static String accept_friend="friend/accept";
    public static String add_group="groups/store";
    public static String reject_friend="friend/reject";
    public static String MyFriendList="friend/getMyFriendList";
    public static String getNewRequest="friend/getNewRequest";
    public static String ticket="ticket";
    public static String discover_ticket=ticket+"/discover";
    public static String categories="categories";
    public static String yyyy_MM_dd="yyyy-MM-dd";
    public static String dd_MMM_yyyy="dd MMM yyyy";
    public static String EEEE_dd_MMM_yyyy="EEEE, dd MMM yyyy";
    public static String HH_mm_ss="HH:mm:ss";
    public static String hh_mm_aa="hh:mm aa";
    public static String userId="userId";
    public static String country_code_url="http://ip-api.com/json";
}
