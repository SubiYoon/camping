package com.camp.camping.Controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camp.camping.DTO.CompanyDTO;
import com.camp.camping.DTO.ImageDTO;
import com.camp.camping.DTO.SiteDTO;
import com.camp.camping.DTO.ZoneDTO;
import com.camp.camping.service.ImageService;
import com.camp.camping.service.SiteService;
import com.camp.camping.service.ZoneService;

@Controller
@RequestMapping("/zone")
public class ZoneController {
	
	@Autowired
	ImageService serviceI;
	
	@Autowired
	SiteService serviceS;
	
	@Autowired
	ZoneService serviceZ;
	

	String dir = "zone/";
	
	@RequestMapping("")
	public String main(Model model, HttpSession session, ZoneDTO zoneDTO, ImageDTO imageDTO, SiteDTO siteDTO, CompanyDTO companyDTO) {
		
		List<ZoneDTO> listZ = null;
		List<ImageDTO> listI = null;
		List<SiteDTO> listS = null;
		CompanyDTO company = (CompanyDTO)session.getAttribute("company");
		
		try {
			listZ=serviceZ.selectZone(company.getCompany_code());	//상호코드
			model.addAttribute("zlist", listZ);
		
			listI=serviceI.selectByCompanyCode(company.getCompany_code());	//상호코드
			model.addAttribute("ilist", listI);
		
			listS = serviceS.selectSet(company.getCompany_code());	//상호코드
			model.addAttribute("slist", listS);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("center", dir+"zone");
		return "main";
	}
	
	
	@RequestMapping("/site")
	public String site(Model model, int site_code) {
		
		try {
			SiteDTO site = serviceS.selectView(site_code);
			model.addAttribute("site", site);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("center", dir+"site");
		return "main";
	}
	
}