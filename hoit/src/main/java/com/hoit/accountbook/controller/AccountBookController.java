package com.hoit.accountbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.hoit.util.UniqueKey;

@Controller
@RequestMapping(value = "/hoit/accountBook")
public class AccountBookController {

	@Autowired
	private AccountBookService accountBookService;
	
	@Autowired
	private CashBackService cashBackService;

	@GetMapping(value = "/list.do")
	public String list(Model model, @RequestParam Map<String, Object> param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String currentDate = sdf.format(date);
		param.put("ASSET_AT", currentDate);
		model.addAttribute("cm", accountBookService.getCurrentMoney());
		model.addAttribute("list", accountBookService.accountBookList());
		model.addAttribute("ta", accountBookService.getTotalAssets(param));
//		model.addAttribute("mm", minCharge);
		return "accountBook/list";
	}
	
	@PostMapping(value = "/write_submit.do")
	@ResponseBody
	public void write(@RequestBody Map<String, Object> param) {
		accountBookService.writeAccountBook(param);
	}
	
	@PostMapping(value = "/chargeCash_submit.do")
	@ResponseBody
	public Map<String, Object> chargeCash(@RequestBody Map<String, Object> param) {
		accountBookService.chargeCash(param);
		return param;
	}
	
	@PostMapping(value = "/editTotalMoney_submit.do")
	@ResponseBody
	public void editTotalMoney(@RequestBody Map<String, Object> param) {
		accountBookService.edieTotalMoney(param);
	}
	
	@PostMapping(value = "/chargeCashBack_submit.do")
	@ResponseBody
	public void chargeCashBack(@RequestBody Map<String, Object> param) {
		param.put("CASHBACK_ID", UniqueKey.getKeyByDateFormat());
		cashBackService.chargeCashBack(param);
	}
}
