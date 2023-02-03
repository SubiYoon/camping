package com.camp.admin.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camp.admin.DTO.BookDTO;
import com.camp.admin.DTO.CompanyDTO;
import com.camp.admin.DTO.GraphDTO;
import com.camp.admin.DTO.HomeDTO;
import com.camp.admin.DTO.SiteDTO;
import com.camp.admin.DTO.UserDTO;
import com.camp.admin.DTO.ZoneDTO;
import com.camp.admin.service.BookService;
import com.camp.admin.service.HomeService;
import com.camp.admin.service.ImageService;
import com.camp.admin.service.ReviewService;
import com.camp.admin.service.SiteService;
import com.camp.admin.service.ZoneService;

@RestController
public class AjaxController {
    
    @Autowired
    SiteService sService;
    @Autowired
    ZoneService zService;
    @Autowired
    BookService bService;
    @Autowired
    HomeService hService;
    @Autowired
    ImageService iService;
    @Autowired
    ReviewService reviewService;
    @RequestMapping("selectDate")
    public Object selectDate(String selectDate1, String selectDate2, String company_code){
        String change = null;
        List<SiteDTO> sites = null;
        List<ZoneDTO> zones =null;
        JSONObject json = new JSONObject();

        Date date1 = new Date(Integer.parseInt(selectDate1.split("-")[0]),Integer.parseInt(selectDate1.split("-")[1]),Integer.parseInt(selectDate1.split("-")[2]));
        Date date2 = new Date(Integer.parseInt(selectDate2.split("-")[0]),Integer.parseInt(selectDate2.split("-")[1]),Integer.parseInt(selectDate2.split("-")[2]));

        if(date1.after(date2)){
            change = selectDate1;
            selectDate1 = selectDate2;
            selectDate2 = change;
        }
        
        try {
            sites = sService.AvailableSite(Integer.parseInt(company_code), selectDate1, selectDate2);
            zones = zService.selectAll();
            json.put("availableSite", sites);
            json.put("zoneList", zones);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("실패");
        }

        json.put("book_checkin", selectDate1);
        json.put("book_checkout", selectDate2);
        
        return json;
    }
    
    @RequestMapping("userBook")
    public Object userBookList(HttpSession session){
        JSONObject json = new JSONObject();
        List<BookDTO> userBookList = null;
        
		UserDTO user = (UserDTO)session.getAttribute("user");
           
		userBookList = bService.selectUserAll(user.getUser_code());
	
		if(userBookList.size()>10){
			List<BookDTO> userBookListLimit = new ArrayList<BookDTO>();
			for(int i= 0; i<12; i++){
				userBookListLimit.add(userBookList.get(i));
			}
			json.put("userBookList", userBookListLimit);
		}else{
			json.put("userBookList", userBookList);
		}

        return json;
    }

    @RequestMapping("boardEdit")
    public Object boardEdit(HttpSession session, int home_code){
        HomeDTO home = null;
        JSONObject json = new JSONObject();
        CompanyDTO company = (CompanyDTO)session.getAttribute("company");
        
        if(home_code > 0){
            try {
                home = hService.select(company.getCompany_code());
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("실패");
            }

            json.put("home", home);
        
        }
        return json;
    }

    @RequestMapping("logoEdit")
    public Object logoEdit(HttpSession session){
        JSONObject json = new JSONObject();
        CompanyDTO company = (CompanyDTO)session.getAttribute("company");
        
        json.put("company", company);

        return json;
    }

    @RequestMapping("admin.monthly")
    public Object DailySales() throws Exception {
        GraphDTO graph = bService.MonthSalesGraph("2023-02",1);
        JSONObject json = new JSONObject();
        json.put("Graph",graph);
        return json;
    }
    @RequestMapping("admin.monthlyZone")
    public Object monthlyZoneSales() throws Exception {
        GraphDTO graph = bService.MonthlyZoneSalesGraph("2023-02",1);
        JSONObject json = new JSONObject();
        json.put("Graph",graph);
        return json;
    }
    @RequestMapping("admin.monthlyZoneUser")
    public Object monthlyUser() throws Exception {
        GraphDTO graph = bService.MonthlyZoneUserGraph("2023-02",1);
        JSONObject json = new JSONObject();
        json.put("Graph",graph);
        return json;
    }
    @RequestMapping("admin.ReviewScore")
    public Object ReviewScore() throws Exception {
        GraphDTO graph = reviewService.ReviewScoreGraph(1);
        JSONObject json = new JSONObject();
        json.put("Graph",graph);
        return json;
    }
}
