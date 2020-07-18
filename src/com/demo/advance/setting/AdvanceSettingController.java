package com.demo.advance.setting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.login.LoginDataPojo;
import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.demo.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Controller
public class AdvanceSettingController {

	@RequestMapping(value = "/advanceSetting", method = RequestMethod.GET)
	public ModelAndView advanceSetting(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("advance_setting");
		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (name != null && name.length() > 0) {
				Util util = new Util();
				mav.addObject("isAccountActivate", util.isAccountActivate(name));
			} else {
				mav = new ModelAndView("login");
				mav.addObject("login", new LoginDataPojo());
				return mav;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/editAccountActivation", method = RequestMethod.GET)
	public String editAccountActivation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int id) {
		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", name);

			BasicDBObject data = new BasicDBObject();
			if (id == 1) {
				data.put("is_account_activate", true);
			} else {
				data.put("is_account_activate", false);
			}

			table.update(query, new BasicDBObject("$set", data), true, true);
			return "redirect:" + "/advanceSetting";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/advanceSetting";

	}

}
