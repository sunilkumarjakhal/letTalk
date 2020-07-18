package com.demo.create.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.util.ConstantConfig;
import com.demo.util.MongoDBConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Controller
public class PostController {

	@RequestMapping(value = "/addPost", method = RequestMethod.POST)
	public String addPost(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("postData") PostedDataPojo postData) {

		try {

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_user_posted_data");

			BasicDBObject data = new BasicDBObject();
			data.put("username", name);
			data.put("data", postData.getPostDetail());
			data.put("privacy", getPostPrivacy(name));

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 5);
			cal.add(Calendar.MINUTE, 30);

			data.put("created_datetime", sdf.parse(sdf.format(cal.getTime())));

			table.insert(data);

			return "redirect:" + "/";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/";

	}

	private String getPostPrivacy(String name) {
		try {
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_post_privacy_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", name);

			DBCursor cursor = table.find(query);
			while (cursor.hasNext()) {

				BasicDBObject oneDetail = (BasicDBObject) cursor.next();

				return oneDetail.getString("privacy");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Public";
	}

	@RequestMapping(value = "/editPost", method = RequestMethod.GET)
	public ModelAndView editPost(HttpServletRequest request, HttpServletResponse response, @RequestParam String query)
			throws Exception {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		// session.setAttribute("editPostTime", query);
		if (name != null && name.length() > 0) {
			ModelAndView mav = new ModelAndView("edit_post");
			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_user_posted_data");
			BasicDBObject query1 = new BasicDBObject();
			query1.put("username", name);
			query1.put("created_datetime", sdf.parse(query));

			DBCursor cursor = table.find(query1);

			PostedDataPojo postedDataPojo = new PostedDataPojo();

			while (cursor.hasNext()) {
				BasicDBObject oneDetail = (BasicDBObject) cursor.next();
				postedDataPojo.setUsername(name);
				postedDataPojo.setDateTime(query);
				postedDataPojo.setPostDetail(oneDetail.getString("data"));
				postedDataPojo.setPrivacy(oneDetail.getString("privacy"));

			}

			mav.addObject("editPostData", postedDataPojo);
			mav.addObject("firstname", name);
			// mav.addObject("EditPostTime", query);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;
		}

	}

	@RequestMapping(value = "/editPostProcess", method = RequestMethod.POST)
	public String editPostProcess(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("editPostData") PostedDataPojo postData) {

		try {

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_user_posted_data");

			BasicDBObject data = new BasicDBObject();
			data.put("username", name);
			data.put("created_datetime", sdf.parse(postData.getDateTime()));

			BasicDBObject document = new BasicDBObject();
			document.put("data", postData.getPostDetail());
			document.put("privacy", postData.getPrivacy());

			table.update(data, new BasicDBObject("$set", document), true, true);

			return "redirect:" + "/";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/";

	}

	@RequestMapping(value = "/deletePost", method = RequestMethod.GET)
	public String deletePost(HttpServletRequest request, HttpServletResponse response, String query) {

		try {

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_user_posted_data");

			BasicDBObject data = new BasicDBObject();
			data.put("username", name);
			data.put("created_datetime", sdf.parse(query));

			table.remove(data);

			return "redirect:" + "/";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/";

	}

	@RequestMapping(value = "/editPostPrivacy", method = RequestMethod.GET)
	public ModelAndView editPostPrivacy(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("letsTalk_username");

		if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

			if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
				// System.out..println("DB is again connected");
			} else {

				// System.out..println("DB is not connecting.........");
			}
		}

		DBCollection table = MongoDBConnection.getDB().getCollection("tbl_post_privacy_data");

		// BasicDBObject

		if (name != null && name.length() > 0) {

			ModelAndView mav = new ModelAndView("edit_post_privacy");
			mav.addObject("editPostPrivacyData", new PostedDataPojo());
			mav.addObject("postPrivacy", getPostPrivacy(name));
			mav.addObject("firstname", name);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("login");
			mav.addObject("message", "You are logged out!!");
			return mav;
		}

	}

	@RequestMapping(value = "/editPostPrivacyProcess", method = RequestMethod.POST)
	public String editPostPrivacyProcess(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("editPostPrivacyData") PostedDataPojo pojo) {

		try {
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("letsTalk_username");

			if (MongoDBConnection.getDB() == null || MongoDBConnection.getMongoClient() == null) {

				if (MongoDBConnection.makeDBConnection(ConstantConfig.DB_IP, ConstantConfig.DB_PORT)) {
					// System.out..println("DB is again connected");
				} else {

					// System.out..println("DB is not connecting.........");
				}
			}

			DBCollection table = MongoDBConnection.getDB().getCollection("tbl_post_privacy_data");

			BasicDBObject query = new BasicDBObject();
			query.put("username", name);

			BasicDBObject data = new BasicDBObject();
			data.put("privacy", pojo.getPrivacy());

			table.update(query, new BasicDBObject("$set", data), true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + "/advanceSetting";

	}

}
