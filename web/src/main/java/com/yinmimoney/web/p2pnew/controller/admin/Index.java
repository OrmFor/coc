package com.yinmimoney.web.p2pnew.controller.admin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.util.DateUtil;

@Controller("admin_Index")
public class Index extends BaseController {

	@RequestMapping(value = "/admin/main", method = RequestMethod.GET)
	public String index(Model model) {
		/*List<HashMap<String, Object>> list = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = Maps.newHashMap();
			List<Integer> randomList = Lists.newArrayList(-10, -5, -2, -1, 1, 2, 5, 10);
			Collections.shuffle(randomList);// 最终随机分布
			int rndPos = new Random().nextInt(randomList.size());// 随机坐标
			Integer comm = randomList.get(rndPos);
			map.put("money", 10000 * comm * i);
			map.put("money2", 20000 * comm * i);
			map.put("date", DateUtil.dateStr2(DateUtil.rollDay(DateUtil.getNow(), i)));
			list.add(map);
		}
		model.addAttribute("list", list);*/
		return "admin/index";
	}

}