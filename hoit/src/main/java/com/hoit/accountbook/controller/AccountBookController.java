package com.hoit.accountbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.accountbook.service.AccountBookService;
import com.hoit.cashback.service.CashBackService;
import com.hoit.category.service.CategoryService;
import com.hoit.util.UniqueKey;

@Controller
@RequestMapping(value = "/hoit/accountBook")
public class AccountBookController {

	@Autowired
	private AccountBookService accountBookService;
	
	@Autowired
	private CashBackService cashBackService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/list.do")
	public String list(Model model, @RequestParam Map<String, Object> param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String currentDate = sdf.format(date);
		param.put("ASSET_AT", currentDate);
		Map<String, Object> map = new HashMap<>();
		map.put("USE_YN", "Y");
		model.addAttribute("list", accountBookService.accountBookList());
		model.addAttribute("cb", accountBookService.getCurrentMoney());
		model.addAttribute("cg", categoryService.selectCategoryList(map));
		return "accountBook/list";
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
	
	@PostMapping(value = "/chargeCashBack_submit.do")
	@ResponseBody
	public void chargeCashBack(@RequestBody Map<String, Object> param) {
		param.put("CASHBACK_ID", UniqueKey.getKeyByDateFormat());
		cashBackService.chargeCashBack(param);
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
