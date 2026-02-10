package com.hoit.accountbook.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoit.accountbook.service.AccountBookService;
import com.hoit.category.service.CategoryService;
import com.hoit.common.CursorResponse;
import com.hoit.common.PageVO;
import com.hoit.common.Pagination;
import com.hoit.common.PagingSetting;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/hoit/accountBook")
public class AccountBookController {

	@Autowired
	private AccountBookService accountBookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PagingSetting pageingSetting;

	@GetMapping(value = "/list.do")
	public String list(Model model, @RequestParam Map<String, Object> param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String currentDate = sdf.format(date);
		param.put("ASSET_AT", currentDate);
		Map<String, Object> map = new HashMap<>();
		map.put("USE_YN", "Y");
		Map<String, Object> commonPaging = pageingSetting.CommonPaging(new PageVO(), accountBookService.accountBookCnt());
		
		model.addAttribute("list", accountBookService.accountBookList(commonPaging));
		model.addAttribute("cb", accountBookService.getCurrentMoney());
		model.addAttribute("cg", categoryService.selectCategoryList(map));
		model.addAttribute("totalPageCnt", commonPaging.get("totalPageCnt"));
		model.addAttribute("pageVO", commonPaging.get("pageVO"));
		
		return "accountBook/list";
	}
	
//	@PostMapping(value = "pagingAjax.do")
//	@ResponseBody
//	public void pagingAjax(@RequestParam Map<String, Object> param, HttpServletResponse response, @RequestBody String filterJSON) throws Exception {
//
//	    JSONObject obj = new JSONObject();
//	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
//
//	    try {
//	        ObjectMapper mapper = new ObjectMapper();
//	        PageVO page = mapper.readValue(filterJSON, new TypeReference<PageVO>(){});
//	        
//			int tot = accountBookService.accountBookCnt();
//			Map<String, Object> commonPaging = pageingSetting.CommonPaging(page, tot);
//			
//			param.put("recordCountPerPage", commonPaging.get("recordCountPerPage"));
//			param.put("firstIndex", commonPaging.get("firstIndex"));
//			
//			obj.put("list", accountBookService.accountBookList(param));
//	        obj.put("resultCnt", tot);
//	        obj.put("totalPageCnt", commonPaging.get("totalPageCnt"));
//	        obj.put("pageVO", mapper.writeValueAsString(commonPaging.get("pageVO")));
//
//	    } catch (Exception e) {
//	        obj.put("res", "error");
//	    }
//	    out.print(obj);
//	}
	
	@PostMapping(value = "/scrollList.do")
	@ResponseBody
	public CursorResponse scrollList(@RequestBody Map<String, Object> param) {
		return accountBookService.getScrollList(param);
	}
	
	@PostMapping(value = "/write_submit.do")
	@ResponseBody
	public void write(@RequestBody Map<String, Object> param) {
		System.out.println(param);
		accountBookService.writeAccountBook(param);
	}
	
	@PostMapping(value = "/chargeCash_submit.do")
	@ResponseBody
	public Map<String, Object> saveAsset(@RequestBody Map<String, Object> param) {
		accountBookService.saveAsset(param);
		return param;
	}
	
	@PostMapping(value = "/deleteAccountBook_submit.do")
	@ResponseBody
	public void deleteAccountBook(@RequestBody Map<String, Object> param) {
		accountBookService.deleteAccountBook(param);
	}
	
	@GetMapping(value = "/stats.do")
	public String stats() {
		return "accountBook/stats";
	}
	
	@PostMapping(value = "/getMonthlyAmount.do")
	@ResponseBody
	public Map<String, Object> getMonthlyAmount(@RequestBody Map<String, Object> param) {
		return accountBookService.getMonthlyAmount(param);
	}
	@PostMapping(value = "/getCategoryMonthlyAmount.do")
	@ResponseBody
	public List<Map<String, Object>> getCategoryMonthlyAmount(@RequestBody Map<String, Object> param) {
		return accountBookService.getCategoryMonthlyAmount(param);
	}
	
}
