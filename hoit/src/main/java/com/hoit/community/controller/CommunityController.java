package com.hoit.community.controller;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.community.service.CommunityService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/hoit/community")
public class CommunityController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommunityService communityService;

	@GetMapping(value = "/list.do")
	public String communityList(Model model) throws Exception {
		model.addAttribute("commu", communityService.selectCommunityList());
		return "community/list";
	}
	
	@GetMapping(value = "/view.do")
	public String communityView(@RequestParam Map<String, Object> param, Model model, HttpSession session) throws Exception {
		model.addAttribute("view", communityService.getSelectCommunity(param));
		Object _commucd = param.get("commu_cd");
		if(session.getAttribute("COMMUCD") == null || !_commucd.equals(session.getAttribute("COMMUCD").toString())) {
			session.setAttribute("COMMUCD", _commucd);
			communityService.communityViewCnt(param);
		}
		return "community/view";
	}
	
	@GetMapping(value = "/write.do")
	public String communityWrite() {
		
		return "community/write";
	}
	
	@PostMapping(value = "/write_submit.do")
	public String communityWrite_submit(@RequestParam Map<String, Object> param) throws Exception {
		communityService.insertCommunity(param);
		return "redirect:/hoit/community/list.do";
	}
	
	@GetMapping(value = "/edit.do")
	public String communityEdit(Model model, @RequestParam Map<String, Object> param) throws Exception {
		model.addAttribute("view", communityService.getSelectCommunity(param));
		return "community/edit";
	}
	
	@PostMapping(value = "/edit_submit.do")
	public String communityEdit_submit(@RequestParam Map<String, Object> param) throws Exception {
		communityService.updateCommunity(param);
		return "redirect:/hoit/community/list.do";
	}
	
	@PostMapping(value = "/delete_submit.do")
	@ResponseBody
	public void deleteCommunity(@RequestParam Map<String, Object> param) throws Exception {
		communityService.deleteCommunity(param);
	}
}
