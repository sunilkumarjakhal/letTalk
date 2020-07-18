package com.demo.login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo.create.post.PostedDataPojo;
import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.demo.util.Util;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Controller
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (name != null && name.length() > 0) {
			ModelAndView mav = new ModelAndView("welcome");

			List<PostedDataPojo> postedDataPojoList = getAllPostData(request);
			mav.addObject("postedDataPojoList", postedDataPojoList);
			mav.addObject("postData", new PostedDataPojo());
			mav.addObject("firstname", session.getAttribute("letsTalk_username").toString());
			return mav;
		}

		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new LoginDataPojo());
		return mav;

	}

	@ResponseBody
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("login") LoginDataPojo login, BindingResult br) {

		ModelAndView mav = null;

		if (br.hasErrors()) {
			mav = new ModelAndView("login");
			mav.addObject("message", "Something goes wrong !!!");
			return mav;
		}

		if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

			if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
				// System.out..println("DB is again connected");
			} else {

				// System.out..println("DB is not connecting.........");
			}
		}

		DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");

		BasicDBObject query = new BasicDBObject();
		query.put("username", login.getUsername());
		query.put("password", login.getPassword());

		DBCursor cursor = table.find(query);

		if (cursor.size() > 0) {

			HttpSession session = request.getSession();
			session.setAttribute("letsTalk_username", login.getUsername());

			mav = new ModelAndView("welcome");
			List<PostedDataPojo> postedDataPojoList = getAllPostData(request);
			mav.addObject("postedDataPojoList", postedDataPojoList);
			mav.addObject("postData", new PostedDataPojo());
			mav.addObject("firstname", login.getUsername());
		} else {
			mav = new ModelAndView("login");
			mav.addObject("message", "Username or Password is wrong!!");
		}

		return mav;

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("register");
		mav.addObject("register", new RegistrationDataPojo());
		return mav;

	}

	@ResponseBody
	@RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
	public ModelAndView registerProcess(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("register") RegistrationDataPojo registrationData, BindingResult br) {

		ModelAndView mav = null;

		if (br.hasErrors()) {
			mav = new ModelAndView("register");
			mav.addObject("message", "Something goes wrong !!!");
			return mav;
		}

		if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

			if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
				// System.out..println("DB is again connected");
			} else {

				// System.out..println("DB is not connecting.........");
			}
		}

		DBCollection table = MongoDBConnection.getDB().getCollection("tbl_users_detail_data");
		DBCollection table2 = MongoDBConnection.getDB().getCollection("tbl_post_privacy_data");
		

		BasicDBObject query = new BasicDBObject();
		query.put("username", registrationData.getUsername());

		DBCursor cursor = table.find(query);

		if (cursor.size() > 0) {
			mav = new ModelAndView("register");
			mav.addObject("message", "User already exists !!!");
			return mav;
		}

		else if (registrationData.getPassword().equalsIgnoreCase(registrationData.getCpassword())) {

			HttpSession session = request.getSession();
			session.setAttribute("letsTalk_username", registrationData.getUsername());

			long theRandomNum = (long) (Math.random() * Math.pow(10, 10));
			
			BasicDBObject privacy = new BasicDBObject();
			privacy.put("privacy", "Public");

			BasicDBObject data = new BasicDBObject();
			data.put("username", registrationData.getUsername());
			data.put("password", registrationData.getPassword());
			data.put("first_name", registrationData.getFirstName());
			data.put("last_name", registrationData.getLastName());
			data.put("is_account_activate", true);
			data.put("f_id", theRandomNum);

			table.insert(data);
			table2.insert(privacy);

			mav = new ModelAndView("welcome");
			List<PostedDataPojo> postedDataPojoList = getAllPostData(request);
			mav.addObject("postedDataPojoList", postedDataPojoList);
			mav.addObject("postData", new PostedDataPojo());
			mav.addObject("firstname", registrationData.getUsername());
			return mav;
		} else {
			mav = new ModelAndView("register");
			mav.addObject("message", "Password not matched !!!");
			return mav;
		}
		// return mav;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		session.removeAttribute("letsTalk_username");

		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new LoginDataPojo());
		return mav;

	}

	public List<PostedDataPojo> getAllPostData(HttpServletRequest request) {

		List<PostedDataPojo> pojoList = new LinkedList<PostedDataPojo>();
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		try {

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_user_posted_data");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			Util util = new Util();
			BasicDBList dbList = util.findFriendList(name);

			BasicDBObject query1 = new BasicDBObject();
			query1.put("username", name);

			BasicDBObject query2 = new BasicDBObject();
			query2.put("privacy", "Public");
			//query2.put("is_account_activate", true);

			BasicDBObject query3 = new BasicDBObject();
			query3.put("username", new BasicDBObject("$in", dbList));
			query3.put("privacy", "Friend");
			//query3.put("is_account_activate", util.isAccountActivate(name));
			BasicDBList or = new BasicDBList();
			or.add(query1);
			or.add(query2);
			or.add(query3);
			BasicDBObject finalQuery = new BasicDBObject("$or", or);

			DBCursor cursor = table.find(finalQuery).sort(new BasicDBObject("created_datetime", -1));
			BasicDBObject oneDetail = null;
			while (cursor.hasNext()) {
				oneDetail = (BasicDBObject) cursor.next();
				PostedDataPojo pojo = new PostedDataPojo();
				pojo.setUsername(oneDetail.getString("username"));
				if (oneDetail.getString("username").equalsIgnoreCase(name)) {
					pojo.setMyPost(true);
				} else {
					pojo.setMyPost(false);
				}
				pojo.setPostDetail(oneDetail.getString("data"));
				pojo.setDateTime(sdf.format(oneDetail.getDate("created_datetime")));

				pojoList.add(pojo);
			}
			System.out.println("pojoList " + pojoList.size());
			return pojoList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
